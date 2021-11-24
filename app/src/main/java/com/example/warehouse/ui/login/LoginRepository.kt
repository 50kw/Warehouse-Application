package com.example.warehouse.ui.login

import com.example.warehouse.database.WarehouseDatabase

class LoginRepository(
    private val warehouseDatabase: WarehouseDatabase
) {

    suspend fun getUserId(userId: String, password: String): String? {
        return warehouseDatabase.userEntityDao().getUserId(userId, password)?: "wrong"
    }
}