package com.example.warehouse.ui.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.warehouse.databinding.FragmentItemsBinding
import com.example.warehouse.ui.BaseFragment

class ItemsFragment : BaseFragment(){
    private var _binding: FragmentItemsBinding? = null
    private val binding get() = _binding!!

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



    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}