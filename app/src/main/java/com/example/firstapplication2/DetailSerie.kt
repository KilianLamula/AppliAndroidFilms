package com.example.firstapplication2

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun DetailSerie(id:String?, viewModel: MainViewModel, windowClass: WindowSizeClass){
    when (windowClass.heightSizeClass) {
        WindowHeightSizeClass.Compact -> {
            detailSerie(id, viewModel, 780, 500,4)
        }

        else -> {
            detailSerie(id, viewModel, 1280, 780,2)
        }
    }
}

@Composable
fun detailSerie(id:String?, viewModel: MainViewModel, sizeBackdrop: Int, sizePoster: Int, nbCol: Int){
    if (id != null) {
        viewModel.getSerie(id)
    }
    val serie by viewModel.serie.collectAsState()

    LazyColumn{
        item{
            Card(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(10.dp),
                elevation = 50.dp
            ){
                Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
                    serie?.let {
                        Text(
                            text = it.name,
                            style = MaterialTheme.typography.h5,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        AsyncImage(
                            model = "https://image.tmdb.org/t/p/w"+sizeBackdrop+"/"+it.backdrop_path,
                            contentDescription = null,
                            modifier = Modifier.padding(20.dp)
                        )

                        Text(text = "Synopsis", style = MaterialTheme.typography.h5, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(10.dp))
                        Text(it.overview, textAlign = TextAlign.Justify, modifier = Modifier.padding(10.dp))

                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceAround){
                            Column(){
                                AsyncImage(
                                    model = "https://image.tmdb.org/t/p/w500/"+it.poster_path,
                                    contentDescription = null,
                                    modifier = Modifier.padding(20.dp)
                                )
                            }
                            Column(){
                                Spacer(Modifier.height(10.dp))
                                Text(text = "Date de début :", fontWeight = FontWeight.Bold)
                                Spacer(Modifier.height(5.dp))
                                Text(text = it.first_air_date, fontStyle = FontStyle.Italic)
                                Spacer(Modifier.height(20.dp))
                                Text(text = "Genre :", fontWeight = FontWeight.Bold)
                                Spacer(Modifier.height(5.dp))
                                LazyColumn(modifier = Modifier.height(100.dp)) {
                                    items(it.genres) { genre ->
                                        Spacer(Modifier.height(10.dp))
                                        Text(
                                            genre.name
                                        )
                                    }
                                }
                                Spacer(Modifier.height(20.dp))
                                Text(text = "Informations :", fontWeight = FontWeight.Bold)
                                Spacer(Modifier.height(5.dp))
                                Text(text = it.number_of_episodes.toString()+" épisode(s)")
                                Spacer(Modifier.height(5.dp))
                                Text(text = it.number_of_seasons.toString()+" saison(s)")
                            }
                        }
                        Spacer(Modifier.height(10.dp))
                        if(it.credits.cast.isNotEmpty()){
                            Text(text = "Casting", style = MaterialTheme.typography.h5, fontWeight = FontWeight.Bold)
                            Spacer(Modifier.height(20.dp))
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(nbCol),
                                modifier = Modifier.height(400.dp)
                            ) {
                                items(it.credits.cast){ actor ->
                                    Card(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .padding(15.dp),
                                        elevation = 10.dp
                                    ){
                                        Column(horizontalAlignment = Alignment.CenterHorizontally){
                                            if(actor.profile_path != null){
                                                AsyncImage(
                                                    model = "https://image.tmdb.org/t/p/w"+sizePoster+"/" + actor.profile_path,
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
                                            Text(text = actor.name, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                                            Spacer(Modifier.height(10.dp))
                                            Text(text = actor.original_name, fontStyle = FontStyle.Italic, textAlign = TextAlign.Center)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}