package com.example.warehouse.database.dao

import androidx.room.*
import com.example.warehouse.database.entity.OrderEntity
import com.example.warehouse.database.entity.UserEntity
import com.example.warehouse.database.entity.UserWithOrderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserEntityDao {

    @Query("SELECT * FROM user_entity")
    fun getAllUsers(): Flow<List<UserEntity>>

    @Transaction
    @Query("SELECT * FROM user_entity")
    fun getAllUserWithOrderEntity(): Flow<List<UserWithOrderEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userEntity: UserEntity)

    @Delete
    suspend fun deleteUser(userEntity: UserEntity)

    @Update
    suspend fun updateUser(userEntity: UserEntity)

    @Query("SELECT userId FROM user_entity WHERE userLoginId = :loginId AND userPassword = :password")
    suspend fun getUserId(loginId: String, password: String): String?
}