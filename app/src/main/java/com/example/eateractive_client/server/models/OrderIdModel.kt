package com.example.eateractive_client.server.models

import com.google.gson.annotations.SerializedName

data class OrderIdModel(
    @SerializedName("order_id")
    val orderId: Int
)