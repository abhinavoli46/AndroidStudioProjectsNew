package com.example.texttospeechdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import com.example.texttospeechdemo.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var tts : TextToSpeech? = null
    private var binding : ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        tts = TextToSpeech(this,this)
        binding?.startButton?.setOnClickListener{
            if(binding?.textInputView?.text!!.isEmpty()){
                Toast.makeText(this, "Please Enter Some text before proceeding", Toast.LENGTH_SHORT).show()
            }
            else{
                speak(binding?.textInputView?.text.toString())
            }
        }


    }
    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){
            val result = tts!!.setLanguage(Locale.ENGLISH)

            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS", "LANGUAGE NOT SUPPORTED")
            }
            else{
                Log.e("TTS", "INITIALIZATION FAILED")
            }
        }

    }
    private fun speak(text:String){
        tts?.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }

    override fun onDestroy() {
        super.onDestroy()
        if(tts!= null){
            tts?.stop()
            tts?.shutdown()
        }
        binding = null
    }
}