package com.example.githubapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private val _selectedUser = MutableLiveData<UserDetailResponse>()
    val selectedUser: LiveData<UserDetailResponse> = _selectedUser

    private val _listFollowers = MutableLiveData<List<FollowersResponseItem>>()
    val listFollowers: LiveData<List<FollowersResponseItem>> = _listFollowers

    private val _listFollowing = MutableLiveData<List<FollowingResponseItem>>()
    val listFollowing: LiveData<List<FollowingResponseItem>> = _listFollowing


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "DetailViewModel"
    }

    fun getDetailUser(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService()
            .getDetailUser(query)

        client.enqueue(object : Callback<UserDetailResponse>{
            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                _isLoading.value = false
                val responseBody = response.body()
                if (response.isSuccessful) {
                    _selectedUser.value = responseBody
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

    fun getFollowing(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService()
            .getFollowing(query)

        client.enqueue(object : Callback<List<FollowingResponseItem>>{
            override fun onResponse(
                call: Call<List<FollowingResponseItem>>,
                response: Response<List<FollowingResponseItem>>
            ) {
                _isLoading.value = false
                val responseBody = response.body()
                if (response.isSuccessful) {
                    _listFollowing.value = responseBody
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowingResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}", )
            }

        })
    }

    fun getFollowers(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService()
            .getFollowers(query)

        client.enqueue(object : Callback<List<FollowersResponseItem>>{
            override fun onResponse(
                call: Call<List<FollowersResponseItem>>,
                response: Response<List<FollowersResponseItem>>
            ) {
                _isLoading.value = false
                val responseBody = response.body()
                if (response.isSuccessful) {
                    _listFollowers.value = responseBody
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowersResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}", )
            }

        })
    }
}