package com.example.warehouse.ui.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warehouse.SharedPrefUtil
import com.example.warehouse.arch.Event
import com.example.warehouse.database.WarehouseDatabase
import com.example.warehouse.database.entity.ItemEntity
import com.example.warehouse.database.entity.OrderEntity
import com.example.warehouse.database.entity.OrderWithItemEntities
import com.example.warehouse.database.entity.UserEntity
import com.example.warehouse.ui.users.UsersRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class OrderViewModel : ViewModel() {

    private lateinit var orderRepository: OrderRepository

    private val storedUserId = SharedPrefUtil.getCurrentUserId()

    val orderWithItemEntitiesLiveData = MutableLiveData<List<OrderWithItemEntities>>()

    val orderTransactionCompleteLiveData = MutableLiveData<Event<Boolean>>()

    val orderEntityEditIdLiveData = MutableLiveData<String>()

    val userId: MutableLiveData<String> by lazy {
        MutableLiveData<String>(storedUserId)
    }

    fun init (warehouseDatabase: WarehouseDatabase) {
        orderRepository = OrderRepository(warehouseDatabase)

        viewModelScope.launch {
            orderRepository.getAllOrderWithItemEntities().collect { orders ->
                orderWithItemEntitiesLiveData.postValue(orders)

                updateOrdersViewState(orders)
            }
        }
    }

    fun refreshItems() {
        viewModelScope.launch {
            orderRepository.getAllOrderWithItemEntities().collect { orders ->
                orderWithItemEntitiesLiveData.postValue(orders)

                updateOrdersViewState(orders)
            }
        }
    }

    fun findItemEntity(orderId : String) : OrderWithItemEntities {
        return orderWithItemEntitiesLiveData.value?.find { orderWithItemEntities ->
            orderWithItemEntities.orderEntity.orderId == orderId
        }?: OrderWithItemEntities(OrderEntity(), emptyList())
    }

    fun findOrderWithItemEntities(orderId: String) : OrderWithItemEntities {
       _ordersViewStateLiveData.value?.dataList?.forEach {
           val orderWithItemEntities = it.data as OrderWithItemEntities
           if (orderWithItemEntities.orderEntity.orderId == orderId) {
               return orderWithItemEntities
           }
       }
        return OrderWithItemEntities(OrderEntity(), emptyList())
    }

    private val _ordersViewStateLiveData = MutableLiveData<OrdersViewState>()
    val ordersViewStateLiveData: LiveData<OrdersViewState>
        get() = _ordersViewStateLiveData

    data class OrdersViewState(
        val dataList: List<DataItem<*>> = emptyList(),
        val responsibleUser: UserEntity = UserEntity(),
        val isLoading: Boolean = false
    ) {
        data class DataItem<T> (
            val data: T
        )
    }

    private fun updateOrdersViewState(orders: List<OrderWithItemEntities>) {
        val dataList = ArrayList<OrdersViewState.DataItem<*>>()

        orders.forEach { item ->
            dataList.add(OrdersViewState.DataItem(item))
        }

        _ordersViewStateLiveData.postValue(OrdersViewState(dataList = dataList, isLoading = false))
    }

    fun insertOrder(orderEntity: OrderEntity) {
        viewModelScope.launch {
            orderRepository.insertOrder(orderEntity)

            orderTransactionCompleteLiveData.postValue(Event(true))
        }
    }

    fun insertItem(itemEntityList: List<ItemEntity>) {
        viewModelScope.launch {
            orderRepository.insertItem(itemEntityList)

            //orderTransactionCompleteLiveData.postValue(Event(true))
        }
    }


    fun insertOrderWithItemEntity(orderId: String, itemId: String) {
        viewModelScope.launch {
            orderRepository.insertOrderWithItemEntities(orderId, itemId)

            //orderEntityEditIdLiveData.postValue(orderId)
        }
    }

    fun deleteOrder(orderEntity: OrderEntity) {
        viewModelScope.launch {
            orderRepository.deleteOrder(orderEntity)

            orderEntityEditIdLiveData.value = null
        }
    }

    fun updateOrder(orderEntity: OrderEntity) {
        viewModelScope.launch {
            orderRepository.updateOrder(orderEntity)

            orderTransactionCompleteLiveData.postValue(Event(true))
        }
    }
}

