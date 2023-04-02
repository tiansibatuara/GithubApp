package com.example.githubapp

import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("search/users")
    fun getUser(@Query("q") id: String): Call<UserResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<UserDetailResponse>


}