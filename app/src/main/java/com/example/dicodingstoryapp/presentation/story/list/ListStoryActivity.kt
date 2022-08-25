package com.example.dicodingstoryapp.presentation.story.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dicodingstoryapp.R

class ListStoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_story)

        supportActionBar?.title = getString(R.string.your_story)
    }
}