package com.example.eateractive_client.restaurant_list

sealed interface RestaurantModel {
    data class Restaurant(
        val id: Int,
        val name: String
    ) : RestaurantModel

    object Divider : RestaurantModel
}