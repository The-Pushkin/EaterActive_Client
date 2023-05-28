package com.example.eateractive_client.cart

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.eateractive_client.cart.dao.CartDao
import com.example.eateractive_client.cart.entity.MenuItemEntity

@Database(
    entities = [
        MenuItemEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class CartDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
}

fun cartDatabase(context: Context) = Room.databaseBuilder(
    context,
    CartDatabase::class.java, "cart_database"
).build()