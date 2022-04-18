package com.langga.movieapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.langga.movieapp.databinding.ActivitySplashScreenBinding
import kotlinx.coroutines.*

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private val activityScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        activityScope.launch {
            delay(TIME)
            startActivity(Intent(this@SplashScreenActivity, DashboardActivity::class.java))
            finishAffinity()
        }
    }

    companion object {
        private const val TIME = 2000L
    }

    override fun onPause() {
        activityScope.cancel()
        super.onPause()
    }
}