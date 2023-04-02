package com.example.githubapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Query

class DetailViewModel : ViewModel() {

    private val _selectedUser = MutableLiveData<UserDetailResponse>()
    val selectedUser: LiveData<UserDetailResponse> = _selectedUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "DetailViewModel"
    }

    fun getDetailUser(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(query)

        client.enqueue(object : Callback<UserDetailResponse>{
            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                _isLoading.value = false
                val responseBody = response.body()
                if (response.isSuccessful) {
                    _selectedUser.value = responseBody
                    Log.d(TAG, "onResponse: ${responseBody}")
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}", )
            }

        })
    }
}