package com.example.warehouse.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_entity")
data class UserEntity(
    @PrimaryKey val userId: String = "",
    val userLoginId: String = "",
    val userPassword: String = "",
    val userFullName: String = "",
    val UserPosition: String = "",
    val currentOrderId: String? = null
)