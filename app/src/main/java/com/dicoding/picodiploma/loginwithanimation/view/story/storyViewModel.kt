package com.dicoding.picodiploma.loginwithanimation.view.story

import com.dicoding.picodiploma.loginwithanimation.data.StoryRepository
import androidx.lifecycle.ViewModel

class StoryViewModel(private val repository: StoryRepository) : ViewModel() {
    fun story() = repository.getStory()

}
