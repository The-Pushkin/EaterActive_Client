package com.example.eateractive_client.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eateractive_client.cart.entity.MenuItemEntity
import kotlinx.coroutines.launch

class CartViewModel(private val database: CartDatabase) : ViewModel() {
    private val _menuItems: MutableLiveData<List<MenuItemEntity>> = MutableLiveData(emptyList())
    val menuItems: LiveData<List<MenuItemEntity>> = _menuItems

    init {
        viewModelScope.launch {
            database.cartDao().getAll().collect {
                _menuItems.value = it
            }
        }
    }

    fun addToCart(menuItemEntity: MenuItemEntity) = viewModelScope.launch {
        database.cartDao().insert(menuItemEntity)
    }

    fun removeFromCart(menuItemEntity: MenuItemEntity) = viewModelScope.launch {
        database.cartDao().delete(menuItemEntity)
    }

    fun emptyCart() = viewModelScope.launch {
        database.cartDao().deleteAll()
    }
}
