package com.example.firstapplication2

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage

@Composable
fun Series(viewModel: MainViewModel, navController: NavController, windowClass: WindowSizeClass){
    when (windowClass.heightSizeClass) {
        WindowHeightSizeClass.Compact -> {
            listeSerie(viewModel, navController, 4, 500)
        }

        else -> {
            listeSerie(viewModel, navController, 2, 780)
        }
    }
}

@Composable
fun listeSerie(viewModel: MainViewModel, navController: NavController, nbCol: Int, sizeImage: Int){
    viewModel.getSeriesInitiales()
    val series by viewModel.series.collectAsState()

    var textSearch by remember { mutableStateOf(TextFieldValue("")) }
    Scaffold(
        topBar = {
            TopAppBar(contentColor = Color(0xFF651FFF)){
                Row(Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceAround){
                    TextField(
                        value = textSearch,
                        singleLine = true,
                        maxLines = 1,
                        leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "searchIcon") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                if (textSearch.text == ""){
                                    viewModel.getSeriesInitiales()
                                }
                                else{
                                    viewModel.searchSeries(searchText = textSearch.text)
                                }
                            }
                        ),
                        onValueChange = { newText ->
                            textSearch = newText
                        },
                        placeholder = { Text(text = "Chercher une série") }
                    )
                    Spacer(Modifier.width(60.dp))
                    IconButton(onClick = {
                        if (textSearch.text == ""){
                            viewModel.getSeriesInitiales()
                        }
                        else{
                            viewModel.searchSeries(searchText = textSearch.text)
                        }
                    }) {
                        Icon(Icons.Filled.Search, contentDescription = null)
                    }
                }
            }
        }
    ) { innerPadding ->

        LazyVerticalGrid(
            columns = GridCells.Fixed(nbCol),
            Modifier.padding(innerPadding)
        ) {
            items(series) { serie ->
                Card(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(15.dp),
                    elevation = 10.dp
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        AsyncImage(
                            model = "https://image.tmdb.org/t/p/w"+ sizeImage + "/" + serie.poster_path,
                            contentDescription = null
                        )
                        Text(
                            text = serie.name,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(10.dp))
                        Text(text = serie.first_air_date, fontStyle = FontStyle.Italic)
                        Spacer(Modifier.height(5.dp))
                        Button(
                            onClick = { navController.navigate("detailSerie/" + serie.id) },
                            border = BorderStroke(width = 1.dp, brush = SolidColor(Color.DarkGray)),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF651FFF)),
                            shape = RoundedCornerShape(40),
                        ) {
                            Text("Détails")
                        }
                    }
                }
            }
        }
    }
}