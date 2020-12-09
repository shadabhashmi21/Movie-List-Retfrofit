package com.e.retrofit

import com.e.models.ApiModel
import retrofit2.Call
import retrofit2.http.GET

interface APIService {
    @GET("/3/movie/top_rated?page=1&api_key=7a21e3b33f92fe9700af60427bb7ca24")
    fun getMovieDetails(): Call<ApiModel>
}