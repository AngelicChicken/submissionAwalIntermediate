package com.dicoding.picodiploma.loginwithanimation.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dicoding.picodiploma.loginwithanimation.data.Api.ApiService
import com.dicoding.picodiploma.loginwithanimation.data.api.response.ErrorResponse
import com.dicoding.picodiploma.loginwithanimation.data.api.response.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserPreference
import com.google.gson.Gson
import retrofit2.HttpException

class StroyRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {
    fun getStory(): LiveData<ResultState<List<ListStoryItem>>> = liveData{
        emit(ResultState.Loading)
        try{
            val response = apiService.getStories()
            emit(ResultState.Success(response.listStory))
        }catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
            emit(ResultState.Error(errorResponse.message))
        }catch (e: Exception){
            emit(ResultState.Error(e.message ?: "Error"))
        }
    }

    companion object {
        @Volatile
        private var instance: StroyRepository? = null

        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference
        ): StroyRepository =
            instance ?: synchronized(this) {
                instance ?: StroyRepository(apiService, userPreference)
            }.also { instance = it }
    }
}