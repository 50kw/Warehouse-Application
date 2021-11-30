package com.example.warehouse.ui.items

import com.example.warehouse.database.WarehouseDatabase
import com.example.warehouse.database.entity.ItemEntity
import com.example.warehouse.database.entity.OrderItemCrossRef
import kotlinx.coroutines.flow.Flow

class ItemsRepository(
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
}