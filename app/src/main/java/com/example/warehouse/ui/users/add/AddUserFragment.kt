package com.example.warehouse.ui.users.add

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.warehouse.R
import com.example.warehouse.database.entity.ItemEntity
import com.example.warehouse.database.entity.OrderEntity
import com.example.warehouse.database.entity.UserEntity
import com.example.warehouse.databinding.FragmentAddUserBinding
import com.example.warehouse.ui.BaseFragment
import com.example.warehouse.ui.users.UsersViewModel
import java.util.*

class AddUserFragment : BaseFragment() {
    val usersViewModel: UsersViewModel by activityViewModels()

    private var _binding: FragmentAddUserBinding? = null
    private val binding get() = _binding!!

    private lateinit var selectedUserEntity: UserEntity
    private var inEditMode = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userTypes = resources.getStringArray(R.array.user_types_array)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, userTypes)
        binding.typeEditText.setAdapter(adapter)

        binding.saveButton.setOnClickListener {
            if (fieldsFilled()) saveUserEntityToDatabase(generateUserEntity())
        }

        usersViewModel.transactionCompleteLiveData.observe(viewLifecycleOwner) { event ->
            /*event.getContent()?.let {

            }*/

            Toast.makeText(requireActivity(), "User saved!", Toast.LENGTH_SHORT).show()
            binding.nameEditText.text = null
            binding.loginIdEditText.text = null
            binding.passwordEditText.text = null
            binding.typeEditText.text = null

            binding.nameEditText.requestFocus()
        }

        usersViewModel.userEntityEditIdLiveData.observe(viewLifecycleOwner) { userEntityId ->
            userEntityId?.let {
                setupEditMode(userEntityId)
            }
        }
    }

    private fun setupEditMode(userId: String) {
        inEditMode = true
        selectedUserEntity = usersViewModel.findUserEntity(userId).userEntity
        setHasOptionsMenu(true)

        binding.saveButton.text = "Update"
        mainActivity.supportActionBar?.title = "Update User"

        binding.nameEditText.setText(selectedUserEntity.userFullName)
        binding.loginIdEditText.setText(selectedUserEntity.userLoginId)
        binding.passwordEditText.setText(selectedUserEntity.userPassword)
        binding.typeEditText.setText(selectedUserEntity.userPosition)
        binding.nameEditText.requestFocus()
    }

    private fun generateUserEntity() : UserEntity {
        val name = binding.nameEditText.text.toString().trim()
        val loginId = binding.loginIdEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString().trim()
        val type = binding.typeEditText.text.toString().trim()

        if (inEditMode) {
            return selectedUserEntity!!.copy(
                userFullName = name,
                userLoginId = loginId,
                userPassword = password,
                userPosition = type
            )
        }

        return UserEntity(
            userId = UUID.randomUUID().toString(),
            userFullName = name,
            userLoginId = loginId,
            userPassword = password,
            userPosition = type
        )
    }

    private fun fieldsFilled() : Boolean {
        val name = binding.nameEditText.text.toString().trim()
        val loginId = binding.loginIdEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString().trim()
        val type = binding.typeEditText.text.toString().trim()

        var failed = false

        if (name.isEmpty()) {
            binding.nameLabelTextView.error = "Required field"
            failed = true
        } else {
            binding.nameLabelTextView.error = null
        }

        if (loginId.isEmpty()) {
            binding.loginIdLabelTextView.error = "Required field"
            failed = true
        } else {
            binding.loginIdLabelTextView.error = null
        }

        if (password.isEmpty()) {
            binding.passwordLabelTextView.error = "Required field"
            failed = true
        } else {
            binding.passwordLabelTextView.error = null
        }

        if (type.isEmpty()) {
            binding.typeLabelTextView.error = "Required field"
            failed = true
        } else {
            binding.typeLabelTextView.error = null
        }

        return !failed
    }

    private fun saveUserEntityToDatabase(userEntity: UserEntity) {
        if (inEditMode) {
            usersViewModel.updateUser(userEntity)
            navigateUp()
        } else {
            usersViewModel.insertUser(userEntity)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_delete, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.menuDeleteItem) {
            usersViewModel.deleteUser(selectedUserEntity)
            navigateUp()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        usersViewModel.userEntityEditIdLiveData.value = null
        _binding = null
    }
}