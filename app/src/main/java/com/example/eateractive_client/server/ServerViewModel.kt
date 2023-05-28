package com.example.eateractive_client.server

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServerViewModel {
    private const val baseUrl = "http://x72bh6y9v-1zq4d3fpl.hopto.org:5000"

    fun getInstance(): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}