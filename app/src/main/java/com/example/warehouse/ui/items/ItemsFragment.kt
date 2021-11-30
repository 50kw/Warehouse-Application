package com.example.warehouse.ui.items

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.warehouse.R
import com.example.warehouse.database.WarehouseDatabase
import com.example.warehouse.databinding.FragmentItemsBinding
import com.example.warehouse.ui.BaseFragment
import com.example.warehouse.ui.orders.OrderViewModel

class ItemsFragment : BaseFragment(){
    private var _binding: FragmentItemsBinding? = null
    private val binding get() = _binding!!

    val safeArgs: ItemsFragmentArgs by navArgs()
    private val itemSelectOrderId: String? by lazy {
        safeArgs.itemSelectOrderId
    }

    val itemsViewModel: ItemsViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemsViewModel.init(WarehouseDatabase.getDatabase(requireContext()))

        val controller = ItemsEpoxyController(::onItemSelected, textWatcher)
        binding.epoxyRecyclerView.setController(controller)

        itemsViewModel.itemsViewStateLiveData.observe(viewLifecycleOwner) { viewState ->
            controller.itemsViewState = viewState
        }

    }

    private fun onItemSelected(itemId: String) {
        if (!itemSelectOrderId.isNullOrEmpty()) {
            val orderViewModel: OrderViewModel by activityViewModels()
            orderViewModel.insertOrderWithItemEntity(itemSelectOrderId!!, itemId)
            navigateUp()
        } else {
            itemsViewModel.itemEntityEditIdLiveData.postValue(itemId)
            navigateViaNavGraph(ItemsFragmentDirections.actionNavItemsToAddItemFragment())
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.menuAdd) {
            navigateViaNavGraph(ItemsFragmentDirections.actionNavItemsToAddItemFragment())
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            // nothing
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if (p0 != null) {
                itemsViewModel.showItemEntitiesWithText(p0.toString())
            }
        }

        override fun afterTextChanged(p0: Editable?) {
            // nothing
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}