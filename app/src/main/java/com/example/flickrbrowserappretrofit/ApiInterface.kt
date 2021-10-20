package com.example.flickrbrowserappretrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    //https://stackoverflow.com/questions/35964147/illegalargumentexception-in-retrofit-must-not-have-replace-block
    @GET("/services/rest/?method=flickr.photos.search&page=1&format=json&nojsoncallback=1")
    fun getPhotos(@Query("api_key") API_KEY:String, @Query("per_page") amount:String, @Query("tags") tag:String): Call<Photos>
}