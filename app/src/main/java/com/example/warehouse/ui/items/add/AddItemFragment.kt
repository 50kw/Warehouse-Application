package com.example.warehouse.ui.items.add

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.activityViewModels
import com.example.warehouse.R
import com.example.warehouse.database.entity.ItemEntity
import com.example.warehouse.databinding.FragmentAddItemBinding
import com.example.warehouse.ui.BaseFragment
import com.example.warehouse.ui.items.ItemsViewModel
import java.util.*

class AddItemFragment : BaseFragment() {

    val itemsViewModel: ItemsViewModel by activityViewModels()

    private var _binding: FragmentAddItemBinding? = null
    private val binding get() = _binding!!

    private lateinit var selectedItemEntity : ItemEntity
    private var inEditMode = false

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

        binding.nameEditText.requestFocus()

        itemsViewModel.transactionCompleteLiveData.observe(viewLifecycleOwner) { event ->
            event.getContent()?.let { complete ->
                if (complete) {
                    Toast.makeText(requireActivity(), "Item saved!", Toast.LENGTH_SHORT).show()
                    resetFields()
                }
            }
        }

        itemsViewModel.itemEntityEditIdLiveData.observe(viewLifecycleOwner) { itemEntityId ->
            itemEntityId?.let {
                setupEditMode(itemEntityId)
            }
        }

    }

    private fun resetFields() {
        binding.nameEditText.text = null
        binding.quantityEditText.text = null
        binding.placeEditText.text = null
        binding.nameEditText.requestFocus()
    }

    private fun setupEditMode(itemId: String) {
        inEditMode = true
        selectedItemEntity = itemsViewModel.findItemEntity(itemId)
        setHasOptionsMenu(true)

        binding.saveButton.text = "Update"
        mainActivity.supportActionBar?.title = "Update Item"

        binding.nameEditText.setText(selectedItemEntity.itemName)
        binding.quantityEditText.setText(selectedItemEntity.itemCount.toString())
        binding.placeEditText.setText(selectedItemEntity.itemPlace)
        binding.nameEditText.requestFocus()
    }

    private fun generateItemEntity() : ItemEntity {
        val name = binding.nameEditText.text.toString().trim()
        val quantity = try {binding.quantityEditText.text.toString().toInt()} catch (e: Exception) {0}
        val place = binding.placeEditText.text.toString().trim()

        if (inEditMode) {
            return selectedItemEntity!!.copy(
                itemName = name,
                itemCount = quantity,
                itemPlace = place
            )
        }

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
        if (inEditMode) {
            itemsViewModel.updateItem(itemEntity)
            navigateUp()
        } else {
            itemsViewModel.insertItem(itemEntity)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_delete, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.menuDeleteItem) {
            itemsViewModel.deleteItem(selectedItemEntity)
            navigateUp()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        itemsViewModel.itemEntityEditIdLiveData.value = null
        _binding = null
    }
}