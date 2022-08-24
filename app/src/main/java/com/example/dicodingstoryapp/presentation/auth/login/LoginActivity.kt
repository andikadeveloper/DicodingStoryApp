package com.example.dicodingstoryapp.presentation.auth.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.example.dicodingstoryapp.core.showToast
import com.example.dicodingstoryapp.data.source.remote.request.AuthRequest
import com.example.dicodingstoryapp.databinding.ActivityLoginBinding
import com.example.dicodingstoryapp.presentation.story.list.ListStoryActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel>()

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val listOfEditText = listOf(
        binding.edLoginEmail,
        binding.edLoginPassword
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with (binding) {
            initBinding()
            lifecycleScope.launchWhenStarted { setupEvent() }
        }
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

                    navigateToListStory()
                }
            }
        }
    }

    private fun ActivityLoginBinding.initBinding() {
        addTextChangedListener()

        btnLogin.setOnClickListener { onLogin() }
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

    private fun ActivityLoginBinding.addTextChangedListener() {
        for (editText in listOfEditText) {
            editText.addTextChangedListener { setActiveInActiveButtonLogin() }
        }
    }

    private fun ActivityLoginBinding.setActiveInActiveButtonLogin() {
        btnLogin.isEnabled = false

        val rules = listOf(
            edLoginEmail.error.isNullOrEmpty(),
            edLoginPassword.error.isNullOrEmpty(),
        )

        for (isValid in rules) {
            if (!isValid) return
        }

        btnLogin.isEnabled = true
    }

    private fun ActivityLoginBinding.renderLoading(isLoading: Boolean = true) {
        pbLogin.isVisible = isLoading
    }

    private fun navigateToListStory() {
        val intent = Intent(this, ListStoryActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}