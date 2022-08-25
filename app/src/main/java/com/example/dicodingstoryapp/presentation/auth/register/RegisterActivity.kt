package com.example.dicodingstoryapp.presentation.auth.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.example.dicodingstoryapp.core.isValidEmail
import com.example.dicodingstoryapp.core.showToast
import com.example.dicodingstoryapp.data.source.remote.request.AuthRequest
import com.example.dicodingstoryapp.databinding.ActivityRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private val viewModel by viewModels<RegisterViewModel>()
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with (binding) {
            initBinding()
            lifecycleScope.launchWhenStarted { setupEvent() }
        }

        supportActionBar?.hide()
    }

    private suspend fun ActivityRegisterBinding.setupEvent() {
        viewModel.event.collect {
            when (it) {
                is UiEvent.Error -> {
                    renderLoading(false)

                    showToast(it.message)
                }
                is UiEvent.Loading -> renderLoading()
                is UiEvent.Success -> {
                    renderLoading(false)
                    showToast(it.message)

                    navigateToLogin()
                }
            }
        }
    }

    private fun ActivityRegisterBinding.initBinding() {
        edRegisterName.addTextChangedListener { setActiveInActiveButtonRegister() }
        edRegisterEmail.addTextChangedListener { setActiveInActiveButtonRegister() }
        edRegisterPassword.addTextChangedListener { setActiveInActiveButtonRegister() }

        btnRegister.setOnClickListener { onRegister() }
    }

    private fun ActivityRegisterBinding.setActiveInActiveButtonRegister() {
        btnRegister.isEnabled = false

        val name = edRegisterName.text.toString().trim()
        val email = edRegisterEmail.text.toString().trim()
        val password = edRegisterPassword.text.toString().trim()

        val rules = listOf(
            name.isNotEmpty(),
            email.isNotEmpty(),
            password.isNotEmpty(),
            email.isValidEmail(),
            password.length >= 6,
        )

        for (isValid in rules) {
            if (!isValid) return
        }

        btnRegister.isEnabled = true
    }

    private fun ActivityRegisterBinding.onRegister() {
        val name = edRegisterName.text.toString().trim()
        val email = edRegisterEmail.text.toString().trim()
        val password = edRegisterPassword.text.toString().trim()

        val payload = AuthRequest(
            name = name,
            email = email,
            password = password,
        )

        viewModel.register(payload)
    }

    private fun ActivityRegisterBinding.renderLoading(isLoading: Boolean = true) {
        pbRegister.isVisible = isLoading
    }

    private fun navigateToLogin() {
        finish()
    }
}