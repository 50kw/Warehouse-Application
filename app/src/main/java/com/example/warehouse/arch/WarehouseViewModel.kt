package com.example.warehouse.arch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warehouse.database.WarehouseDatabase
import com.example.warehouse.database.entity.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class WarehouseViewModel: ViewModel() {
    private lateinit var repository: WarehouseRepository

    val itemEntitiesLiveData = MutableLiveData<List<ItemEntity>>()
    val userWithOrderEntityLiveData = MutableLiveData<List<UserWithOrderEntity>>()
    val orderWithItemEntitiesLiveData = MutableLiveData<List<OrderWithItemEntities>>()

    val transactionCompleteLiveData = MutableLiveData<Event<Boolean>>()

    fun init (warehouseDatabase: WarehouseDatabase) {
        repository = WarehouseRepository(warehouseDatabase)

        viewModelScope.launch {
            repository.getAllItems().collect { items ->
                itemEntitiesLiveData.postValue(items)
            }
        }

        viewModelScope.launch {
            repository.getAllUserWithOrderEntity().collect { users ->
                userWithOrderEntityLiveData.postValue(users)
            }
        }

        /*viewModelScope.launch {
            repository.getAllOrderWithItemEntities().collect { orders ->
                orderWithItemEntitiesLiveData.postValue(orders)
            }
        }*/
    }

    private val _loginViewStateLiveData = MutableLiveData<String>()
    val loginViewStateLiveData: LiveData<String>
        get() = _loginViewStateLiveData

/*    data class LoginViewState(
        private val userId: String = "",
        private val loginId: String,
        private val password: String
    ) {
        fun getUserID
    }*/

    fun userLogin(loginId: String, password: String) {
        userWithOrderEntityLiveData.value?.forEach {
            if (it.userEntity.userLoginId == loginId && it.userEntity.userPassword == password) {
                _loginViewStateLiveData.postValue(it.userEntity.userId)
                return
            }
        }
        _loginViewStateLiveData.postValue("none")
    }

    fun insertItem(itemEntity: ItemEntity) {
        viewModelScope.launch {
            repository.insertItem(itemEntity)

            transactionCompleteLiveData.postValue(Event(true))
        }
    }

    fun deleteItem(itemEntity: ItemEntity) {
        viewModelScope.launch {
            repository.deleteItem(itemEntity)
        }
    }

    fun updateItem(itemEntity: ItemEntity) {
        viewModelScope.launch {
            repository.updateItem(itemEntity)

            transactionCompleteLiveData.postValue(Event(true))
        }
    }

    /*fun insertOrder(orderEntity: OrderEntity) {
        viewModelScope.launch {
            repository.insertOrder(orderEntity)

            transactionCompleteLiveData.postValue(Event(true))
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

            transactionCompleteLiveData.postValue(Event(true))
        }
    }*/

    fun insertUser(userEntity: UserEntity) {
        viewModelScope.launch {
            repository.insertUser(userEntity)

            transactionCompleteLiveData.postValue(Event(true))
        }
    }

    fun deleteUser(userEntity: UserEntity) {
        viewModelScope.launch {
            repository.deleteUser(userEntity)
        }
    }

    fun updateUser(userEntity: UserEntity) {
        viewModelScope.launch {
            repository.updateUser(userEntity)

            transactionCompleteLiveData.postValue(Event(true))
        }
    }
}