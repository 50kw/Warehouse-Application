package com.example.warehouse.ui.items

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warehouse.arch.Event
import com.example.warehouse.arch.WarehouseRepository
import com.example.warehouse.database.WarehouseDatabase
import com.example.warehouse.database.entity.ItemEntity
import com.example.warehouse.ui.login.LoginRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ItemsViewModel : ViewModel() {
    private lateinit var repository: ItemsRepository

    val itemEntitiesLiveData = MutableLiveData<List<ItemEntity>>()

    val transactionCompleteLiveData = MutableLiveData<Event<Boolean>>()

    fun init (warehouseDatabase: WarehouseDatabase) {
        repository = ItemsRepository(warehouseDatabase)

        viewModelScope.launch {
            repository.getAllItems().collect { items ->
                itemEntitiesLiveData.postValue(items)

                updateItemsViewState(items)
            }
        }

    }

    private val _itemsViewStateLiveData = MutableLiveData<ItemsViewState>()
    val itemsViewStateLiveData: LiveData<ItemsViewState>
        get() = _itemsViewStateLiveData

    data class ItemsViewState(
        val dataList: List<DataItem<*>> = emptyList(),
        val isLoading: Boolean = false
    ) {
        data class DataItem<T> (
            val data: T
        )
    }

    private fun updateItemsViewState(items: List<ItemEntity>) {
        val dataList = ArrayList<ItemsViewState.DataItem<*>>()

        items.forEach { item ->
            dataList.add(ItemsViewState.DataItem(item))
        }

        _itemsViewStateLiveData.postValue(ItemsViewState(dataList = dataList, isLoading = false))
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
}