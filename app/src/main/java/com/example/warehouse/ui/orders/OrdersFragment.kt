package com.example.warehouse.ui.orders

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.warehouse.R
import com.example.warehouse.SharedPrefUtil
import com.example.warehouse.database.WarehouseDatabase
import com.example.warehouse.databinding.FragmentOrdersBinding
import com.example.warehouse.ui.BaseFragment
import com.example.warehouse.ui.login.LoginViewModel

class OrdersFragment : BaseFragment() {

    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!

    //private val loginViewModel: LoginViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val savedUserId = SharedPrefUtil.getSavedUserId()
        var userId = getCurrentUser()

        if (savedUserId != "none") {
            userId = savedUserId
        }

        if (userId == "none") {
            findNavController().navigate(R.id.loginFragment)

        } else {
            binding.textView.text = userId
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}