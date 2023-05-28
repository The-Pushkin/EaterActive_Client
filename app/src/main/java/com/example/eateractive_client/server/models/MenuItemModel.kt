package com.example.eateractive_client.server.models

import com.google.gson.annotations.SerializedName

data class MenuItemModel(
    @SerializedName("item_id")
    val id: Int,
    val name: String,
    val price: Double
)