package com.example.warehouse.database.dao

import androidx.room.*
import com.example.warehouse.database.entity.*
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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderItemCrossRef(crossRef: OrderItemCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(itemEntityList: List<ItemEntity>)

    @Transaction
    suspend fun insertOrderWithItemEntities(orderEntity: OrderEntity, itemEntityList: List<ItemEntity>) {

        insertItem(itemEntityList)
    }


    @Delete
    suspend fun deleteOrder(orderEntity: OrderEntity)

    @Update
    suspend fun updateOrder(orderEntity: OrderEntity)
}