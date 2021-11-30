package com.example.warehouse.ui.items

import android.content.ClipData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warehouse.arch.Event
import com.example.warehouse.database.WarehouseDatabase
import com.example.warehouse.database.entity.ItemEntity
import com.example.warehouse.database.entity.OrderEntity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ItemsViewModel : ViewModel() {
    private lateinit var repository: ItemsRepository

    val itemEntitiesLiveData = MutableLiveData<List<ItemEntity>>()

    val transactionCompleteLiveData = MutableLiveData<Event<Boolean>>()

    val itemEntityEditIdLiveData = MutableLiveData<String>()

    fun init (warehouseDatabase: WarehouseDatabase) {
        repository = ItemsRepository(warehouseDatabase)

        viewModelScope.launch {
            repository.getAllItems().collect { items ->
                itemEntitiesLiveData.postValue(items)

                updateItemsViewState(items)
            }
        }

    }

    fun findItemEntity(itemId : String) : ItemEntity {
        return itemEntitiesLiveData.value?.find { itemEntity ->
            itemEntity.itemId == itemId
        }?: ItemEntity()
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

    fun showItemEntitiesWithText(text: String) {
        val dataList = ArrayList<ItemsViewState.DataItem<*>>()

        itemEntitiesLiveData.value?.forEach { itemEntity ->
            if (itemEntity.itemName.lowercase().contains(text.lowercase()) || text.isEmpty()) {
                dataList.add(ItemsViewState.DataItem(itemEntity))
            }
        }

        _itemsViewStateLiveData.postValue(ItemsViewState(dataList = dataList, isLoading = false))
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

            itemEntityEditIdLiveData.value = null
        }
    }

    fun updateItem(itemEntity: ItemEntity) {
        viewModelScope.launch {
            repository.updateItem(itemEntity)

            transactionCompleteLiveData.postValue(Event(true))
        }
    }
}