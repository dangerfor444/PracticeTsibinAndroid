package com.example.practicetsibin.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Badge
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

data class BottomItem(val label: String, val route: String, val hasBadge: Boolean = false)

@Composable
fun BottomBar(navController: NavHostController, items: List<BottomItem>, hasActiveFilters: Boolean = false) {
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
                    Column(
                        modifier = Modifier,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        val iconVector = when {
                            index == 0 -> Icons.AutoMirrored.Filled.List
                            item.label == "Избранное" -> Icons.Default.Star
                            else -> Icons.Filled.Person
                        }
                        
                        Box {
                            Icon(
                                imageVector = iconVector,
                                contentDescription = item.label
                            )
                            
                            if (item.hasBadge && hasActiveFilters) {
                                Badge(
                                    modifier = Modifier
                                        .offset(x = 8.dp, y = (-4).dp)
                                )
                            }
                        }
                    }
                },
                label = { Text(item.label) }
            )
        }
    }
}
