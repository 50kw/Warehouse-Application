package com.example.warehouse.database.dao

import androidx.room.*
import com.example.warehouse.database.entity.OrderEntity
import com.example.warehouse.database.entity.OrderWithItemEntities
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderEntityDao {

    @Query("SELECT * FROM order_entity")
    fun getAllOrders(): Flow<List<OrderEntity>>

    @Transaction
    @Query("SELECT * FROM order_entity")
    fun getAllOrderWithItemEntities(): Flow<List<OrderWithItemEntities>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(orderEntity: OrderEntity)

    @Delete
    suspend fun deleteOrder(orderEntity: OrderEntity)

    @Update
    suspend fun updateOrder(orderEntity: OrderEntity)
}