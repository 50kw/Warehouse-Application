package com.example.warehouse.ui.users

import com.example.warehouse.database.WarehouseDatabase
import com.example.warehouse.database.entity.ItemEntity
import com.example.warehouse.database.entity.UserEntity
import com.example.warehouse.database.entity.UserWithOrderEntity
import kotlinx.coroutines.flow.Flow

class UsersRepository(
    private val warehouseDatabase: WarehouseDatabase
) {

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

}