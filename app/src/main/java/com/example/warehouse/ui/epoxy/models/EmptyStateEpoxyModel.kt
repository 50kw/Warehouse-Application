package com.example.warehouse.ui.epoxy.models

import com.example.warehouse.R
import com.example.warehouse.databinding.ModelNoDataFoundBinding
import com.example.warehouse.ui.epoxy.ViewBindingKotlinModel

class EmptyStateEpoxyModel :
    ViewBindingKotlinModel<ModelNoDataFoundBinding>(R.layout.model_no_data_found) {
    override fun ModelNoDataFoundBinding.bind() {
        // Nothing to do
    }
}