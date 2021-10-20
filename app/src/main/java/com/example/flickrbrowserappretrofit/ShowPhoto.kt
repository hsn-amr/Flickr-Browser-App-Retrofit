package com.example.flickrbrowserappretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide

class ShowPhoto : AppCompatActivity() {

    private lateinit var mainImageView: ImageView
    var urlOfImage = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_photo)

        val extra = intent.extras
        if(extra != null){
            urlOfImage = extra.getString("url").toString()
        }
        mainImageView = findViewById(R.id.ivMain)

        Glide.with(this)
            .load(urlOfImage)
            .into(mainImageView)
    }
}