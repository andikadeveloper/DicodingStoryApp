package com.example.dicodingstoryapp.presentation.story.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dicodingstoryapp.R
import com.example.dicodingstoryapp.domain.model.Story

class DetailStoryActivity : AppCompatActivity() {
    private var story: Story? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_story)

        story = intent.getParcelableExtra(EXTRA_STORY)
    }

    companion object {
        const val EXTRA_STORY = "extra_story"
    }
}