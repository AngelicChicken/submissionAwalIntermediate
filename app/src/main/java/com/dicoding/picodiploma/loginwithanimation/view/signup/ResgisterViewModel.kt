package com.dicoding.picodiploma.loginwithanimation.view.signup

import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.loginwithanimation.data.api.response.RegisterResponse
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository


class ResgisterViewModel (private val repository: UserRepository) : ViewModel() {
    suspend fun register(name: String, email: String, password: String): RegisterResponse {
        return repository.register(name,email,password)
    }
}