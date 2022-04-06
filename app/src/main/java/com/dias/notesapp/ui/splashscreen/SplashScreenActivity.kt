package com.dias.notesapp.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.dias.notesapp.R
import com.dias.notesapp.ui.MainActivity

class SplashScreenActivity : AppCompatActivity(R.layout.activity_splash_screen) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // navigate to main activity
        Handler().postDelayed({
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }, 1000)
}
}