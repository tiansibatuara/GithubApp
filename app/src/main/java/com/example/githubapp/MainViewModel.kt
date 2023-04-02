package com.example.githubapp

import android.util.Log
import androidx.lifecycle.*
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _listUser = MutableLiveData<List<User>>()
    val listUser: LiveData<List<User>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "MainViewModel"
    }

    init{
        findUser("tiansibatuara")
    }

     fun findUser(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(query)

        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUser.value = response.body()?.items
                    Log.d(TAG, "${response.body()}")
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}
