package com.example.warehouse.ui.epoxy.models

import android.text.TextWatcher
import com.example.warehouse.R
import com.example.warehouse.databinding.ModelSearchHeaderBinding
import com.example.warehouse.ui.epoxy.ViewBindingKotlinModel

data class SearchHeaderEpoxyModel(
    val textWatcher: TextWatcher
): ViewBindingKotlinModel<ModelSearchHeaderBinding>(R.layout.model_search_header) {
    override fun ModelSearchHeaderBinding.bind() {
        searchEditText.addTextChangedListener(textWatcher)
    }
}