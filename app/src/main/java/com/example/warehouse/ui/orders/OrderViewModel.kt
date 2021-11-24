package com.example.warehouse.ui.orders

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warehouse.SharedPrefUtil
import com.example.warehouse.arch.Event
import com.example.warehouse.arch.WarehouseRepository
import com.example.warehouse.database.WarehouseDatabase
import com.example.warehouse.database.entity.ItemEntity
import com.example.warehouse.database.entity.OrderEntity
import com.example.warehouse.database.entity.OrderWithItemEntities
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class OrderViewModel : ViewModel() {

    private lateinit var repository: OrderRepository

    private val storedUserId = SharedPrefUtil.getCurrentUserId()

    val orderWithItemEntitiesLiveData = MutableLiveData<List<OrderWithItemEntities>>()

    val orderTransactionCompleteLiveData = MutableLiveData<Event<Boolean>>()

    val userId: MutableLiveData<String> by lazy {
        MutableLiveData<String>(storedUserId)
    }


    fun init (warehouseDatabase: WarehouseDatabase) {
        repository = OrderRepository(warehouseDatabase)


        viewModelScope.launch {
            repository.getAllOrderWithItemEntities().collect { orders ->
                orderWithItemEntitiesLiveData.postValue(orders)
            }
        }
    }

    /*fun userLogin(loginId: String, password: String) {
        userWithOrderEntityLiveData.value?.forEach {
            if (it.userEntity.userLoginId == loginId && it.userEntity.userPassword == password) {
                _loginViewStateLiveData.postValue(it.userEntity.userId)
                return
            }
        }
        _loginViewStateLiveData.postValue("none")
    }*/

    fun insertOrder(orderEntity: OrderEntity) {
        viewModelScope.launch {
            repository.insertOrder(orderEntity)

            orderTransactionCompleteLiveData.postValue(Event(true))
        }
    }

    fun deleteOrder(orderEntity: OrderEntity) {
        viewModelScope.launch {
            repository.deleteOrder(orderEntity)
        }
    }

    fun updateOrder(orderEntity: OrderEntity) {
        viewModelScope.launch {
            repository.updateOrder(orderEntity)

            orderTransactionCompleteLiveData.postValue(Event(true))
        }
    }
}

