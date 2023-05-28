package com.example.eateractive_client.server.models

import com.google.gson.annotations.SerializedName

data class RestaurantModel(
    @SerializedName("restaurant_id")
    val id: Int,
    @SerializedName("restaurant_name")
    val name: String
)