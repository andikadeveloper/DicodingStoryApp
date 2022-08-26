package com.example.dicodingstoryapp.presentation.story.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coil.load
import com.example.dicodingstoryapp.R
import com.example.dicodingstoryapp.core.withDateFormat
import com.example.dicodingstoryapp.databinding.ActivityDetailStoryBinding
import com.example.dicodingstoryapp.domain.model.Story

class DetailStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val story = intent.getParcelableExtra<Story>(EXTRA_STORY)
        story?.let { binding.bindStory(it) }
    }

    private fun ActivityDetailStoryBinding.bindStory(story: Story) {
        story.apply {
            ivDetailPhoto.load(photoUrl) {
                placeholder(R.drawable.img_empty_story)
                error(R.drawable.img_empty_story)
            }
            tvDetailName.text = name
            tvDetailDescription.text = description
            tvDetailDate.text = createdAt.withDateFormat()
        }
    }

    companion object {
        const val EXTRA_STORY = "extra_story"
    }
}