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


    /*suspend fun insertOrder(orderEntity: OrderEntity) {
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
    }*/



    suspend fun insertUser(userEntity: UserEntity) {
        warehouseDatabase.userEntityDao().insertUser(userEntity)
    }

    suspend fun deleteUser(userEntity: UserEntity) {
        warehouseDatabase.userEntityDao().deleteUser(userEntity)
    }

    suspend fun updateUser(userEntity: UserEntity) {
        warehouseDatabase.userEntityDao().updateUser(userEntity)
    }

    fun getAllUsers(): Flow<List<UserEntity>> {
        return warehouseDatabase.userEntityDao().getAllUsers()
    }

    fun getAllUserWithOrderEntity(): Flow<List<UserWithOrderEntity>> {
        return warehouseDatabase.userEntityDao().getAllUserWithOrderEntity()
    }

    suspend fun getUserId(userId: String, password: String): String? {
        return warehouseDatabase.userEntityDao().getUserId(userId, password)
    }
}