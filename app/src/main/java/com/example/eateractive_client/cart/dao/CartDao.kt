package com.example.eateractive_client.cart.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.eateractive_client.cart.entity.MenuItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT * FROM cart_table")
    fun getAll(): Flow<List<MenuItemEntity>>

    @Insert
    suspend fun insert(menuItemEntity: MenuItemEntity)

    @Delete
    suspend fun delete(menuItemEntity: MenuItemEntity)

    @Query("DELETE FROM cart_table")
    suspend fun deleteAll()
}