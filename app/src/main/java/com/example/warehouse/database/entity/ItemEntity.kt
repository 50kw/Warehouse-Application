package com.example.warehouse.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_entity")
data class ItemEntity(
    @PrimaryKey val itemId: String = "",
    val itemName: String = "",
    val itemCount: Int = 0,
    val itemPlace: String = ""
)