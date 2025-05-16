package com.example.myapplication2

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class Questions : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val userName = intent.getStringExtra("userName") ?: "player" //username passed from MainActivity

            //state variable
            var isAnswered by remember {mutableStateOf(false)}
            var isCorrect by remember { mutableStateOf(false)}
            var isWrong by remember { mutableStateOf(false)}
            var currentIndex by remember { mutableStateOf(0)}
            var incorrectScore by remember { mutableStateOf(0)}
            var correctScore by remember { mutableStateOf(0)}
            var isComplete by remember { mutableStateOf(false)}
            var answersGiven by remember { mutableStateOf(mutableListOf<String>()) }
            var correctAnswersStatus by remember { mutableStateOf(mutableListOf<Int>()) }
            var wrongAnswersStatus by remember { mutableStateOf(mutableListOf<Int>()) }




            //array of questions
            data class Quest(val id: Int, val question: String)
            val  questionsArray = arrayOf(
                Quest(1, "Did you do it?"),
                Quest(2, "Did it hurt?"),
                Quest(3, "Will you be back?")
            )
            val currentQuestion = questionsArray.getOrNull(currentIndex)

            val answers = arrayOf("true", "false","false") //array of answers

            //function that updates the state variables and moves to the next question after answering
            fun btnFun(selectedAnswer: String){
                isAnswered = true //causes the overlay to display
                answersGiven.add(selectedAnswer) //tracks the users answers
                if (selectedAnswer == answers[currentIndex]){
                    isCorrect = true //updates the state
                    isWrong = false //updates the state
                    correctScore++ //add 1 point to the score
                    correctAnswersStatus.add(1) //stores correct status when a question is answered correctly
                    wrongAnswersStatus.add(0) //does not store the wrong status if correct

                }
                else {
                    isCorrect = false
                    isWrong = true
                    incorrectScore++
                    correctAnswersStatus.add(0) //does not store the correct status when wrong
                    wrongAnswersStatus.add(1) //stores wrong status for later use
                }
                //Delays the next question so the overlay does not stay on the screen
                Handler(Looper.getMainLooper()).postDelayed({
                    if(currentIndex < questionsArray.size - 1){
                        currentIndex++
                        isAnswered = false //Resets the overlay for the next question
                        isCorrect = false
                        isWrong = false
                    }else{
                        isComplete = true //makes a button appear when the quiz is done
                    }
                }, 1000) //Delays the transition between questions for 1 second



            }


            Column(
                modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                currentQuestion?.let { quest -> //iterating through the questions per index

                    OutlinedCard(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                        ),
                        border = BorderStroke(2.dp, Color.Black),
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(width = 240.dp, height = 200.dp),


                    ) {
                        Box(modifier = Modifier.fillMaxSize()){
                            Column(modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "Question ${quest.id}", //displays the number of the question
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    fontSize = 20.sp,
                                    textAlign = TextAlign.Center,
                                )
                                Text(
                                    text = quest.question, //displays the question
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    fontSize = 20.sp,
                                    textAlign = TextAlign.Center,
                                )

                                Row(modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center)

                                {
                                    Button(onClick = { btnFun("true") }) {
                                        Text("True") //button that when clicked answers true
                                    }
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Button(onClick = { btnFun("false")}) {
                                        Text("False") //button that when clicked answers false
                                    }
                                }
                            }
                            if (isAnswered){ //checks if the question is answered

                                if (isCorrect){ //checks if the answer is correct and displays text indicating so

                                        Text(
                                            text = "Correct!!",
                                            color = Color.Black,
                                            fontSize = 50.sp,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.fillMaxSize()
                                                .background(Color.Green.copy(alpha = 0.5f))
                                        )


                                } else if (isWrong){ //checks if the answer is wrong and displays a text indicating so
                                    Text(
                                        text = "That is wrong",
                                        color = Color.Black,
                                        fontSize = 50.sp,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.fillMaxSize()
                                        .background(Color.Red.copy(alpha = 0.5f))
                                    )

                                }
                            }
                        }
                    }
                    if (isComplete){ //if the questions are complete this button will go to and push this data to the results page
                        Button(onClick = {val toResultPage = Intent(this@Questions, ResultsPage::class.java)
                            toResultPage.putExtra("correctScore", correctScore)
                            toResultPage.putExtra("incorrectScore", incorrectScore)
                            toResultPage.putExtra("userName", userName)
                            toResultPage.putStringArrayListExtra("questions", ArrayList(questionsArray.map {it.question}))
                            toResultPage.putStringArrayListExtra("answers", ArrayList(answersGiven))
                            toResultPage.putIntegerArrayListExtra("correctStatus", ArrayList(correctAnswersStatus))
                            toResultPage.putIntegerArrayListExtra("wrongStatus", ArrayList(wrongAnswersStatus))
                            startActivity(toResultPage)}) {
                            Text("Results")
                        }
                    }
                        }



                    }
                }

            }


        }




