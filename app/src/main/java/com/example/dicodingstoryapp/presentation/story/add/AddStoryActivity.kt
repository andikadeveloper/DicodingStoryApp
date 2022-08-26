package com.example.dicodingstoryapp.presentation.story.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dicodingstoryapp.databinding.ActivityAddStoryBinding

class AddStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}