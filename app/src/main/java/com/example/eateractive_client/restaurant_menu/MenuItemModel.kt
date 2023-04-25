package com.example.eateractive_client.restaurant_menu

import com.example.eateractive_client.cart.entity.MenuItemEntity

sealed interface MenuItemModel {
    data class MenuItem(
        val menuItemEntity: MenuItemEntity
    ) : MenuItemModel {
        companion object {
            operator fun invoke(name: String, price: Double): MenuItem =
                MenuItem(MenuItemEntity(0, name, price))
        }
    }

    object Divider : MenuItemModel
}