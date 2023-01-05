package com.example.firstapplication2

import Films
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.Typography
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.text.TextStyle

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstApplication2Theme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val windowSizeClass = calculateWindowSizeClass(this)
                    val navController = rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    Scaffold(
                        bottomBar = {
                            when (windowSizeClass.widthSizeClass) {
                                WindowWidthSizeClass.Compact ->{
                                    if (currentDestination != null && currentDestination?.hierarchy?.any { it.route == "home" } == false) {
                                        BottomAppBar(modifier = Modifier.border(BorderStroke(1.dp, Color(0xFF651FFF)), shape = RoundedCornerShape(15))){
                                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                                            val currentDestination = navBackStackEntry?.destination
                                            BottomNavigationItem(
                                                icon = {
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.movies),
                                                        contentDescription = null,
                                                        modifier = Modifier.size(25.dp)
                                                    )
                                                },
                                                label = { Text(text = "Films") },
                                                selected = currentDestination?.hierarchy?.any { it.route == "films" } == true,
                                                onClick = { navController.navigate("films") },
                                                selectedContentColor = Color(0xFFAE88FF),
                                                unselectedContentColor = Color.LightGray
                                            )
                                            BottomNavigationItem(
                                                icon = {
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.series),
                                                        contentDescription = null,
                                                        modifier = Modifier.size(25.dp)
                                                    )
                                                },
                                                label = { Text(text = "Séries") },
                                                selected = currentDestination?.hierarchy?.any { it.route == "series" } == true,
                                                onClick = { navController.navigate("series") },
                                                selectedContentColor = Color(0xFFAE88FF),
                                                unselectedContentColor = Color.LightGray
                                            )
                                            BottomNavigationItem(
                                                icon = {
                                                    Icon(
                                                        Icons.Filled.Person,
                                                        contentDescription = null
                                                    )
                                                },
                                                label = { Text(text = "Acteurs") },
                                                selected = currentDestination?.hierarchy?.any { it.route == "acteurs" } == true,
                                                onClick = { navController.navigate("acteurs") },
                                                selectedContentColor = Color(0xFFAE88FF),
                                                unselectedContentColor = Color.LightGray
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    ) { innerPadding ->
                        when (windowSizeClass.heightSizeClass) {
                            WindowHeightSizeClass.Compact ->{
                                Row {
                                    if (currentDestination != null && currentDestination?.hierarchy?.any { it.route == "home" } == false) {
                                        NavigationRail() {
                                            NavigationRailItem(
                                                icon = {
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.movies),
                                                        contentDescription = null,
                                                        modifier = Modifier.size(25.dp)
                                                    )
                                                },
                                                label = { Text(text = "Films") },
                                                selected = currentDestination?.hierarchy?.any { it.route == "films" } == true,
                                                onClick = { navController.navigate("films") }
                                            )
                                            NavigationRailItem(
                                                icon = {
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.series),
                                                        contentDescription = null,
                                                        modifier = Modifier.size(25.dp)
                                                    )
                                                },
                                                label = { Text(text = "Séries") },
                                                selected = currentDestination?.hierarchy?.any { it.route == "series" } == true,
                                                onClick = { navController.navigate("series") }
                                            )
                                            NavigationRailItem(
                                                icon = {
                                                    Icon(
                                                        Icons.Filled.Person,
                                                        contentDescription = null
                                                    )
                                                },
                                                label = { Text(text = "Acteurs") },
                                                selected = currentDestination?.hierarchy?.any { it.route == "acteurs" } == true,
                                                onClick = { navController.navigate("acteurs") }
                                            )
                                        }
                                    }
                                    NavHost(
                                        navController = navController,
                                        startDestination = "home",
                                        Modifier.padding(innerPadding)
                                    ) {
                                        composable("home") {
                                            Home(windowSizeClass, navController)
                                        }
                                        composable("films") {
                                            Films(
                                                MainViewModel(),
                                                navController,
                                                windowSizeClass
                                            )
                                        }
                                        composable("detailFilm/{id}") {
                                            DetailFilm(
                                                id = navBackStackEntry?.arguments?.getString(
                                                    "id"
                                                ), MainViewModel(), windowSizeClass
                                            )
                                        }
                                        composable("acteurs") {
                                            Acteurs(
                                                MainViewModel(),
                                                navController,
                                                windowSizeClass
                                            )
                                        }
                                        composable("detailActeur/{id}") {
                                            DetailActeur(
                                                id = navBackStackEntry?.arguments?.getString(
                                                    "id"
                                                ), MainViewModel()
                                            )
                                        }
                                        composable("series") {
                                            Series(
                                                MainViewModel(),
                                                navController,
                                                windowSizeClass
                                            )
                                        }
                                        composable("detailSerie/{id}") {
                                            DetailSerie(
                                                id = navBackStackEntry?.arguments?.getString(
                                                    "id"
                                                ), MainViewModel(), windowSizeClass
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        when (windowSizeClass.widthSizeClass) {
                            WindowWidthSizeClass.Compact ->{
                                NavHost(navController = navController, startDestination = "home", Modifier.padding(innerPadding)) {
                                    composable("home") {
                                        Home(windowSizeClass, navController)
                                    }
                                    composable("films") {
                                        Films(MainViewModel(), navController, windowSizeClass)
                                    }
                                    composable("detailFilm/{id}") {
                                        DetailFilm(id = navBackStackEntry?.arguments?.getString("id"), MainViewModel(), windowSizeClass)
                                    }
                                    composable("acteurs") {
                                        Acteurs(MainViewModel(), navController, windowSizeClass)
                                    }
                                    composable("detailActeur/{id}") {
                                        DetailActeur(id = navBackStackEntry?.arguments?.getString("id"), MainViewModel())
                                    }
                                    composable("series") {
                                        Series(MainViewModel(), navController, windowSizeClass)
                                    }
                                    composable("detailSerie/{id}") {
                                        DetailSerie(id = navBackStackEntry?.arguments?.getString("id"), MainViewModel(), windowSizeClass)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    /*Pour le thème sombre :*/
    @Composable
    fun FirstApplication2Theme(
        content: @Composable () -> Unit
    ) {
        val colors = DarkColorPalette

        val typography = DarkTypography

        MaterialTheme(
            colors = colors,
            typography = typography,
            shapes = shapes,
            content = content
        )
    }
}

val purple200 = Color(0xFF651FFF)
val purple500 = Color(0xFF6200EA)
val background = Color(0xFF2B292B)
val background800 = Color(0xFF424242)
val DarkTypography = Typography(
    h1 = TextStyle(
        color = Color.White
    ),
    h2 = TextStyle(
        color = Color.White
    ),
    h3 = TextStyle(
        color = Color.White
    ),
    h4 = TextStyle(
        color = Color.White
    )
)
private val DarkColorPalette = darkColors(
    background = background,
    onBackground = background800,
    onPrimary = Color.White,
    onSecondary = Color.White
)