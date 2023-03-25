package com.example.musicplayerapp

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.widget.AppCompatImageButton

class MainActivity : AppCompatActivity() {

    private lateinit var seek: SeekBar
    private lateinit var mediaPlayer: MediaPlayer
    private var currentSongIndex = 0

    private val songs = intArrayOf(R.raw.sundays, R.raw.smoke, R.raw.waterfall)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val previousBtn: AppCompatImageButton = findViewById(R.id.previous)
        val playBtn: AppCompatImageButton = findViewById(R.id.play)
        val nextBtn: AppCompatImageButton = findViewById(R.id.next)
        seek = findViewById(R.id.seek_bar)

        mediaPlayer = MediaPlayer.create(this, songs[currentSongIndex])

        previousBtn.setOnClickListener{
            if (mediaPlayer.isPlaying) {
                mediaPlayer.reset() // stops the current song
            }
            currentSongIndex = if (currentSongIndex == 0) {
                songs.size - 1
            } else {
                currentSongIndex - 1
            }
            mediaPlayer = MediaPlayer.create(this, songs[currentSongIndex])
            mediaPlayer.start() // starts the current song without the overlap of the previous song
        }

        playBtn.setOnClickListener{
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
                playBtn.setImageResource(R.drawable.play)
            } else {
                mediaPlayer.start()
                playBtn.setImageResource(R.drawable.pause)
            }
            initializeSeekBar()
        }

        nextBtn.setOnClickListener{
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop() // .stop() stops the previous song
            }
            currentSongIndex = (currentSongIndex + 1) % songs.size
            mediaPlayer = MediaPlayer.create(this, songs[currentSongIndex])
            mediaPlayer.start()
        }
    }

    private fun initializeSeekBar(){
        seek.max = mediaPlayer.duration
        val updateSeekBar = object : Runnable {
            override fun run() {
                val currentPosition = mediaPlayer.currentPosition
                seek.progress = currentPosition
                seek.postDelayed(this, 1000)
            }
        }
        seek.postDelayed(updateSeekBar, 100)
    }

    override fun onStop() {
        super.onStop()
        if (mediaPlayer!= null){
            mediaPlayer.stop()
        }
    }
}
