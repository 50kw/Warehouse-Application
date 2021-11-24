package com.example.warehouse.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.warehouse.R
import com.example.warehouse.SharedPrefUtil
import com.example.warehouse.database.WarehouseDatabase
import com.example.warehouse.databinding.FragmentLoginBinding
import com.example.warehouse.ui.BaseFragment
import com.example.warehouse.ui.orders.OrderViewModel

class LoginFragment : BaseFragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel.init(WarehouseDatabase.getDatabase(requireContext()))

        //findNavController().popBackStack()
        /*val actionBar = requireActivity().actionBar
        actionBar?.setDisplayHomeAsUpEnabled(false)
        actionBar?.setDisplayShowHomeEnabled(false)
        actionBar?.setHomeButtonEnabled(false)*/
        val navIcon = mainActivity.toolbar.navigationIcon
        mainActivity.toolbar.navigationIcon = null

        loginViewModel.userId.observe(viewLifecycleOwner) { userId ->
            if (userId == "wrong") {
                binding.wrongDataTextView.isVisible = true
            } else if (userId != "none") {
                binding.wrongDataTextView.isVisible = false
                if (binding.rememberCheckBox.isChecked) {
                    SharedPrefUtil.setSavedUserId(userId)
                } else {
                    SharedPrefUtil.setSavedUserId("none")
                }
                setCurrentUser(userId)
                mainActivity.toolbar.navigationIcon = navIcon
                val navDirections = LoginFragmentDirections.actionLoginFragmentToNavOrders()
                navigateViaNavGraph(navDirections)
                return@observe
            }
            //Log.i("userid", userId)
        }


        binding.loginButton.setOnClickListener {
            /*val navDirections = LoginFragmentDirections.actionLoginFragmentToOrdersFragment()
            navigateViaNavGraph(navDirections)*/
            if (noInputErrors()) {
                login(binding.userIdEditText.text.toString(), binding.passwordEditText.text.toString())
            }
        }


    }

    private fun noInputErrors(): Boolean {
        val userIdString = binding.userIdEditText.text.toString().trim()
        val userPasswordString = binding.passwordEditText.text.toString().trim()

        when {
            userIdString.isEmpty() -> {
                binding.userIdInputLayout.error = "Required field"
            }
            userPasswordString.isEmpty() -> {
                binding.passwordInputLayout.error = "Required field"
            }
            else -> {
                binding.userIdInputLayout.error = null
                binding.passwordInputLayout.error = null
                return true
            }
        }
        return false
    }

    private fun login(userId: String, password: String) {
        loginViewModel.getUserId(userId, password)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}