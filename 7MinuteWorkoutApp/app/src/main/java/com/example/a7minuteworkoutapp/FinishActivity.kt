package com.example.a7minuteworkoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import com.example.a7minuteworkoutapp.databinding.ActivityFinishBinding
import java.util.Locale

class FinishActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    var binding:ActivityFinishBinding? = null
    var textToSpeech:TextToSpeech?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        textToSpeech = TextToSpeech(this,this)

        binding?.finishButton?.setOnClickListener{
            speak("well done")
            startActivity(Intent(this@FinishActivity,MainActivity::class.java))

        }
    }

    private fun speak(text: String) {
        textToSpeech?.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){
            val result = textToSpeech!!.setLanguage(Locale.CHINA)

            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS", "LANGUAGE NOT SUPPORTED")
            }
            else{
                Log.e("TTS", "INITIALIZATION FAILED")
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        if(textToSpeech!=null){
            textToSpeech!!.stop()
            textToSpeech!!.shutdown()
        }
        binding = null

    }


}