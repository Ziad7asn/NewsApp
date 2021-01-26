package com.example.newsapp.api


import com.example.newsapp.api.model.NewsResponse
import com.example.newsapp.api.model.SourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface WebServices {
    @GET("sources")
    fun getSources(
        @Query("apiKey")key:String,@Query("language")lang:String
    ):Call<SourcesResponse>

    @GET("everything")
    fun getNews(
                @Query("apiKey")key:String,@Query("sources")sources:String,
                @Query("language")lang:String):Call<NewsResponse>
}