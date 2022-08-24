package com.example.dicodingstoryapp.core

import android.widget.Toast
import androidx.viewbinding.ViewBinding

fun ViewBinding.showToast(message: String) {
    Toast.makeText(root.context, message, Toast.LENGTH_SHORT).show()
}