package com.dicoding.picodiploma.loginwithanimation.view.story

import androidx.lifecycle.LiveData
import com.dicoding.picodiploma.loginwithanimation.data.StoryRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.picodiploma.loginwithanimation.data.api.response.ListStoryItem

class StoryViewModel(private val repository: StoryRepository) : ViewModel() {
    val story: LiveData<PagingData<ListStoryItem>> = repository.getStory().cachedIn(viewModelScope)

}
