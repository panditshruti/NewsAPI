package com.shrutipandit.newsapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.shrutipandit.newsapi.adapter.NewsAdapter
import com.shrutipandit.newsapi.databinding.ActivityMainBinding
import com.shrutipandit.newsapi.db.NewsArticle
import com.shrutipandit.newsapi.reprojetry.NewsResponse
import com.shrutipandit.newsapi.reprojetry.NewsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var bindig: ActivityMainBinding
    private val apiKey = "618bf4b07ce140e0bc288c033fb1332e"
    private lateinit var newsService: NewsService
    private lateinit var newItemArrayList: List<NewsArticle>
    private lateinit var newsAdapter: NewsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindig = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindig.root)


        newItemArrayList = listOf()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        newsService = retrofit.create(NewsService::class.java)


        val call = newsService.getTopHeadlines(apiKey = apiKey)
        call.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@MainActivity, "load successfully", Toast.LENGTH_SHORT).show()
                    newItemArrayList = (response.body()?.articles ?: emptyList())
                    Toast.makeText(this@MainActivity, newItemArrayList[1].description, Toast.LENGTH_SHORT).show()
                    newsAdapter.updateData(newItemArrayList)
                    // Process and display newsArticles in your UI
                } else {
                    // Handle unsuccessful response
                    Toast.makeText(this@MainActivity, "Unable to load", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Failed To load", Toast.LENGTH_SHORT).show()
            }
        })



        newsAdapter = NewsAdapter(newItemArrayList)
        bindig.recyclerView.layoutManager = LinearLayoutManager(this)
        bindig.recyclerView.adapter = newsAdapter


    }

}

