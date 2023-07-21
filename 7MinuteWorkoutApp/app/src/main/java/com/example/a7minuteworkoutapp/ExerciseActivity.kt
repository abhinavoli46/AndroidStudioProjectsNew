package com.example.a7minuteworkoutapp

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.a7minuteworkoutapp.databinding.ActivityExerciseBinding
import java.util.Locale

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var binding : ActivityExerciseBinding? = null
    private var textToSpeech: TextToSpeech? = null

    private var restTimer : CountDownTimer? = null
    private var restProgress : Int = 0
    private var totalTimeForRest = 3

    private var exerciseTimer:CountDownTimer? = null
    private var exerciseProgress:Int = 0
    private var totalTimeForExercise = 5

    private var exerciseList : ArrayList<ExerciseModel>? = null
    private var currentExercise = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //view binding
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        textToSpeech = TextToSpeech(this,this)
        setContentView(binding?.root)

        //Settings for toolbar and status bar
        setSupportActionBar(binding?.toolbarExercise)
        this.window.statusBarColor = this.resources.getColor(R.color.black)

        //Getting the list of ExerciseModel objects from Constants object file into ExerciseList
        exerciseList = Constants.defaultExerciseList()


        //Setting timer textview to total rest time allocated
        binding?.timerForRest?.text = totalTimeForRest.toString()
        binding?.backButton?.setOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }

        //Start the activity by setting up the rest timer view
        setUpRestView()

    }

    //Function to set up rest timer view
    private fun setUpRestView() {
        //Make exercise timer and exercise name textview invisible
        binding?.exerciseImageView?.visibility = View.INVISIBLE
        binding?.frameTimerForExercise?.visibility = View.INVISIBLE
        binding?.tvExerciseName?.visibility = View.INVISIBLE

        //Make rest timer and textview visible
        binding?.frameTimerForRest?.visibility = View.VISIBLE
        binding?.textviewtitle?.visibility = View.VISIBLE
        binding?.tvNextExercise?.visibility = View.VISIBLE
        binding?.tvupcoming?.visibility = View.VISIBLE



        //First check if timer is not reset before starting the activity
        //If timer is not reset, first reset that then  set up the ProgressBar
        if(restTimer != null)
        {
            restTimer?.cancel()
            restProgress = 0
        }
        speakOut("Be ready")
        //Updating Next Exercise Name
        binding?.tvNextExercise?.text = exerciseList!![currentExercise+1].getName()

        //Call the Rest Timer
        setRestProgressBar()
    }
    //Function to set up exercise view after rest timer expires
    private fun setUpExerciseView() {

        //Make rest timer and textview invisible
        binding?.frameTimerForRest?.visibility = View.INVISIBLE
        binding?.textviewtitle?.visibility = View.INVISIBLE
        binding?.tvNextExercise?.visibility = View.INVISIBLE
        binding?.tvupcoming?.visibility = View.INVISIBLE

        //Make exercise timer and exercise name textview visible
        binding?.tvExerciseName?.visibility = View.VISIBLE
        binding?.frameTimerForExercise?.visibility = View.VISIBLE
        binding?.exerciseImageView?.visibility = View.VISIBLE


        //First check if timer is not reset before starting the activity
        //If timer is not reset, first reset that then  set up the ProgressBar
        if(exerciseTimer != null)
        {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }
        //speaking the exercise name
        speakOut(exerciseList!![currentExercise].getName())
        //Set Up Image, name of exercise and call the Start timer
        binding?.exerciseImageView?.setImageResource(exerciseList!![currentExercise].getImage())
        binding?.tvExerciseName?.text = exerciseList!![currentExercise].getName()
        setExerciseProgressBar()
    }

    //Functionality of Rest Timer
    private fun setRestProgressBar(){
        binding?.progressBarForRest?.progress = restProgress

        restTimer = object : CountDownTimer(3000, 1000){
            override fun onTick(p0: Long) {
                //increase the rest taken every second
                restProgress++
                //Set progress bar and textview according to rest remaining
                binding?.progressBarForRest?.progress = totalTimeForRest - restProgress
                binding?.timerForRest?.text = (totalTimeForRest - restProgress).toString()
            }

            override fun onFinish() {
                currentExercise++
                setUpExerciseView()
            }

        }.start()
    }
    //Functionality of Exercise Timer
    private fun setExerciseProgressBar(){
        binding?.progressBarForExercise?.progress = exerciseProgress

        exerciseTimer = object : CountDownTimer(5000, 1000){
            override fun onTick(p0: Long) {
                //increase the exercise timer every second
                exerciseProgress++
                //Set progress bar and textview according to exercise time remaining
                binding?.progressBarForExercise?.progress = totalTimeForExercise - exerciseProgress
                binding?.timerForExercise?.text = (totalTimeForExercise - exerciseProgress).toString()
            }

            override fun onFinish() {
                if(currentExercise < exerciseList?.size!!-1) {
                    setUpRestView()
                }
                    else{
                        Toast.makeText(this@ExerciseActivity,
                        "Congratulations",
                        Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@ExerciseActivity,MainActivity::class.java))
                }
            }

        }.start()
    }
    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){
            val result = textToSpeech!!.setLanguage(Locale.UK)

            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS", "LANGUAGE NOT SUPPORTED")
            }
            else{
                Log.e("TTS", "INITIALIZATION FAILED")
            }
        }
    }

    private fun speakOut(text : String){
        textToSpeech!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }

    override fun onDestroy() {
        super.onDestroy()
        if(restTimer != null){
            restTimer?.cancel()
            restProgress = 0
        }
        if(exerciseTimer != null){
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }
        if(textToSpeech!=null){
            textToSpeech!!.stop()
            textToSpeech!!.shutdown()
        }
        binding = null
    }


}