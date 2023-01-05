package com.example.firstapplication2

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
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
fun Acteurs(viewModel: MainViewModel, navController: NavController, windowClass: WindowSizeClass){
    when (windowClass.heightSizeClass) {
        WindowHeightSizeClass.Compact -> {
            listeActeur(viewModel, navController, 4, 500)
        }

        else -> {
            listeActeur(viewModel, navController, 2, 780)
        }
    }
}

@Composable
fun listeActeur(viewModel: MainViewModel, navController: NavController, nbCol: Int, sizeImage: Int){
    viewModel.getActeursWeek()
    val actors by viewModel.actors.collectAsState()

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
                                    viewModel.getActeursWeek()
                                }
                                else{
                                    viewModel.searchActors(searchText = textSearch.text)
                                }
                            }
                        ),
                        onValueChange = { newText ->
                            textSearch = newText
                        },
                        placeholder = { Text(text = "Chercher un acteur") }
                    )
                    Spacer(Modifier.width(60.dp))
                    IconButton(onClick = {
                        if (textSearch.text == ""){
                            viewModel.getActeursWeek()
                        }
                        else{
                            viewModel.searchActors(searchText = textSearch.text)
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
            items(actors) { actor ->
                Card(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(15.dp),
                    elevation = 10.dp
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        if(actor.profile_path != null){
                            AsyncImage(
                                model = "https://image.tmdb.org/t/p/w" + sizeImage + "/" + actor.profile_path,
                                contentDescription = null
                            )
                        }
                        else{
                            Image(
                                painterResource(R.drawable.imagemanquante),
                                contentDescription = "noImage",
                                modifier = Modifier.size(247.dp)
                            )
                        }
                        Text(
                            text = actor.name,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(5.dp))
                        Button(
                            onClick = { navController.navigate("detailActeur/"+actor.id) },
                            border = BorderStroke(width = 1.dp, brush = SolidColor(Color.DarkGray)),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF651FFF)),
                            shape = RoundedCornerShape(40)
                        ) {
                            Text("Détails")
                        }
                    }
                }
            }
        }
    }
}