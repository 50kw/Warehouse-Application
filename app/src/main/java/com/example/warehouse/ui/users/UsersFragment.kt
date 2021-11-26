package com.example.warehouse.ui.users

import android.os.Bundle
import android.view.*
import androidx.fragment.app.activityViewModels
import com.airbnb.epoxy.EpoxyController
import com.example.warehouse.R
import com.example.warehouse.database.WarehouseDatabase
import com.example.warehouse.databinding.FragmentUsersBinding
import com.example.warehouse.ui.BaseFragment

class UsersFragment : BaseFragment() {
    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!

    val usersViewModel: UsersViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        usersViewModel.init(WarehouseDatabase.getDatabase(requireContext()))

        val controller = UsersEpoxyController(::onUserSelected)
        binding.epoxyRecyclerView.setController(controller)

        usersViewModel.usersViewStateLiveData.observe(viewLifecycleOwner) { viewState ->
            controller.usersViewState = viewState
        }

    }

    private fun onUserSelected(userId: String) {

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.menuAdd) {
            navigateViaNavGraph(UsersFragmentDirections.actionNavUsersToAddUserFragment())
            true
        } else super.onOptionsItemSelected(item)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}