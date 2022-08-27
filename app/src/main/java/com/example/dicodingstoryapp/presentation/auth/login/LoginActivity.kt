package com.example.dicodingstoryapp.presentation.auth.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.example.dicodingstoryapp.core.isValidEmail
import com.example.dicodingstoryapp.core.showToast
import com.example.dicodingstoryapp.data.source.remote.request.AuthRequest
import com.example.dicodingstoryapp.databinding.ActivityLoginBinding
import com.example.dicodingstoryapp.presentation.auth.register.RegisterActivity
import com.example.dicodingstoryapp.presentation.story.list.ListStoryActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel>()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with (binding) {
            initBinding()
            setupAnimation()
            lifecycleScope.launchWhenStarted { setupEvent() }
        }

        supportActionBar?.hide()
    }

    private suspend fun ActivityLoginBinding.setupEvent() {
        viewModel.event.collect {
            when (it) {
                is UiEvent.Loading -> renderLoading()
                is UiEvent.Error -> {
                    renderLoading(false)
                    showToast(it.message)
                }
                is UiEvent.Success -> {
                    renderLoading(false)
                    showToast(it.message)

                    navigateToListStory()
                }
            }
        }
    }

    private fun ActivityLoginBinding.initBinding() {
        edLoginEmail.addTextChangedListener { setActiveInActiveButtonLogin() }

        edLoginPassword.addTextChangedListener { setActiveInActiveButtonLogin() }

        btnLogin.setOnClickListener { onLogin() }

        tvRegister.setOnClickListener { navigateToRegister() }
    }

    private fun ActivityLoginBinding.onLogin() {
        val email = edLoginEmail.text.toString().trim()
        val password = edLoginPassword.text.toString().trim()

        val payload = AuthRequest(
            email = email,
            password = password,
        )

        viewModel.login(payload)
    }

    private fun ActivityLoginBinding.setActiveInActiveButtonLogin() {
        btnLogin.isEnabled = false

        val email = edLoginEmail.text.toString().trim()
        val password = edLoginPassword.text.toString().trim()

        val rules = listOf(
            email.isNotEmpty(),
            password.isNotEmpty(),
            email.isValidEmail(),
            password.length >= 6
        )

        for (isValid in rules) {
            if (!isValid) return
        }

        btnLogin.isEnabled = true
    }

    private fun ActivityLoginBinding.renderLoading(isLoading: Boolean = true) {
        pbLogin.isVisible = isLoading
        btnLogin.isEnabled = !isLoading
    }

    private fun ActivityLoginBinding.setupAnimation() {
        val objImgLogin = ObjectAnimator.ofFloat(ivLogin, View.TRANSLATION_Y, 0f).setDuration(500)

        val objEdEmail = ObjectAnimator.ofFloat(edLoginEmail, View.ALPHA, 1f).setDuration(500)
        val objEdPassWord = ObjectAnimator.ofFloat(edLoginPassword, View.ALPHA, 1f).setDuration(500)

        val objTvNotHaveAccount = ObjectAnimator.ofFloat(tvNotHaveAccount, View.ALPHA, 1f).setDuration(500)
        val objTvRegister = ObjectAnimator.ofFloat(tvRegister, View.ALPHA, 1f).setDuration(500)

        val objBtnLogin = ObjectAnimator.ofFloat(btnLogin, View.TRANSLATION_Y, 0f).setDuration(500)

        AnimatorSet().apply {
            play(objEdEmail).after(objImgLogin)
            play(objEdPassWord).after(objEdEmail)
            play(objTvNotHaveAccount).with(objTvRegister)
            play(objTvNotHaveAccount).after(objEdPassWord)
            play(objBtnLogin).after(objTvNotHaveAccount)
            start()
        }
    }

    private fun navigateToListStory() {
        val intent = Intent(this, ListStoryActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}