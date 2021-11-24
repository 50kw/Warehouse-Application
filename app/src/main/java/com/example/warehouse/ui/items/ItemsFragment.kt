package com.example.warehouse.ui.items

import android.os.Bundle
import android.view.*
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.warehouse.R
import com.example.warehouse.database.WarehouseDatabase
import com.example.warehouse.databinding.FragmentItemsBinding
import com.example.warehouse.ui.BaseFragment

class ItemsFragment : BaseFragment(){
    private var _binding: FragmentItemsBinding? = null
    private val binding get() = _binding!!

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

        val controller = ItemsEpoxyController()
        binding.epoxyRecyclerView.setController(controller)

        itemsViewModel.itemsViewStateLiveData.observe(viewLifecycleOwner) { viewState ->
            controller.itemsViewState = viewState
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_items_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.menuAddItem) {
            navigateViaNavGraph(ItemsFragmentDirections.actionNavItemsToAddItemFragment())
            true
        } else {
            super.onOptionsItemSelected(item)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}