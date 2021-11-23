package com.example.warehouse.database.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class OrderWithItemEntities(
    @Embedded
    val orderEntity: OrderEntity,

    @Relation(
        parentColumn = "orderId",
        entityColumn = "itemId",
        associateBy = Junction(OrderItemCrossRef::class)
    )
    val ItemEntities: List<ItemEntity>
)