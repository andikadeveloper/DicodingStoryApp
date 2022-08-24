package com.example.dicodingstoryapp.presentation.auth.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.example.dicodingstoryapp.core.showToast
import com.example.dicodingstoryapp.data.source.remote.request.AuthRequest
import com.example.dicodingstoryapp.databinding.ActivityRegisterBinding
import com.example.dicodingstoryapp.presentation.auth.login.LoginActivity
import com.example.dicodingstoryapp.presentation.customview.CustomEditText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private val viewModel by viewModels<RegisterViewModel>()
    private lateinit var binding: ActivityRegisterBinding

    private val listOfEditText = mutableListOf<CustomEditText>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with (binding) {
            initBinding()
            lifecycleScope.launchWhenStarted { setupEvent() }
        }
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

                    navigateToLogin()
                }
            }
        }
    }

    private fun ActivityRegisterBinding.initBinding() {
        addTextChangedListener()

        btnRegister.setOnClickListener { onRegister() }

        listOfEditText.addAll(listOf(
            binding.edRegisterName,
            binding.edRegisterEmail,
            binding.edRegisterPassword
        ))
    }

    private fun ActivityRegisterBinding.addTextChangedListener() {
        for (editText in listOfEditText) {
            editText.addTextChangedListener { setActiveInActiveButtonRegister() }
        }
    }

    private fun ActivityRegisterBinding.setActiveInActiveButtonRegister() {
        btnRegister.isEnabled = false

        val rules = listOf(
            !edRegisterName.text.isNullOrEmpty(),
            edRegisterEmail.error.isNullOrEmpty(),
            edRegisterPassword.error.isNullOrEmpty(),
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
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}