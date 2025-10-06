package com.example.practicetsibin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size

data class Movie(
    val id: String,
    val title: String,
    val year: Int,
    val posterUrl: String,
    val rating: Double,
    val genres: List<String>,
    val description: String
)

object MockMovieRepository {
    val movies: List<Movie> = listOf(
        Movie(
            id = "1",
            title = "Начало",
            year = 2010,
            posterUrl = "",
            rating = 8.8,
            genres = listOf("Боевик", "Фантастика", "Триллер"),
            description = "Профессионал проникает в сны, чтобы внедрить идею в сознание человека."
        ),
        Movie(
            id = "2",
            title = "Интерстеллар",
            year = 2014,
            posterUrl = "",
            rating = 8.6,
            genres = listOf("Приключения", "Драма", "Фантастика"),
            description = "Команда исследователей отправляется через червоточину на поиски нового дома для человечества."
        ),
        Movie(
            id = "3",
            title = "Тёмный рыцарь",
            year = 2008,
            posterUrl = "",
            rating = 9.0,
            genres = listOf("Боевик", "Криминал", "Драма"),
            description = "Бэтмен сталкивается с хаотичным Джокером, ставящим Готэм на грань анархии."
        ),
        Movie(
            id = "4",
            title = "Дюна",
            year = 2021,
            posterUrl = "",
            rating = 8.1,
            genres = listOf("Фантастика", "Драма", "Приключения"),
            description = "Наследник великого дома отправляется на пустынную планету Арракис."
        ),
        Movie(
            id = "5",
            title = "Матрица",
            year = 1999,
            posterUrl = "",
            rating = 8.7,
            genres = listOf("Фантастика", "Боевик"),
            description = "Хакер Нео узнаёт, что мир — это симуляция, и присоединяется к сопротивлению."
        ),
        Movie(
            id = "6",
            title = "Паразиты",
            year = 2019,
            posterUrl = "",
            rating = 8.5,
            genres = listOf("Драма", "Триллер"),
            description = "Бедная семья внедряется в дом богатых, что приводит к неожиданным последствиям."
        ),
        Movie(
            id = "7",
            title = "Зелёная миля",
            year = 1999,
            posterUrl = "",
            rating = 9.1,
            genres = listOf("Драма", "Фэнтези"),
            description = "Тюремный надзиратель знакомится с необычным заключённым, обладающим даром исцеления."
        ),
        Movie(
            id = "8",
            title = "Побег из Шоушенка",
            year = 1994,
            posterUrl = "",
            rating = 9.3,
            genres = listOf("Драма"),
            description = "Банкир, несправедливо осуждённый, планирует побег и не теряет надежды."
        ),
        Movie(
            id = "9",
            title = "Аватар",
            year = 2009,
            posterUrl = "",
            rating = 7.8,
            genres = listOf("Фантастика", "Приключения"),
            description = "Бывший морпех отправляется на планету Пандора и становится частью народа На'ви."
        ),
        Movie(
            id = "10",
            title = "Властелин колец: Братство кольца",
            year = 2001,
            posterUrl = "",
            rating = 8.8,
            genres = listOf("Фэнтези", "Приключения"),
            description = "Хоббит Фродо начинает опасное путешествие, чтобы уничтожить Кольцо Всевластия."
        ),
        Movie(
            id = "11",
            title = "Гарри Поттер и философский камень",
            year = 2001,
            posterUrl = "",
            rating = 7.6,
            genres = listOf("Фэнтези", "Приключения"),
            description = "Мальчик-волшебник поступает в Хогвартс и узнаёт о своём предназначении."
        ),
        Movie(
            id = "12",
            title = "Джентльмены",
            year = 2019,
            posterUrl = "",
            rating = 7.8,
            genres = listOf("Криминал", "Комедия"),
            description = "Наркобарон пытается продать бизнес, но попадает в водоворот интриг."
        ),
        Movie(
            id = "13",
            title = "Форрест Гамп",
            year = 1994,
            posterUrl = "",
            rating = 8.9,
            genres = listOf("Драма", "Романтика"),
            description = "История простодушного Форреста, невольно оказавшегося участником важных событий."
        ),
        Movie(
            id = "14",
            title = "1+1",
            year = 2011,
            posterUrl = "",
            rating = 8.8,
            genres = listOf("Драма", "Комедия"),
            description = "Дружба аристократа-инвалида и молодого помощника меняет их жизни."
        ),
        Movie(
            id = "15",
            title = "Бойцовский клуб",
            year = 1999,
            posterUrl = "",
            rating = 8.7,
            genres = listOf("Драма", "Триллер"),
            description = "Клерк знакомится с Тайлером Дёрденом и погружается в мир подпольного клуба."
        )
    )

    fun getMovieById(id: String): Movie? = movies.find { it.id == id }
}

private object Routes {
    const val LIST = "list"
    const val DETAILS = "details/{movieId}"
    const val PROFILE = "profile"
    fun details(movieId: String) = "details/$movieId"
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val items = listOf(
        BottomItem(label = "Список", route = Routes.LIST),
        BottomItem(label = "Профиль", route = Routes.PROFILE)
    )

    Scaffold(
        bottomBar = {
            BottomBar(navController = navController, items = items)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.LIST,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.LIST) {
                MovieListScreen(
                    movies = MockMovieRepository.movies,
                    onMovieClick = { movieId ->
                        navController.navigate(Routes.details(movieId))
                    }
                )
            }
            composable(Routes.PROFILE) {
                PlaceholderScreen(title = "Profile")
            }
            composable(Routes.DETAILS) { backStackEntry ->
                val movieId = backStackEntry.arguments?.getString("movieId") ?: return@composable
                val movie = MockMovieRepository.getMovieById(movieId)
                if (movie != null) {
                    MovieDetailsScreen(movie = movie, onBack = { navController.navigateUp() })
                } else {
                    PlaceholderScreen(title = "Not Found")
                }
            }
        }
    }
}

private data class BottomItem(val label: String, val route: String)

@Composable
private fun BottomBar(navController: NavHostController, items: List<BottomItem>) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route
    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = currentDestination == item.route,
                onClick = {
                    if (currentDestination != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (index == 0) Icons.AutoMirrored.Filled.List else Icons.Filled.Person,
                        contentDescription = item.label
                    )
                },
                label = { Text(item.label) }
            )
        }
    }
}

@Composable
private fun MovieListScreen(movies: List<Movie>, onMovieClick: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(movies) { movie ->
            MovieListItem(movie = movie, onClick = { onMovieClick(movie.id) })
        }
    }
}

@Composable
private fun MovieListItem(movie: Movie, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = movie.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.size(6.dp))
            Text(
                text = "${movie.year} • ${movie.rating} • ${movie.genres.joinToString()}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MovieDetailsScreen(movie: Movie, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = movie.title, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { innerPadding ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            val (titleRef, metaRef, descRef) = createRefs()

            Text(
                text = movie.title,
                modifier = Modifier.constrainAs(titleRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = androidx.constraintlayout.compose.Dimension.fillToConstraints
                }
            )

            Text(
                text = "${movie.year} • ${movie.rating} • ${movie.genres.joinToString()}",
                modifier = Modifier.constrainAs(metaRef) {
                    top.linkTo(titleRef.bottom, margin = 8.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = androidx.constraintlayout.compose.Dimension.fillToConstraints
                }
            )

            Text(
                text = movie.description,
                modifier = Modifier.constrainAs(descRef) {
                    top.linkTo(metaRef.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = androidx.constraintlayout.compose.Dimension.fillToConstraints
                }
            )
        }
    }
}

@Composable
private fun PlaceholderScreen(title: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = title)
    }
}
