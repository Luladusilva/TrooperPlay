package com.example.trooperplay.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trooperplay.R

@Composable
fun StartScreen(onStartClick: (String) -> Unit) {

    var name by remember { mutableStateOf("") }
    var greeting by remember { mutableStateOf("") }

    val purple = Color(0xFF6200EE)
    val lightGray = Color(0xFFF3F0FF)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.White,
        bottomBar = {
            if (greeting.isNotEmpty()) {
                Surface(
                    color = purple,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = greeting,
                        modifier = Modifier.padding(16.dp),
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // -- TITULO PRINCIPAL --
            Text(
                text = "TROOPER",
                fontSize = 44.sp,
                fontWeight = FontWeight.ExtraBold,
                color = purple
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Welcome space-traveler!",
                fontSize = 26.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "R_U ready for the force?",
                fontSize = 18.sp,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(30.dp))

            // -- IMAGEN CENTRAL --
            Image(
                painter = painterResource(id = R.drawable.logodarth),
                contentDescription = "DarthVader Logo",
                modifier = Modifier.size(110.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            // -- TEXTFIELD PERSONALIZADO --
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Enter your spacial-name") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.Black
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = lightGray,
                    focusedIndicatorColor = purple,
                    unfocusedIndicatorColor = Color.Gray,
                    cursorColor = purple,
                    focusedLabelColor = purple,
                    unfocusedLabelColor = Color.Gray
                )
            )

            Spacer(modifier = Modifier.height(26.dp))

            // -- BOTON PRINCIPAL --
            Button(
                onClick = {
                    greeting = "Welcome aboard, $name! 🚀"
                    onStartClick(name)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = purple,
                    contentColor = Color.White
                )
            ) {
                Text(
                    "Lift Off!",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Powered by BlackDragon",
                fontSize = 13.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
