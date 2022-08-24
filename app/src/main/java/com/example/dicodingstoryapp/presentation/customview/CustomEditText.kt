package com.example.dicodingstoryapp.presentation.customview

import android.content.Context
import android.graphics.Canvas
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.dicodingstoryapp.R

class CustomEditText: AppCompatEditText {
    constructor(context: Context): super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        background = ContextCompat.getDrawable(context, R.drawable.bg_edit_text)

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) return

                when (inputType) {
                    PASSWORD -> {
                        if (s.length < 6) {
                            background = ContextCompat.getDrawable(context, R.drawable.bg_edit_text_error)
                            error = "Password length is less than 6"
                        } else {
                            background = ContextCompat.getDrawable(context, R.drawable.bg_edit_text)
                        }
                    }
                    EMAIL -> {
                        if (!Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
                            background = ContextCompat.getDrawable(context, R.drawable.bg_edit_text_error)
                            error = "Email is not valid"
                        } else {
                            background = ContextCompat.getDrawable(context, R.drawable.bg_edit_text)
                        }
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    companion object {
        private val PASSWORD = Integer.decode((InputType.TYPE_TEXT_VARIATION_PASSWORD + 1).toString())
        private val EMAIL = Integer.decode((InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS + 1).toString())
    }
}