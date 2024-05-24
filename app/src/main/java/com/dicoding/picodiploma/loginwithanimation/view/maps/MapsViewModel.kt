package com.dicoding.picodiploma.loginwithanimation.view.maps

import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.loginwithanimation.data.pref.MapsRepository

class MapsViewModel(private val repository: MapsRepository): ViewModel() {
    fun storyWithLocation() = repository.getStoryLocation()
}