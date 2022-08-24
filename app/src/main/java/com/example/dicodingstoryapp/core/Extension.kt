package com.example.dicodingstoryapp.core

import android.util.Patterns
import android.widget.Toast
import androidx.viewbinding.ViewBinding

fun ViewBinding.showToast(message: String) {
    Toast.makeText(root.context, message, Toast.LENGTH_SHORT).show()
}

fun String.isValidEmail(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}