package com.example.warehouse.database.entity

import androidx.room.Entity

@Entity(primaryKeys = ["orderId", "itemId"], tableName = "order_item_entities")
data class OrderItemCrossRef(
    val orderId: String,
    val itemId: String
)