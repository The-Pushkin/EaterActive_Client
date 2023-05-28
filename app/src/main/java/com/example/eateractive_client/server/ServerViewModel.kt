package com.example.eateractive_client.server

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServerViewModel {
    private const val baseUrl = "http://vlogoeats.onrender.com"

    fun getInstance(): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}