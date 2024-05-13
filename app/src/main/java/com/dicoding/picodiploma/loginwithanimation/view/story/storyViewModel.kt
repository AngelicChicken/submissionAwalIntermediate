package com.dicoding.picodiploma.loginwithanimation.view.story

import com.dicoding.picodiploma.loginwithanimation.data.StroyRepository
import androidx.lifecycle.ViewModel
class StoryViewModel(private val repository: StroyRepository) : ViewModel() {
    fun story() = repository.getStory()

}
