package com.dicoding.picodiploma.loginwithanimation.view.story

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityDetailStoryBinding

class DetailStoryActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        setupData()

    }
    private fun setupData(){
        val storyName = intent.getStringExtra(STORY_NAME)
        val imageStory = intent.getStringExtra(IMAGE_STORY)
        val descStory = intent.getStringExtra(DESCRIPTION)

        Glide.with(applicationContext)
            .load(imageStory)
            .transform(RoundedCorners(16))
            .into(binding.imageView)

        binding.nameTextView.text = storyName
        binding.descTextView.text = descStory
    }

    companion object {
        const val STORY_NAME = "name"
        const val IMAGE_STORY = "image"
        const val DESCRIPTION = "description"
    }
}