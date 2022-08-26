package com.example.dicodingstoryapp.presentation.story.list.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.dicodingstoryapp.R
import com.example.dicodingstoryapp.core.withDateFormat
import com.example.dicodingstoryapp.databinding.ItemStoryBinding
import com.example.dicodingstoryapp.domain.model.Story
import com.example.dicodingstoryapp.presentation.story.detail.DetailStoryActivity

class StoryAdapter: ListAdapter<Story, StoryAdapter.StoryViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        return StoryViewHolder(
            ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val story = getItem(position)

        holder.binding.bind(story, position)
    }

    private fun ItemStoryBinding.bind(story: Story, position: Int) {
        divider.isVisible = position != itemCount - 1

        story.apply {
            ivItemPhoto.load(photoUrl) {
                placeholder(R.drawable.img_empty_story)
                error(R.drawable.img_empty_story)
            }
            tvItemName.text = name
            tvItemDate.text = createdAt.withDateFormat()
        }

        root.setOnClickListener { navigateToDetailStory(story) }
    }

    private fun ItemStoryBinding.navigateToDetailStory(story: Story) {
        val intent = Intent(root.context, DetailStoryActivity::class.java).apply {
            putExtra(DetailStoryActivity.EXTRA_STORY, story)
        }
        root.context.startActivity(intent)
    }

    inner class StoryViewHolder(
        val binding: ItemStoryBinding
    ): RecyclerView.ViewHolder(binding.root)

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Story>() {
            override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem == newItem
            }
        }
    }
}