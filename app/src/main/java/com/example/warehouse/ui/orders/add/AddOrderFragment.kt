package com.example.warehouse.ui.orders.add

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.warehouse.Constants
import com.example.warehouse.R
import com.example.warehouse.database.entity.ItemEntity
import com.example.warehouse.database.entity.OrderEntity
import com.example.warehouse.databinding.FragmentAddOrderBinding
import com.example.warehouse.ui.BaseFragment
import com.example.warehouse.ui.orders.OrderViewModel
import java.util.*

class AddOrderFragment : BaseFragment() {
    val ordersViewModel: OrderViewModel by activityViewModels()

    private var _binding: FragmentAddOrderBinding? = null
    private val binding get() = _binding!!

    private lateinit var selectedOrderEntity: OrderEntity
    private var inEditMode = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveButton.setOnClickListener {
            if (fieldsFilled()) saveOrderEntityToDatabase(generateOrderEntity())
        }

        binding.nameEditText.requestFocus()

        ordersViewModel.orderTransactionCompleteLiveData.observe(viewLifecycleOwner) { event ->
            event.getContent()?.let { complete ->
                if (complete) {
                    Toast.makeText(requireActivity(), "Order saved!", Toast.LENGTH_SHORT).show()
                    resetFields()
                }
            }

        }

        ordersViewModel.orderEntityEditIdLiveData.observe(viewLifecycleOwner) { itemEntityId ->
            itemEntityId?.let {
                setupEditMode(itemEntityId)
            }
        }

    }

    private fun resetFields() {
        binding.nameEditText.text = null
        binding.descriptionEditText.text = null
        binding.nameEditText.requestFocus()
    }

    private fun setupEditMode(orderId: String) {
        inEditMode = true
        selectedOrderEntity = ordersViewModel.findItemEntity(orderId).orderEntity
        setHasOptionsMenu(true)

        binding.saveButton.text = "Update"
        mainActivity.supportActionBar?.title = "Update order"

        binding.nameEditText.setText(selectedOrderEntity.orderName)
        binding.descriptionEditText.setText(selectedOrderEntity.orderDescription)
        binding.statusRadioGroup.check(getOrderStatusRadioButton(selectedOrderEntity.orderStatus))
        binding.nameEditText.requestFocus()
    }

    private fun generateOrderEntity(): OrderEntity {
        val name = binding.nameEditText.text.toString().trim()
        val description = binding.descriptionEditText.text.toString().trim()

        if (inEditMode) {
            return selectedOrderEntity!!.copy(
                orderName = name,
                orderDescription = description,
                orderStatus = getOrderStatus()
            )
        }

        return OrderEntity(
            orderId = UUID.randomUUID().toString(),
            orderName = name,
            orderDescription = description,
            orderStatus = getOrderStatus()
        )
    }

    private fun getOrderStatusRadioButton(status: String) : Int {
        return when (status) {
            Constants.ORDER_PENDING -> binding.pendingRadioButton.id
            Constants.ORDER_IN_PROGRESS -> binding.inProgressRadioButton.id
            Constants.ORDER_COMPLETED -> binding.completedRadioButton.id
            else -> binding.pendingRadioButton.id
        }
    }

    private fun getOrderStatus() : String {
        return when (binding.statusRadioGroup.checkedRadioButtonId) {
            binding.pendingRadioButton.id -> Constants.ORDER_PENDING
            binding.inProgressRadioButton.id -> Constants.ORDER_IN_PROGRESS
            binding.completedRadioButton.id -> Constants.ORDER_COMPLETED
            else -> Constants.ORDER_PENDING
        }
    }

    private fun fieldsFilled(): Boolean {
        val name = binding.nameEditText.text.toString().trim()
        val description = binding.descriptionEditText.text.toString().trim()

        var failed: Boolean = false

        if (name.isEmpty()) {
            binding.nameLabelTextView.error = "Required field"
            failed = true
        } else {
            binding.nameLabelTextView.error = null
        }

        if (description.isEmpty()) {
            binding.descriptionEditText.error = "Required field"
            failed = true
        } else {
            binding.descriptionEditText.error = null
        }

        return !failed
    }

    private fun saveOrderEntityToDatabase(orderEntity: OrderEntity) {
        if (inEditMode) {
            ordersViewModel.updateOrder(orderEntity)
            navigateUp()
        } else {
            ordersViewModel.insertOrder(orderEntity)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_delete, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.menuDeleteItem) {
            ordersViewModel.deleteOrder(selectedOrderEntity)
            navigateUp()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ordersViewModel.orderEntityEditIdLiveData.value = null
        _binding = null
    }
}