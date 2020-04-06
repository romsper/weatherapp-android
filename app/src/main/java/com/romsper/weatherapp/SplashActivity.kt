package com.romsper.weatherapp

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.romsper.weatherapp.screen.MainActivity
import kotlinx.android.synthetic.main.activity_splashscreen.*


class SplashActivity : AppCompatActivity() {
    private lateinit var weatherIconAnimation: AnimationDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        val animation = splash_weather_icon as ImageView
        animation.setBackgroundResource(R.drawable.animation_weather_icon)
        val weatherIconAnimation = animation.background as AnimationDrawable
        weatherIconAnimation.start()


        splash_weather_icon.setOnClickListener {
            weatherIconAnimation.stop()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
