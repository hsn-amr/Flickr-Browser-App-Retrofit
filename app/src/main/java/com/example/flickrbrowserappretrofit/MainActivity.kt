package com.example.flickrbrowserappretrofit

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    companion object{
        var tag = ""
        val API_KEY = "0c944ebbc472f758230f3711aea24676"
        var amount = "100"
    }
    private lateinit var adapter: RVPhoto
    private lateinit var rvMain: RecyclerView

    private lateinit var tagInput: EditText
    private lateinit var searchButton: Button
    private lateinit var amountInput: EditText

    private var photosList = ArrayList<Photos.Photo.Item>()

    private lateinit var cm: ConnectivityManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvMain = findViewById(R.id.rvMain)
        adapter = RVPhoto(photosList,this)
        rvMain.adapter = adapter
        rvMain.layoutManager = GridLayoutManager(this, 2)

        tagInput = findViewById(R.id.etTagInput)
        searchButton = findViewById(R.id.btnSearch)
        amountInput = findViewById(R.id.etAmount)

        searchButton.setOnClickListener {
            if(tagInput.text.isNotEmpty()){
                tag = tagInput.text.toString()
                if(amountInput.text.isNotEmpty()) {
                    amount = (amountInput.text.toString().toInt()+1).toString()
                    amountInput.text.clear()
                }
                requestApi()
                tagInput.text.clear()
            }else{
                Toast.makeText(this, "Please, Enter a tag to search", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun requestApi(){
        cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        if(activeNetwork?.isConnectedOrConnecting == true){
            val apiInterface = ApiClient.getClient()?.create(ApiInterface::class.java)
            val call: Call<Photos> = apiInterface!!.getPhotos(API_KEY, amount, tag)

            call?.enqueue(object : Callback<Photos>{
                override fun onResponse(call: Call<Photos>, response: Response<Photos>) {
                    if(photosList.isNotEmpty()) photosList.clear()

                    var photos = response.body()
                    for(i in photos?.photos?.photo!!){
                        photosList.add(i)
                    }
                    rvMain.adapter!!.notifyDataSetChanged()
                    rvMain.smoothScrollToPosition(0)
                }

                override fun onFailure(call: Call<Photos>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Something went wrong", Toast.LENGTH_LONG).show()
                }

            })
        }else{
            Toast.makeText(this, "Please Connect To Internet First", Toast.LENGTH_LONG).show()
        }
    }
}