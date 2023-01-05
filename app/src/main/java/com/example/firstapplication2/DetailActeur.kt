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
fun DetailActeur(id:String?, viewModel: MainViewModel){
    if (id != null) {
        viewModel.getActor(id)
    }
    val actor by viewModel.actor.collectAsState()

    LazyColumn{
        item{
            Card(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(10.dp),
                elevation = 50.dp
            ){
                Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
                    actor?.let {
                        Text(
                            text = it.name,
                            style = MaterialTheme.typography.h5,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        if(it.profile_path != null){
                            AsyncImage(
                                model = "https://image.tmdb.org/t/p/w780/" + it.profile_path,
                                contentDescription = null,
                                modifier = Modifier.padding(20.dp)
                            )
                        }
                        else{
                            Image(
                                painterResource(R.drawable.imagemanquante),
                                contentDescription = "noImage",
                                modifier = Modifier.size(247.dp)
                            )
                        }

                        Text(text = "Biographie", style = MaterialTheme.typography.h5, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(10.dp))

                        if(it.biography==""){
                            Text(it.name + " ne possède aucune biographie pour le moment.", textAlign = TextAlign.Justify, modifier = Modifier.padding(10.dp))
                        }
                        else{
                            Text(it.biography, textAlign = TextAlign.Justify, modifier = Modifier.padding(10.dp))
                        }

                        Spacer(Modifier.height(20.dp))
                        Text(text = "Informations supplémentaires", style = MaterialTheme.typography.h5, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(20.dp))
                        Row{
                            Text(text = "Anniversaire : ", fontWeight = FontWeight.Bold)
                            if(it.birthday!=null){
                                Text(text = it.birthday)
                            }
                            else{
                                Text(text = "Pas de date disponible.")
                            }

                        }
                        Spacer(Modifier.height(10.dp))
                        Row{
                            Text(text = "Lieu de naissance : ", fontWeight = FontWeight.Bold)
                            if(it.place_of_birth!=null) {
                                Text(text = it.place_of_birth)
                            }
                            else{
                                Text(text = "Pas de lieu de naissance.")
                            }
                        }
                        Spacer(Modifier.height(20.dp))
                    }
                }
            }
        }
    }
}