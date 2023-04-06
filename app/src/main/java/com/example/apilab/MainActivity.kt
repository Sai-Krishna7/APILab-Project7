package com.example.apilab

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    var petImageURL = ""
    var animeTitle = ""
    var animeScore = ""
    var randomTopAnime = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getDogImageURL()

        var button = findViewById<Button>(R.id.petButton)
        var imageView = findViewById<ImageView>(R.id.petImage)
        var randInteger = Random.nextInt(2)

        getNextImage(button, imageView, randInteger)
    }

//    private fun getDogImageURL()
//    {
//        val client = AsyncHttpClient()
//
//        client["https://dog.ceo/api/breeds/image/random/20", object : JsonHttpResponseHandler() {
//            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
//                Log.d("Dog", "response successful$json")
//                petImageURL = json.jsonObject.getString("message")
//                Log.d("petImageURL", "pet image URL set")
//            }
//
//            override fun onFailure(
//                statusCode: Int,
//                headers: Headers?,
//                errorResponse: String,
//                throwable: Throwable?
//            ) {
//                Log.d("Dog Error", errorResponse)
//            }
//        }]
//    }

    private fun getDogImageURL()
    {
        val client = AsyncHttpClient()
//        val randomTopAnime = Random.nextInt(25)


        client["https://api.jikan.moe/v4/top/anime", object : JsonHttpResponseHandler() {
            @SuppressLint("SetTextI18n")
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                Log.d("Dog", "response successful$json")
                if (randomTopAnime > 24)
                {
                    randomTopAnime = 0
                }
                petImageURL = json.jsonObject.getJSONArray("data")
                    .getJSONObject(randomTopAnime)
                    .getJSONObject("images")
                    .getJSONObject("jpg")
                    .getString("image_url")
                animeTitle = json.jsonObject.getJSONArray("data")
                    .getJSONObject(randomTopAnime)
                    .getString("title")
                animeScore = json.jsonObject.getJSONArray("data")
                    .getJSONObject(randomTopAnime)
                    .getString("score")
                Log.d("petImageURL", "pet image URL set")
                randomTopAnime++
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



//    private fun getCatImageURL() {
//        val client = AsyncHttpClient()
//
//        client["https://api.aniapi.com/v1/images/random", object : JsonHttpResponseHandler() {
//            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
//                var resultsJSON = json.jsonArray.getJSONObject(0)
//                petImageURL = resultsJSON.getString("url")
//            }
//
//            override fun onFailure(
//                statusCode: Int,
//                headers: Headers?,
//                errorResponse: String,
//                throwable: Throwable?
//            ) {
//                Log.d("Dog Error", errorResponse)
//            }
//        }]
//    }

    @SuppressLint("SetTextI18n")
    private fun getNextImage(button: Button, imageView: ImageView, randInteger: Int) {

        button.setOnClickListener {
            getDogImageURL()
            Glide.with(this)
                . load(petImageURL)
                .fitCenter()
                .into(imageView)
            val animeTitleTextField = findViewById<TextView>(R.id.AnimeTitle)
            animeTitleTextField.setText("Anime Title: " + animeTitle)
            val animeScoreTextField = findViewById<TextView>(R.id.AnimeScore)
            if (animeScore != "null")
            {
                animeScoreTextField.setText("Anime Score: " + animeScore)
            }

            else
            {
                animeScoreTextField.setText("Anime Score: " + 0)
            }

        }
    }
}