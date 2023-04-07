package com.example.apilab

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var animeList: MutableList<String>
    private lateinit var animeTitleList: MutableList<String>
    private lateinit var animeScoreList: MutableList<String>
    private lateinit var rvAnime: RecyclerView
    private lateinit var rvAnime2: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvAnime = findViewById(R.id.anime_list)
        rvAnime2 = findViewById(R.id.anime_list)
        animeList = mutableListOf()
        animeTitleList = mutableListOf()
        animeScoreList = mutableListOf()

        getDogImageURL()
    }

    private fun getDogImageURL()
    {
        val client = AsyncHttpClient()


        client["https://api.jikan.moe/v4/top/anime", object : JsonHttpResponseHandler() {
            @SuppressLint("SetTextI18n")
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                Log.d("Dog", "response successful$json")
                val animeImageArray = json.jsonObject.getJSONArray("data")
                for (i in 0 until animeImageArray.length()) {
                    //animeList.add(animeImageArray.getString(i))
                    val topAnime = animeImageArray.getJSONObject(i)
                    val images = topAnime.getJSONObject("images")
                    val jpg = images.getJSONObject("jpg")
                    val imageURL = jpg.getString("image_url")
                    animeList.add(imageURL)
                    val animeTitle = topAnime.getString("title")
                    animeTitleList.add(animeTitle)
                    val animeScore = topAnime.getString("score")
                    animeScoreList.add(animeScore)

                }

                val adapter = AnimeAdapter(animeList, animeTitleList, animeScoreList)
                rvAnime.adapter = adapter
                rvAnime.layoutManager = LinearLayoutManager(this@MainActivity)
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Dog Error", errorResponse)
            }
        }]
    }
}