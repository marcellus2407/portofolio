package com.dicoding.picodiploma.storyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.storyapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    companion object {
        const val DETAIL_ACTIVITY = "detail_activity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val stories = intent.getParcelableExtra<StoryList>(DETAIL_ACTIVITY) as StoryList
        Glide.with(this)
            .load(stories.photo)
            .into(binding.imgDetail)
        binding.tvNameDetail.text = stories.name
        binding.tvDescriptionDetail.text = stories.description

    }

}