package com.romsper.weatherapp.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.romsper.weatherapp.ui.viewModel.AuthorizationViewModel
import com.romsper.weatherapp.R
import kotlinx.android.synthetic.main.activity_authorization.*
import kotlinx.android.synthetic.main.activity_splashscreen.linear_splash

class AuthorizationActivity : AppCompatActivity() {
    lateinit var authorizationViewModel: AuthorizationViewModel
    lateinit var snackbar: Snackbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)

        authorizationViewModel = ViewModelProvider(this).get(AuthorizationViewModel::class.java)

        snackbar = Snackbar.make(linear_splash, "Wrong email or password", Snackbar.LENGTH_LONG)

        btn_splash_login.setOnClickListener {
            login()
        }

        btn_skip_auth.setOnClickListener {
            try {
                startActivity(Intent(this, MainActivity::class.java).putExtra("popup", true))
            } finally {
                finish()
            }

        }
    }

    private fun login() {
        val isSuccess = authorizationViewModel.login(
            login = edit_splash_login.text.toString(),
            pass = edit_splash_password.text.toString())

        when (isSuccess) {
            true -> {
                startActivity(Intent(this, MainActivity::class.java))
            }
            false -> {
                snackbar
                    .setAction("CLOSE", SnackListener())
                    .show()
            }
        }
    }

    inner class SnackListener : View.OnClickListener {
        override fun onClick(v: View?) {
        }
    }
}
