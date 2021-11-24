package com.example.warehouse.database.entity

import androidx.room.Embedded
import androidx.room.Relation

class UserWithOrderEntity(
    @Embedded
    val userEntity: UserEntity,

    @Relation(
        parentColumn = "userId",
        entityColumn = "userOwnerId"
    )
    val orderEntity: OrderEntity?
)