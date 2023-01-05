package com.example.firstapplication2

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun Home(windowClass: WindowSizeClass, navController: NavController){
    when (windowClass.heightSizeClass) {
        WindowHeightSizeClass.Compact -> {
            Row(Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceAround){
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
                    MonImage()
                    MonNom("Kilian Lamula")

                }
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
                    MonContact()
                    Spacer(Modifier.height(30.dp))
                    Bouton(navController)
                }
            }
        }
        else -> {
            Column(Modifier.fillMaxSize(),horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
                MonImage()
                MonNom("Kilian Lamula")
                Spacer(Modifier.height(70.dp))
                MonContact()
                Spacer(Modifier.height(130.dp))
                Bouton(navController)
            }
        }
    }
}

@Composable
fun MonNom(name: String){
    Text(text = "Cin' Actu", style = MaterialTheme.typography.h4, modifier = Modifier.padding(10.dp), fontWeight = FontWeight.Bold, fontSize = 30.sp, color = Color(0xFF651FFF))
    Text(text = "$name", style = MaterialTheme.typography.h4, modifier = Modifier.padding(5.dp), fontWeight = FontWeight.Bold, fontSize = 20.sp)
    Text("Etudiant en informatique de la santé", style = MaterialTheme.typography.h4)
    Text("Ecole d'ingénieur ISIS - INU Champollion", fontStyle = FontStyle.Italic, style = MaterialTheme.typography.h4)
}

@Composable
fun MonImage(){
    Column(modifier = Modifier.padding(15.dp)) {
        Image(
            painterResource(R.drawable.rengoku),
            contentDescription = "rengoku",
            modifier = Modifier.clip(CircleShape)
        )
    }
}

@Composable
fun LogoContact(image: Int, desc: String){
    Image(
        painterResource(id = image),
        contentDescription = desc,
        modifier = Modifier.size(25.dp)
    )
}

@Composable
fun MonContact(){
    Column(horizontalAlignment = Alignment.CenterHorizontally){
        Row{
            LogoContact(R.drawable.logomail, "Logo mail")
            Spacer(Modifier.width(5.dp))
            Text("lamula308ii@gmail.com", style = MaterialTheme.typography.h4)
        }
        Row{
            LogoContact(R.drawable.logolinkedin, "Logo LinkedIn")
            Spacer(Modifier.width(5.dp))
            Text("www.linkedin.com/in/kilian-lamula", style = MaterialTheme.typography.h4)
        }
    }
}

@Composable
fun Bouton(navController: NavController){
    Button(
        onClick = { navController.navigate("films") },
        border = BorderStroke(width = 1.dp, brush = SolidColor(Color.DarkGray)),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF651FFF)),
        shape = RoundedCornerShape(50),
        elevation =  ButtonDefaults.elevation(
            defaultElevation = 8.dp,
            pressedElevation = 10.dp,
            disabledElevation = 0.dp
        )
    )
    {
        Text("Démarrer", color = Color.White)
    }
}