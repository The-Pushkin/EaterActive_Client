package com.example.eateractive_client.restaurant_menu

sealed interface MenuItemModel {
    class MenuItem(
        name: String,
        price: Float
    ) : MenuItemModel

    object Divider : MenuItemModel
}