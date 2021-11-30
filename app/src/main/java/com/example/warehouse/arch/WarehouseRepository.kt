package com.example.warehouse.arch

import com.example.warehouse.database.WarehouseDatabase
import com.example.warehouse.database.entity.*
import kotlinx.coroutines.flow.Flow

class WarehouseRepository(
    private val warehouseDatabase: WarehouseDatabase
    ) {

    suspend fun insertItem(itemEntity: ItemEntity) {
        warehouseDatabase.itemEntityDao().insertItem(itemEntity)
    }

    suspend fun deleteItem(itemEntity: ItemEntity) {
        warehouseDatabase.itemEntityDao().deleteItem(itemEntity)
    }

    suspend fun updateItem(itemEntity: ItemEntity) {
        warehouseDatabase.itemEntityDao().updateItem(itemEntity)
    }

    fun getAllItems(): Flow<List<ItemEntity>> {
        return warehouseDatabase.itemEntityDao().getAllItems()
    }


    suspend fun insertOrder(orderEntity: OrderEntity) {
        warehouseDatabase.orderEntityDao().insertOrder(orderEntity)
    }

    suspend fun deleteOrder(orderEntity: OrderEntity) {
        warehouseDatabase.orderEntityDao().deleteOrder(orderEntity)
    }

    suspend fun updateOrder(orderEntity: OrderEntity) {
        warehouseDatabase.orderEntityDao().updateOrder(orderEntity)
    }

    suspend fun insertOrderItem(itemEntityList: List<ItemEntity>) {
        warehouseDatabase.orderEntityDao().insertItem(itemEntityList)
    }

    fun getAllOrderWithItemEntities(): Flow<List<OrderWithItemEntities>> {
        return warehouseDatabase.orderEntityDao().getAllOrderWithItemEntities()
    }


    suspend fun insertUser(userEntity: UserEntity) {
        warehouseDatabase.userEntityDao().insertUser(userEntity)
    }

    suspend fun deleteUser(userEntity: UserEntity) {
        warehouseDatabase.userEntityDao().deleteUser(userEntity)
    }

    suspend fun updateUser(userEntity: UserEntity) {
        warehouseDatabase.userEntityDao().updateUser(userEntity)
    }

    fun getAllUserWithOrderEntity(): Flow<List<UserWithOrderEntity>> {
        return warehouseDatabase.userEntityDao().getAllUserWithOrderEntity()
    }

}