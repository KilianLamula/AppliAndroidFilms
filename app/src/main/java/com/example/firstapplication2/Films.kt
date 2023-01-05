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
import com.example.firstapplication2.*

@Composable
fun Films(viewModel: MainViewModel, navController: NavController, windowClass: WindowSizeClass){
    when (windowClass.heightSizeClass) {
        WindowHeightSizeClass.Compact -> {
            listeFilm(viewModel, navController, 4, 500)
        }

        else -> {
            listeFilm(viewModel, navController, 2, 780)
        }
    }
}

@Composable
fun listeFilm(viewModel: MainViewModel, navController: NavController, nbCol: Int, sizeImage: Int){
    val movies by viewModel.movies.collectAsState()

    var textSearch by remember { mutableStateOf(TextFieldValue("")) }

    Scaffold(
        topBar = {
            TopAppBar(contentColor = Color(0xFF651FFF)) {
                Row(Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceAround){
                    TextField(
                        value = textSearch,
                        singleLine = true,
                        maxLines = 1,
                        leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "searchIcon") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                if ((textSearch.text == "")){
                                    viewModel.getFilmsInitiaux()
                                }
                                else{
                                    viewModel.searchFilms(searchText = textSearch.text)
                                }
                            }
                        ),
                        onValueChange = { newText ->
                            textSearch = newText
                        },
                        placeholder = { Text(text = "Chercher un film") }
                    )
                    Spacer(Modifier.width(60.dp))
                    IconButton(onClick = {
                        if (textSearch.text == ""){
                            viewModel.getFilmsInitiaux()
                        }
                        else{
                            viewModel.searchFilms(searchText = textSearch.text)
                        }
                    }) {
                        Icon(Icons.Filled.Search, contentDescription = null)
                    }
                }
            }
        }
    ){ innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(nbCol),
            Modifier.padding(innerPadding)
        ) {
            items(movies){ movie ->
                Card(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(15.dp),
                    elevation = 10.dp
                ){
                    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
                        AsyncImage(
                            model = "https://image.tmdb.org/t/p/w"+sizeImage+"/"+movie.poster_path,
                            contentDescription = null
                        )
                        Text(text = movie.title, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                        Spacer(Modifier.height(10.dp))
                        Text(text = movie.release_date, fontStyle = FontStyle.Italic)
                        Spacer(Modifier.height(5.dp))
                        Button(
                            onClick = { navController.navigate("detailFilm/"+movie.id) },
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