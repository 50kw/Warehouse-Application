package com.example.warehouse.ui.items.add

import android.content.ClipData
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.warehouse.database.entity.ItemEntity
import com.example.warehouse.databinding.FragmentAddItemBinding
import com.example.warehouse.ui.BaseFragment
import com.example.warehouse.ui.items.ItemsViewModel
import java.util.*

class AddItemFragment : BaseFragment() {

    val itemsViewModel: ItemsViewModel by activityViewModels()

    private var _binding: FragmentAddItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveButton.setOnClickListener {
            if (fieldsFilled()) saveItemEntityToDatabase(generateItemEntity())
        }

        itemsViewModel.transactionCompleteLiveData.observe(viewLifecycleOwner) { event ->
            /*event.getContent()?.let {

            }*/

            Toast.makeText(requireActivity(), "Item saved!", Toast.LENGTH_SHORT).show()
            binding.nameEditText.text = null
            binding.quantityEditText.text = null
            binding.placeEditText.text = null

            binding.nameEditText.requestFocus()
        }
    }

    private fun generateItemEntity() : ItemEntity {
        val name = binding.nameEditText.text.toString().trim()
        val quantity = try {binding.quantityEditText.text.toString().toInt()} catch (e: Exception) {0}
        val place = binding.placeEditText.text.toString().trim()

        return ItemEntity(
            itemId = UUID.randomUUID().toString(),
            itemName = name,
            itemCount = quantity,
            itemPlace = place
        )
    }

    private fun fieldsFilled() : Boolean {
        val name = binding.nameEditText.text.toString().trim()
        val quantity = binding.quantityEditText.text.toString().trim()
        val place = binding.placeEditText.text.toString().trim()

        var failed: Boolean = false

        if (name.isEmpty()) {
            binding.nameLabelTextView.error = "Required field"
            failed = true
        } else {
            binding.nameLabelTextView.error = null
        }

        if (quantity.isEmpty()) {
            binding.quantityLabelTextView.error = "Required field"
            failed = true
        } else if (!quantity.isDigitsOnly()){
            binding.quantityLabelTextView.error = "Digits only"
            failed = true
        } else {
            binding.quantityLabelTextView.error = null
        }

        if (place.isEmpty()) {
            binding.placeLabelTextView.error = "Required field"
            failed = true
        } else {
            binding.placeLabelTextView.error = null
        }

        return !failed

    }

    private fun saveItemEntityToDatabase(itemEntity: ItemEntity) {
        itemsViewModel.insertItem(itemEntity)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}