package com.dicoding.picodiploma.loginwithanimation.data.pref

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dicoding.picodiploma.loginwithanimation.data.ResultState
import com.dicoding.picodiploma.loginwithanimation.data.api.ApiService
import com.dicoding.picodiploma.loginwithanimation.data.api.response.ErrorResponse
import com.dicoding.picodiploma.loginwithanimation.data.api.response.ListStoryItem
import com.google.gson.Gson
import retrofit2.HttpException

class MapsRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference //terpanggil di companion object
){
    fun getStoryLocation(): LiveData<ResultState<List<ListStoryItem>>> = liveData {
        emit(ResultState.Loading)
        try {
            val response = apiService.getStoriesWithLocation()
            emit(ResultState.Success(response.listStory))
        }catch (e: HttpException){
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
            emit(ResultState.Error(errorResponse.message))
        }catch (e: Exception){
            emit(ResultState.Error(e.message ?: "Error"))
        }
    }

    companion object {
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference
        ) = MapsRepository(apiService, userPreference)
    }
}