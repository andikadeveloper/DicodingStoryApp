package com.example.dicodingstoryapp.presentation.story.list.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
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

        val optionsCompat = getOptionsCompat()

        root.context.startActivity(intent, optionsCompat.toBundle())
    }

    private fun ItemStoryBinding.getOptionsCompat(): ActivityOptionsCompat {
        return ActivityOptionsCompat.makeSceneTransitionAnimation(
            root.context as Activity,
            Pair(ivUserPhoto, getString(R.string.story_user_photo_transition)),
            Pair(tvItemName, getString(R.string.story_name_transition)),
            Pair(tvItemDate, getString(R.string.story_date_transition)),
            Pair(ivItemPhoto, getString(R.string.story_photo_transition))
        )
    }

    private fun ItemStoryBinding.getString(id: Int): String {
        return root.context.getString(id)
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