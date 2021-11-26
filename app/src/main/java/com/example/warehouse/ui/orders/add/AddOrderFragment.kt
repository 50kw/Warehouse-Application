package com.example.warehouse.ui.orders.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.warehouse.R
import com.example.warehouse.database.entity.UserEntity
import com.example.warehouse.databinding.FragmentAddOrderBinding
import com.example.warehouse.databinding.FragmentAddUserBinding
import com.example.warehouse.ui.BaseFragment
import com.example.warehouse.ui.orders.OrderViewModel
import com.example.warehouse.ui.users.UsersViewModel
import java.util.*

class AddOrderFragment : BaseFragment() {
    /*val ordersViewModel: OrderViewModel by activityViewModels()

    private var _binding: FragmentAddOrderBinding? = null
    private val binding get() = _binding!!

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

        val userTypes = resources.getStringArray(R.array.user_types_array)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, userTypes)
        binding.typeEditText.setAdapter(adapter)

        binding.saveButton.setOnClickListener {
            if (fieldsFilled()) saveUserEntityToDatabase(generateUserEntity())
        }

        ordersViewModel.transactionCompleteLiveData.observe(viewLifecycleOwner) { event ->
            *//*event.getContent()?.let {

            }*//*

            Toast.makeText(requireActivity(), "User saved!", Toast.LENGTH_SHORT).show()
            binding.nameEditText.text = null
            binding.loginIdEditText.text = null
            binding.passwordEditText.text = null
            binding.typeEditText.text = null

            binding.nameEditText.requestFocus()
        }
    }

    private fun generateUserEntity() : UserEntity {
        val name = binding.nameEditText.text.toString().trim()
        val loginId = binding.loginIdEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString().trim()
        val type = binding.typeEditText.text.toString().trim()

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
        ordersViewModel.insertUser(userEntity)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }*/
}