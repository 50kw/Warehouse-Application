package com.example.warehouse.ui.orders.information

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.warehouse.arch.WarehouseViewModel
import com.example.warehouse.database.entity.ItemEntity
import com.example.warehouse.database.entity.OrderEntity
import com.example.warehouse.database.entity.OrderWithItemEntities
import com.example.warehouse.databinding.FragmentOrderInformationBinding
import com.example.warehouse.ui.BaseFragment
import com.example.warehouse.ui.orders.OrderViewModel

class OrderInformationFragment : BaseFragment(){

    private var _binding: FragmentOrderInformationBinding? = null
    private val binding get() = _binding!!

    val orderViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderInformationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val controller = OrderInformationEpoxyController(orderInformationInterface)

        binding.epoxyRecyclerView.setController(controller)

        orderViewModel.orderEntityEditIdLiveData.observe(viewLifecycleOwner) {
            controller.orderWithItemEntities = orderViewModel.findOrderWithItemEntities(it)
        }

    }

    val orderInformationInterface = object : OrderInformationInterface {
        override fun onSelectUserClicked(orderId: String) {

        }

        override fun onItemSelected(itemId: String) {

        }

        override fun onAddItemsClicked(orderId: String) {
            navigateViaNavGraph(OrderInformationFragmentDirections.actionOrderInformationFragmentToNavItems(orderId))
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        orderViewModel.orderEntityEditIdLiveData.value = null
        _binding = null
    }

}