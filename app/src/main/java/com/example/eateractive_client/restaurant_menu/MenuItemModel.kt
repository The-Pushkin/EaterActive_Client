package com.example.eateractive_client.restaurant_menu

sealed interface MenuItemModel {
    data class MenuItem(
        val name: String,
        val price: Double
    ) : MenuItemModel

    object Divider : MenuItemModel
}