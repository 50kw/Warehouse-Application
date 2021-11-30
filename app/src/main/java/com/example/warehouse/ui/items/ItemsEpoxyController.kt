package com.example.warehouse.ui.items

import android.text.TextWatcher
import com.airbnb.epoxy.EpoxyController
import com.example.warehouse.R
import com.example.warehouse.database.entity.ItemEntity
import com.example.warehouse.databinding.ModelItemEntityBinding
import com.example.warehouse.databinding.ModelSearchHeaderBinding
import com.example.warehouse.ui.epoxy.ViewBindingKotlinModel
import com.example.warehouse.ui.epoxy.models.EmptyStateEpoxyModel
import com.example.warehouse.ui.epoxy.models.ItemEntityEpoxyModel
import com.example.warehouse.ui.epoxy.models.LoadingEpoxyModel
import com.example.warehouse.ui.epoxy.models.SearchHeaderEpoxyModel

class ItemsEpoxyController(
    private val onItemSelected: (String) -> Unit,
    private val textWatcher: TextWatcher
): EpoxyController() {

    var itemsViewState: ItemsViewModel.ItemsViewState = ItemsViewModel.ItemsViewState(isLoading = true)
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        if (itemsViewState.isLoading) {
            LoadingEpoxyModel().id("loading_state").addTo(this)
            return
        }

        SearchHeaderEpoxyModel(textWatcher).id("search").addTo(this)

        if (itemsViewState.dataList.isEmpty()) {
            EmptyStateEpoxyModel().id("empty_state").addTo(this)
            return
        }

        itemsViewState.dataList.forEach { dataItem ->
            val itemEntity = dataItem.data as ItemEntity
            ItemEntityEpoxyModel(itemEntity, onItemSelected).id(itemEntity.itemId).addTo(this)
        }
    }

}