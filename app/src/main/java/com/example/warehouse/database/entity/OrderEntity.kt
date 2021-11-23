package com.example.warehouse.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order_entity")
data class OrderEntity(
    @PrimaryKey val orderId: String = "",
    val orderName: String = "",
    val orderDescription: String = "",
    val userOwnerId: String = ""
)