package com.example.warehouse.ui.items

import android.content.ClipData
import com.airbnb.epoxy.EpoxyController
import com.example.warehouse.R
import com.example.warehouse.database.entity.ItemEntity
import com.example.warehouse.databinding.ModelItemEntityBinding
import com.example.warehouse.ui.epoxy.ViewBindingKotlinModel
import com.example.warehouse.ui.epoxy.models.EmptyStateEpoxyModel
import com.example.warehouse.ui.epoxy.models.LoadingEpoxyModel

class ItemsEpoxyController : EpoxyController() {

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

        if (itemsViewState.dataList.isEmpty()) {
            EmptyStateEpoxyModel().id("empty_state").addTo(this)
            return
        }

        itemsViewState.dataList.forEach { dataItem ->
            val itemEntity = dataItem.data as ItemEntity
            ItemEntityEpoxyModel(itemEntity).id(itemEntity.itemId).addTo(this)
        }
    }

    data class ItemEntityEpoxyModel(
        val itemEntity: ItemEntity
    ) : ViewBindingKotlinModel<ModelItemEntityBinding>(R.layout.model_item_entity) {

        override fun ModelItemEntityBinding.bind() {
            nameTextView.text = itemEntity.itemName
            countTextView.text = itemEntity.itemCount.toString()
            placeTextView.text = itemEntity.itemPlace
        }
    }
}