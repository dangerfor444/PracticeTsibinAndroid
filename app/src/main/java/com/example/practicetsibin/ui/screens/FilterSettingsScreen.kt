package com.example.practicetsibin.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.practicetsibin.data.FilterSettings

private val availableGenres = listOf(
    "Action", "Adventure", "Animation", "Biography", "Comedy",
    "Crime", "Drama", "Fantasy", "History", "Horror",
    "Mystery", "Romance", "Sci-Fi", "Thriller", "War"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterSettingsScreen(
    filterSettings: FilterSettings,
    onTitleQueryChange: (String) -> Unit,
    onGenreToggle: (String) -> Unit,
    onMinRatingChange: (Double) -> Unit,
    onMinYearChange: (Int) -> Unit,
    onMaxYearChange: (Int) -> Unit,
    onApplyFilters: () -> Unit,
    onClearFilters: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Настройки фильтрации") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Поиск по названию",
                            style = MaterialTheme.typography.titleMedium
                        )
                        
                        OutlinedTextField(
                            value = filterSettings.titleQuery,
                            onValueChange = onTitleQueryChange,
                            label = { Text("Введите название фильма") },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("Например: Matrix, Avatar") }
                        )
                    }
                }
            }
            
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Выберите жанры",
                            style = MaterialTheme.typography.titleMedium
                        )
                        
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            availableGenres.chunked(2).forEach { rowGenres ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    rowGenres.forEach { genre ->
                                        Row(
                                            modifier = Modifier
                                                .weight(1f)
                                                .clickable { onGenreToggle(genre) }
                                                .padding(8.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Checkbox(
                                                checked = filterSettings.genres.contains(genre),
                                                onCheckedChange = { onGenreToggle(genre) }
                                            )
                                            Text(
                                                text = genre,
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Минимальный рейтинг: ${String.format("%.1f", filterSettings.minRating)}",
                            style = MaterialTheme.typography.titleMedium
                        )
                        
                        Slider(
                            value = filterSettings.minRating.toFloat(),
                            onValueChange = { onMinRatingChange(it.toDouble()) },
                            valueRange = 0f..10f,
                            steps = 19,
                            modifier = Modifier.fillMaxWidth()
                        )
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("0.0", style = MaterialTheme.typography.bodySmall)
                            Text("10.0", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
            
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Год выпуска",
                            style = MaterialTheme.typography.titleMedium
                        )
                        
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(
                                text = "От: ${filterSettings.minYear}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            
                            Slider(
                                value = filterSettings.minYear.toFloat(),
                                onValueChange = { onMinYearChange(it.toInt()) },
                                valueRange = 1900f..2024f,
                                steps = 123,
                                modifier = Modifier.fillMaxWidth()
                            )
                            
                            Text(
                                text = "До: ${filterSettings.maxYear}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            
                            Slider(
                                value = filterSettings.maxYear.toFloat(),
                                onValueChange = { onMaxYearChange(it.toInt()) },
                                valueRange = 1900f..2024f,
                                steps = 123,
                                modifier = Modifier.fillMaxWidth()
                            )
                            
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("1900", style = MaterialTheme.typography.bodySmall)
                                Text("2024", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
            }
            
            item {
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick = onClearFilters,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Очистить")
                    }
                    
                    Button(
                        onClick = onApplyFilters,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Применить")
                    }
                }
            }
        }
    }
}