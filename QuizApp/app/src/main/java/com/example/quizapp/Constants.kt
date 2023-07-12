package com.example.quizapp


//This contains the list of Questions and is called in MainActivity.kt
object Constants {
    //Keys
    const val USER_NAME : String = "User_Name"
    const val TOTAL_QUESTIONS : String = "Total_Questions"
    const val CORRECT_ANSWERS : String = "Correct_Answers"


    fun getQuestions() : ArrayList<Question>
    {
        val quesList = ArrayList<Question>()
        val ques1 : Question = Question(
            1, "Guess the country!",R.drawable.ic_flag_of_australia,
            "India","Australia","USA","Israel",
            2
        )
        quesList.add(ques1)
        val ques2 : Question = Question(
            2, "Guess the country!",R.drawable.ic_flag_of_argentina,
            "Argentina","Australia","Russia","Israel",
            1
        )
        quesList.add(ques2)
        val ques3 : Question = Question(
            3, "Guess the country!",R.drawable.ic_flag_of_belgium,
            "India","Belgium","USA","Pakistan",
            2
        )
        quesList.add(ques3)
        val ques4 : Question = Question(
            4, "Guess the country!",R.drawable.ic_flag_of_brazil,
            "Brazil","Belgium","USA","Canada",
            1
        )
        quesList.add(ques4)
        val ques5 : Question = Question(
            5, "Guess the country!",R.drawable.ic_flag_of_fiji,
            "Fiji","Denmark","SriLanka","Israel",
            1
        )
        quesList.add(ques5)
        val ques6 : Question = Question(
            6, "Guess the country!",R.drawable.ic_flag_of_denmark,
            "Nepal","Switzerland","Denmark","Fiji",
            3
        )
        quesList.add(ques6)
        val ques7 : Question = Question(
            7, "Guess the country!",R.drawable.ic_flag_of_germany,
            "Saudi Arabia","Bhutan","Germany","Portugal",
            3
        )
        quesList.add(ques7)
        val ques8 : Question = Question(
            8, "Guess the country!",R.drawable.ic_flag_of_india,
            "India","Iran","China","Russia",
            1
        )
        quesList.add(ques8)
        val ques9 : Question = Question(
            9, "Guess the country!",R.drawable.ic_flag_of_kuwait,
            "Baharin","Congo","USA","Kuwait",
            4
        )
        quesList.add(ques9)
        val ques10 : Question = Question(
            10, "Guess the country!",R.drawable.ic_flag_of_new_zealand,
            "Austalia","NewZealand","Ireland","Netherlands",
            2
        )
        quesList.add(ques10)
        return quesList
    }
}