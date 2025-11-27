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
    // Estado para guardar el nombre y el saludo
    var name by remember { mutableStateOf("") }
    var greeting by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.White,
        bottomBar = {
            if (greeting.isNotEmpty()) {
                Surface(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = greeting,
                        modifier = Modifier.padding(16.dp),
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(56.dp))

            Text(
                text = "TROOPER",
                fontSize = 38.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(120.dp))

            Text(
                text = "Welcome space-traveler!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(36.dp))
            Text(
                text = "R_U ready for the force?",
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(56.dp))
            Image(
                painter = painterResource(id = R.drawable.logodarth),
                contentDescription = "darthVader Image",
                modifier = Modifier.size(120.dp)
            )
            Spacer(modifier = Modifier.height(56.dp))

            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Enter your spacial-name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color(0xFFF5F5F5),
                    focusedIndicatorColor = Color(0xFF6200EE),
                    unfocusedIndicatorColor = Color.Gray,
                    cursorColor = Color.Magenta,
                    focusedLabelColor = Color(0xFF6200EE),
                    unfocusedLabelColor = Color.DarkGray
                )

            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    greeting = "Welcome aboard, $name! 🚀"
                    onStartClick(name)
                          },
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(2.dp, Color.Black),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFEEEEEE),
                    contentColor = Color.Black,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.DarkGray,
                )
            ) {
                Text("Lift Off!")
            }
            Spacer(modifier = Modifier.height(180.dp))

            Text(
                text = "Powered by BlackDragon",
                fontSize = 15.sp,
                style = MaterialTheme.typography.titleLarge, // 👈 estilo predefinido del tema
                color = Color.Gray,           // color personalizado
                fontWeight = FontWeight.Bold,
            )
        }
    }
}