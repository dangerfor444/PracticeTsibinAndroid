package com.example.practicetsibin

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.practicetsibin.di.DIContainer
import com.example.practicetsibin.feature.details.MovieDetailsViewModel
import com.example.practicetsibin.feature.favorites.FavoritesViewModel
import com.example.practicetsibin.feature.list.MovieListViewModel
import com.example.practicetsibin.feature.settings.FilterSettingsViewModel
import com.example.practicetsibin.navigation.Routes
import com.example.practicetsibin.ui.components.BottomBar
import com.example.practicetsibin.ui.components.BottomItem
import com.example.practicetsibin.ui.screens.FavoritesScreen
import com.example.practicetsibin.ui.screens.FilterSettingsScreen
import com.example.practicetsibin.ui.screens.MovieDetailsScreen
import com.example.practicetsibin.ui.screens.MovieListScreen
import com.example.practicetsibin.ui.screens.PlaceholderScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val items = listOf(
        BottomItem(label = "Список", route = Routes.LIST, hasBadge = true),
        BottomItem(label = "Избранное", route = Routes.FAVORITES),
        BottomItem(label = "Профиль", route = Routes.PROFILE)
    )
    
    val hasActiveFilters by DIContainer.badgeCache.hasActiveFilters.collectAsState()

    Scaffold(
        bottomBar = {
            BottomBar(
                navController = navController,
                items = items,
                hasActiveFilters = hasActiveFilters
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.LIST,
            modifier = androidx.compose.ui.Modifier.padding(innerPadding)
        ) {
            composable(Routes.LIST) {
                val viewModel: MovieListViewModel = viewModel()
                val movies by viewModel.movies.collectAsState()
                val isLoading by viewModel.isLoading.collectAsState()
                val error by viewModel.error.collectAsState()
                
                MovieListScreen(
                    movies = movies,
                    isLoading = isLoading,
                    error = error,
                    onMovieClick = { movieId ->
                        navController.navigate(Routes.details(movieId))
                    },
                    onRetry = { viewModel.retry() },
                    onFilterClick = { navController.navigate(Routes.FILTER_SETTINGS) },
                    hasActiveFilters = hasActiveFilters
                )
            }
            composable(Routes.FAVORITES) {
                val viewModel: FavoritesViewModel = viewModel()
                val movies by viewModel.movies.collectAsState()
                val isLoading by viewModel.isLoading.collectAsState()
                
                FavoritesScreen(
                    movies = movies,
                    isLoading = isLoading,
                    onMovieClick = { movieId ->
                        navController.navigate(Routes.details(movieId))
                    },
                    onBack = { navController.navigateUp() },
                    onClearAll = { viewModel.clearAllFavorites() }
                )
            }
            composable(Routes.PROFILE) {
                PlaceholderScreen(title = "Profile")
            }
            composable(Routes.FILTER_SETTINGS) {
                val viewModel: FilterSettingsViewModel = viewModel {
                    FilterSettingsViewModel(
                        DIContainer.filterSettingsRepositoryInstance,
                        DIContainer.badgeCache
                    )
                }
                val filterSettings by viewModel.filterSettings.collectAsState()
                
                FilterSettingsScreen(
                    filterSettings = filterSettings,
                    onTitleQueryChange = viewModel::updateTitleQuery,
                    onGenreToggle = viewModel::toggleGenre,
                    onMinRatingChange = viewModel::updateMinRating,
                    onMinYearChange = viewModel::updateMinYear,
                    onMaxYearChange = viewModel::updateMaxYear,
                    onApplyFilters = {
                        viewModel.applyFilters()
                        navController.navigateUp()
                    },
                    onClearFilters = viewModel::clearFilters,
                    onBack = { navController.navigateUp() }
                )
            }
            composable(Routes.DETAILS) { backStackEntry ->
                val movieId = backStackEntry.arguments?.getString("movieId") ?: return@composable
                val viewModel: MovieDetailsViewModel = viewModel()
                val movie by viewModel.movie.collectAsState()
                val isFavorite by viewModel.isFavorite.collectAsState()
                val isLoading by viewModel.isLoading.collectAsState()
                val error by viewModel.error.collectAsState()
                
                androidx.compose.runtime.LaunchedEffect(movieId) {
                    viewModel.loadMovieDetails(movieId)
                }
                
                if (movie != null) {
                    MovieDetailsScreen(
                        movie = movie!!,
                        isFavorite = isFavorite,
                        onBack = { navController.navigateUp() },
                        onToggleFavorite = { viewModel.toggleFavorite() }
                    )
                } else if (isLoading) {
                    PlaceholderScreen(title = "Загрузка...")
                } else if (error != null) {
                    PlaceholderScreen(title = "Ошибка: $error")
                } else {
                    PlaceholderScreen(title = "Фильм не найден")
                }
            }
        }
    }
}