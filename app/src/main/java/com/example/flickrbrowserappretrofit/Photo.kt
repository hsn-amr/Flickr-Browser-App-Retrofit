package com.example.flickrbrowserappretrofit

import com.google.gson.annotations.SerializedName

class Photos {

    @SerializedName("photos")
    var photos: Photo? = null

    class Photo {

        @SerializedName("photo")
        var photo: ArrayList<Item>? = null

        class Item {

            @SerializedName("id")
            var id: String? = null

            @SerializedName("secret")
            var secret: String? = null

            @SerializedName("server")
            var server: String? = null

            @SerializedName("title")
            var title: String? = null

            fun url(size: String = ""): String {
                var url = if (size.isNotEmpty()) {
                    "https://live.staticflickr.com/$server/${id}_${secret}_$size.jpg"
                } else {
                    "https://live.staticflickr.com/$server/${id}_$secret.jpg"
                }
                return url
            }
        }
    }
}