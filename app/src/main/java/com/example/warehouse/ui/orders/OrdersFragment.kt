package com.example.warehouse.ui.orders

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.warehouse.R
import com.example.warehouse.SharedPrefUtil
import com.example.warehouse.database.WarehouseDatabase
import com.example.warehouse.databinding.FragmentOrdersBinding
import com.example.warehouse.ui.BaseFragment
import com.example.warehouse.ui.login.LoginViewModel
import com.example.warehouse.ui.users.UsersFragmentDirections

class OrdersFragment : BaseFragment() {

    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!

    val orderViewModel: OrderViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val savedUserId = SharedPrefUtil.getSavedUserId()
        var userId = getCurrentUser()
        if (savedUserId != "none") {
            userId = savedUserId
        }
        if (userId == "none") {
            navigateViaNavGraph(OrdersFragmentDirections.actionNavOrdersToLoginFragment(true))
        }

        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        orderViewModel.init(WarehouseDatabase.getDatabase(requireContext()))

        val controller = OrderEpoxyController(::onOrderSelected, ::onMoreSelected)
        binding.epoxyRecyclerView.setController(controller)

        orderViewModel.ordersViewStateLiveData.observe(viewLifecycleOwner) { viewState ->
            controller.ordersViewState = viewState
        }
    }

    private fun onMoreSelected(orderId: String) {
        orderViewModel.orderEntityEditIdLiveData.postValue(orderId)
        navigateViaNavGraph(OrdersFragmentDirections.actionNavOrdersToOrderInformationFragment())
    }


    private fun onOrderSelected(orderId: String) {
        orderViewModel.orderEntityEditIdLiveData.postValue(orderId)
        navigateViaNavGraph(OrdersFragmentDirections.actionNavOrdersToAddOrderFragment())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.menuAdd) {
            navigateViaNavGraph(OrdersFragmentDirections.actionNavOrdersToAddOrderFragment())
            true
        } else super.onOptionsItemSelected(item)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}