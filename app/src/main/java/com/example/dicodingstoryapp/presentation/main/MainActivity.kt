package com.example.dicodingstoryapp.presentation.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.example.dicodingstoryapp.R
import com.example.dicodingstoryapp.presentation.auth.login.LoginActivity
import com.example.dicodingstoryapp.presentation.story.list.ListStoryActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val isLogin = viewModel.token.isNotEmpty()

        initNavigation(isLogin)
    }

    private fun initNavigation(isLogin: Boolean) {
        Handler(Looper.getMainLooper()).postDelayed({
            var intent = Intent(this, ListStoryActivity::class.java)

            if (!isLogin) {
                intent = Intent(this, LoginActivity::class.java)
            }
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }, ONE_SECOND)
    }

    companion object {
        private const val ONE_SECOND = 1 * 1000L
    }
}