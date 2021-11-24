package com.example.warehouse.ui.orders

import com.example.warehouse.database.WarehouseDatabase
import com.example.warehouse.database.entity.OrderEntity
import com.example.warehouse.database.entity.OrderWithItemEntities
import kotlinx.coroutines.flow.Flow

class OrderRepository(
    private val warehouseDatabase: WarehouseDatabase
) {

    suspend fun insertOrder(orderEntity: OrderEntity) {
        warehouseDatabase.orderEntityDao().insertOrder(orderEntity)
    }

    suspend fun deleteOrder(orderEntity: OrderEntity) {
        warehouseDatabase.orderEntityDao().deleteOrder(orderEntity)
    }

    suspend fun updateOrder(orderEntity: OrderEntity) {
        warehouseDatabase.orderEntityDao().updateOrder(orderEntity)
    }

    fun getAllOrders(): Flow<List<OrderEntity>> {
        return warehouseDatabase.orderEntityDao().getAllOrders()
    }

    fun getAllOrderWithItemEntities(): Flow<List<OrderWithItemEntities>> {
        return warehouseDatabase.orderEntityDao().getAllOrderWithItemEntities()
    }
}