package com.example.dicodingstoryapp.presentation.story.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingstoryapp.R
import com.example.dicodingstoryapp.core.Resource
import com.example.dicodingstoryapp.core.showToast
import com.example.dicodingstoryapp.databinding.ActivityListStoryBinding
import com.example.dicodingstoryapp.presentation.story.list.adapter.StoryAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListStoryActivity : AppCompatActivity() {
    private val viewModel by viewModels<ListStoryViewModel>()
    private lateinit var binding: ActivityListStoryBinding
    private val storyAdapter = StoryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.your_story)

        with (binding) {
            initBinding()
            fetchStories()
        }
    }

    private fun ActivityListStoryBinding.fetchStories() {
        viewModel.stories.observe(this@ListStoryActivity) {
            when (it) {
                is Resource.Loading -> {
                    showToast("Loading")
                }
                is Resource.Error -> {
                    showToast(it.message)
                }
                is Resource.Success -> {
                    storyAdapter.submitList(it.data)
                }
            }
        }
    }

    private fun ActivityListStoryBinding.initBinding() {
        rvStory.apply {
            layoutManager = LinearLayoutManager(root.context)
            adapter = storyAdapter
            setHasFixedSize(true)
        }
    }
}