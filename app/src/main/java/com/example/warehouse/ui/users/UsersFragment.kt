package com.example.warehouse.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.warehouse.databinding.FragmentUsersBinding
import com.example.warehouse.ui.BaseFragment

class UsersFragment : BaseFragment() {
    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!

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

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}