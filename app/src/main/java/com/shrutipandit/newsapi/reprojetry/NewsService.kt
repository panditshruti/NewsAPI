package com.shrutipandit.newsapi.reprojetry

import com.shrutipandit.newsapi.db.NewsArticle

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("top-headlines")
    fun getTopHeadlines(
        @Query("country") country: String = "in",
        @Query("apiKey") apiKey: String
    ): retrofit2.Call<NewsResponse>
}

data class NewsResponse(val articles: List<NewsArticle>)