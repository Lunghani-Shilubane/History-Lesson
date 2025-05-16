package com.example.myapplication2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class ResultsPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            //information passed in from Questions
            val correctScore = intent.getIntExtra("correctScore", 0)
            val incorrectScore = intent.getIntExtra("incorrectScore", 0)
            val userName = intent.getStringExtra("userName") ?: "player"
            val questions = intent.getStringArrayListExtra("questions") ?: arrayListOf()
            val answersGiven = intent.getStringArrayListExtra("answers") ?: arrayListOf()
            val correctStatus = intent.getIntegerArrayListExtra("correctStatus") ?: arrayListOf()

            var showAnswers by remember { mutableStateOf(false) }


            Card(
                modifier = Modifier.fillMaxWidth()
                    .padding(8.dp),
                elevation = CardDefaults.elevatedCardElevation(4.dp)
            ) {
            Column(modifier = Modifier.fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
            {
                Text(text = "Welcome to the result page ${userName}. Your results for this quiz are as follows:", //displays the username
                        modifier = Modifier
                    .padding(5.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp)

                Text(text = "You got $correctScore questions right", //shows the score for correct answers
                    modifier = Modifier
                        .padding(5.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp)

                Text(text = "You got $incorrectScore questions wrong", //shows the score for wrong answers
                    modifier = Modifier
                        .padding(5.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp)

                Button(onClick = { showAnswers = true }) {
                    Text("Click to review quiz") //updates the showAnswers state when clicked
                }

                if (showAnswers) { //if true, it iterates through the questions, indexes and answers given
                    questions.zip(answersGiven).forEachIndexed { index, (question, answer) ->

                        //lists the questions with the question number
                        Text(text = "Question ${index + 1}: $question",
                            modifier = Modifier
                                .padding(5.dp),
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp)

                        //text displaying the answer and if its correct or wrong
                        Text(text = "Your answer was: $answer and that was ${if (correctStatus[index] == 1) "Correct. Good job" else  "Incorrect. Take some time to revise and try again"}",
                            modifier = Modifier
                                .padding(5.dp),
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp)
                    }
                }
            }
                Button(onClick = {finishAffinity()}) {
                    Text("Exit the app") //button shuts down the app
                }


            }
        }
    }
}

