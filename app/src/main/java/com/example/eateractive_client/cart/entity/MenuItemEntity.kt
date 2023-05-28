package com.example.eateractive_client.cart.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_table")
data class MenuItemEntity(
    @PrimaryKey(autoGenerate = true) var uid: Int = 0,
    @ColumnInfo(name = "serverId") val serverId: Int?,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "price") val price: Double?,
)