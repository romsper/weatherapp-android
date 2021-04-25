package com.romsper.weatherapp.ui.view

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.romsper.weatherapp.R
import kotlinx.android.synthetic.main.activity_splashscreen.*

class SplashActivity : AppCompatActivity() {
    lateinit var weatherIconAnimation: AnimationDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        val animation = splash_weather_icon as ImageView
        animation.setBackgroundResource(R.drawable.animation_weather_icon)
        weatherIconAnimation = animation.background as AnimationDrawable
        weatherIconAnimation.start()

        try {
            startActivity(Intent(this, AuthorizationActivity::class.java))
        } finally {
            finish()
        }
    }

    override fun onPause() {
        super.onPause()
        weatherIconAnimation.stop()
    }
}
