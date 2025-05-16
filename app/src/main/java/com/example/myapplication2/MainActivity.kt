package com.example.myapplication2

import android.content.Intent
import android.media.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication2.ui.theme.MyApplication2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            //stores username
            var userName by remember { mutableStateOf("") }
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.smartbackground), // Replace with your actual image resource
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop // Ensures the image fills the entire screen
                )
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "History Lesson",
                        fontSize = 50.sp,
                    color = Color.White,
                    modifier = Modifier.offset(y = (-50).dp))
                    Text(text = "Welcome to history lesson, a flashcard game that will help you prepare for any history tests",
                        fontSize = 30.sp,
                        color = Color.White,
                        modifier = Modifier.offset(y = (-50).dp))
                    Text(text = "Take your time, you might learn a thing or two",
                        fontSize = 30.sp,
                        color = Color.White,
                        modifier = Modifier.offset(y = (-50).dp))
                    TextField(
                        value = userName,
                        onValueChange = { name -> userName = name },
                        label = { Text("What is your username?") },
                        modifier = Modifier.background(color = Color.Black), //accepts username input from user
                    )

                    Button(
                        onClick = { //goes to the next page with username when clicked
                            val nextPage = Intent(this@MainActivity, Questions::class.java)
                            nextPage.putExtra("userName", userName)
                            startActivity(nextPage)
                        }
                    ) {
                        Text("Next")
                    }
                }
            }


        }
    }
}

