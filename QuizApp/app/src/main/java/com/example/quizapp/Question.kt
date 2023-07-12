package com.example.quizapp

//This is simple data class that contains the structure of our question
data class Question(
                    var id : Int,
                    var question : String,
                    var image : Int,
                    var optionOne:String,
                    var optionTwo: String,
                    var optionThree: String,
                    var optionFour: String,
                    var correctAns: Int
                    )
