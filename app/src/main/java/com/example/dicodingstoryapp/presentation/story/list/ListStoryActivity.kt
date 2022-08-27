package com.example.dicodingstoryapp.presentation.story.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingstoryapp.R
import com.example.dicodingstoryapp.core.Resource
import com.example.dicodingstoryapp.core.showToast
import com.example.dicodingstoryapp.databinding.ActivityListStoryBinding
import com.example.dicodingstoryapp.domain.model.Story
import com.example.dicodingstoryapp.presentation.auth.login.LoginActivity
import com.example.dicodingstoryapp.presentation.story.add.AddStoryActivity
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

        supportActionBar?.title = getString(R.string.story)

        with (binding) {
            initBinding()
            fetchStories()
        }
    }

    private fun ActivityListStoryBinding.fetchStories() {
        viewModel.stories.observe(this@ListStoryActivity) {
            when (it) {
                is Resource.Loading -> renderLoading()
                is Resource.Error -> {
                    renderLoading(false)
                    showToast(it.message)
                }
                is Resource.Success -> renderSuccess(it.data)
            }
        }
    }

    private fun ActivityListStoryBinding.initBinding() {
        rvStory.apply {
            layoutManager = LinearLayoutManager(root.context)
            adapter = storyAdapter
        }

        fabAddStory.setOnClickListener { navigateToAddStory() }
    }

    private fun ActivityListStoryBinding.renderLoading(isLoading: Boolean = true) {
        pbStory.isVisible = isLoading
    }

    private fun ActivityListStoryBinding.renderSuccess(data: List<Story>) {
        renderLoading(false)

        tvEmptyStory.isVisible = data.isEmpty()

        if (data.isEmpty()) return
        storyAdapter.submitList(data)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = MenuInflater(this)
        inflater.inflate(R.menu.main_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                viewModel.logout()
                navigateToLogin()
                true
            }
            else -> true
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
    }

    private fun navigateToAddStory() {
        val intent = Intent(this, AddStoryActivity::class.java)
        startActivity(intent)
    }
}