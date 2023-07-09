package com.example.musicplayerapp

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import java.sql.Time
import java.util.concurrent.TimeUnit

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    var startTime = 0.0
    var finishTime = 0.0
    var forwardTime = 10000
    var backwardTime = 10000
    var oneTimeOnly = 0

    var handler : Handler = Handler()
    var mediaPlayer : MediaPlayer = MediaPlayer()
    lateinit var seekBar : SeekBar
    lateinit var timeTextView : TextView




    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply{
            try{
                Thread.sleep(5000)
            }
            catch(e : Exception)
            {}
        }
        setContentView(R.layout.activity_main)


        val rewindBtn : Button = findViewById(R.id.rewind_button)
        val forwardBtn : Button = findViewById(R.id.forward_button)
        val playBtn : Button = findViewById(R.id.play_button)
        val pauseBtn : Button = findViewById(R.id.pause_button)
        seekBar = findViewById(R.id.seekBar)
        val titleTextView : TextView = findViewById(R.id.songTitleTextView)
        timeTextView = findViewById(R.id.timeLeftTextView)

        //Initializing mediaPlayer variable
        mediaPlayer = MediaPlayer.create(this,R.raw.astronaut)
        seekBar.isClickable = false

        //Adding Functionalities in Buttons
        playBtn.setOnClickListener()
        {
            mediaPlayer.start()
            finishTime = mediaPlayer.duration.toDouble()
            startTime = mediaPlayer.currentPosition.toDouble()

            if(oneTimeOnly == 0)
            {
                seekBar.max = finishTime.toInt()
                oneTimeOnly = 1
            }
            timeTextView.text = startTime.toString()
            seekBar.progress = startTime.toInt()
            handler.postDelayed(updateSongTime,100)

        }

        //Setting the music title
        titleTextView.text = resources.getResourceEntryName(R.raw.astronaut).toString()

        pauseBtn.setOnClickListener()
        {
            mediaPlayer.pause()
        }


        forwardBtn.setOnClickListener()
        {
            var temp = startTime
            if(temp + forwardTime <= finishTime)
            {
                startTime += forwardTime
                mediaPlayer.seekTo(startTime.toInt())
            }
            else
            {
                Toast.makeText(this,"Less time remaining !!",Toast.LENGTH_SHORT).show()
            }
        }

        rewindBtn.setOnClickListener()
        {
            var temp = startTime.toInt()
            if(temp - backwardTime > 0)
            {
                startTime -= backwardTime
                mediaPlayer.seekTo(startTime.toInt())
            }
            else
            {
                Toast.makeText(this,"Less time remaining !!",Toast.LENGTH_SHORT).show()
            }
        }
    }

    val updateSongTime : Runnable = object : Runnable{
        override fun run() {
            startTime = mediaPlayer.currentPosition.toDouble()
            timeTextView.text = String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes(startTime.toLong()),
                TimeUnit.MILLISECONDS.toSeconds(startTime.toLong()
                        - TimeUnit.MINUTES.toSeconds(
                    TimeUnit.MILLISECONDS.toMinutes(startTime.toLong()
                    )
                ))

            )

            seekBar.progress = startTime.toInt()
            handler.postDelayed(this,100)
        }
    }

}