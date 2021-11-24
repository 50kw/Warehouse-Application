package com.example.warehouse.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warehouse.SharedPrefUtil
import com.example.warehouse.arch.Event
import com.example.warehouse.database.WarehouseDatabase
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private lateinit var repository: LoginRepository

    private val storedUserId = SharedPrefUtil.getCurrentUserId()

    val orderTransactionCompleteLiveData = MutableLiveData<Event<Boolean>>()

    val userId = MutableLiveData<String>()


    fun init (warehouseDatabase: WarehouseDatabase) {
        repository = LoginRepository(warehouseDatabase)

    }

    fun getUserId(loginId: String, password: String) {
        viewModelScope.launch {
            userId.postValue(repository.getUserId(loginId, password))
        }
    }
}