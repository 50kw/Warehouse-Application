package com.example.warehouse.ui.orders

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.warehouse.databinding.FragmentOrdersBinding
import com.example.warehouse.ui.BaseFragment

class OrdersFragment : BaseFragment() {

    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        Log.i("locaf", "Orders Fragment - onCreateView")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.i("locaf", "Orders Fragment - onViewCreated")

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}