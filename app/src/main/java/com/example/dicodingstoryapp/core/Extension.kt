package com.example.dicodingstoryapp.core

import android.util.Patterns
import android.widget.Toast
import androidx.viewbinding.ViewBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun ViewBinding.showToast(message: String) {
    Toast.makeText(root.context, message, Toast.LENGTH_SHORT).show()
}

fun String.isValidEmail(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.withDateFormat(): String {
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
    val date = format.parse(this) as Date

    return DateFormat.getDateInstance(DateFormat.FULL).format(date)
}