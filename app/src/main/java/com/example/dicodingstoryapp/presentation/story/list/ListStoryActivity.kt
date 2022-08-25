package com.example.dicodingstoryapp.presentation.story.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingstoryapp.R
import com.example.dicodingstoryapp.core.Resource
import com.example.dicodingstoryapp.core.showToast
import com.example.dicodingstoryapp.databinding.ActivityListStoryBinding
import com.example.dicodingstoryapp.presentation.auth.login.LoginActivity
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
        }
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
        overridePendingTransition(R.anim.slide_up, R.anim.anim_nothing)
        startActivity(intent)
    }
}