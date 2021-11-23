package com.example.warehouse.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.warehouse.database.dao.ItemEntityDao
import com.example.warehouse.database.dao.OrderEntityDao
import com.example.warehouse.database.dao.UserEntityDao
import com.example.warehouse.database.entity.ItemEntity
import com.example.warehouse.database.entity.OrderEntity
import com.example.warehouse.database.entity.OrderItemCrossRef
import com.example.warehouse.database.entity.UserEntity

@Database(entities = [ItemEntity::class, OrderEntity::class, UserEntity::class, OrderItemCrossRef::class], version = 1)
abstract class WarehouseDatabase : RoomDatabase() {

    companion object {
        private var warehouseDatabase: WarehouseDatabase? = null

        fun getDatabase(context: Context): WarehouseDatabase {
            if (warehouseDatabase != null) {
                return warehouseDatabase!!
            }

            warehouseDatabase = Room
                .databaseBuilder(
                    context.applicationContext,
                    WarehouseDatabase::class.java,
                    "warehouse-database"
                ).build()
            return warehouseDatabase!!
        }
    }

    abstract fun itemEntityDao(): ItemEntityDao
    abstract fun orderEntityDao(): OrderEntityDao
    abstract fun userEntityDao(): UserEntityDao
}