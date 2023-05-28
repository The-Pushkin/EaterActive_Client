package com.example.eateractive_client.server.models

import com.google.gson.annotations.SerializedName

data class OrderListModel(
    val content: String,
    @SerializedName("restaurant_id")
    val restaurantId: Int,
    @SerializedName("delivery_address")
    val deliveryAddress: String
)