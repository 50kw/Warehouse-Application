package com.example.warehouse.ui.login

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.warehouse.R
import com.example.warehouse.SharedPrefUtil
import com.example.warehouse.database.WarehouseDatabase
import com.example.warehouse.databinding.FragmentLoginBinding
import com.example.warehouse.ui.BaseFragment

class LoginFragment : BaseFragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val loginViewModel: LoginViewModel by viewModels()

    private val safeArgs: LoginFragmentArgs by navArgs()
    private val login: Boolean by lazy {
        safeArgs.login
    }

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

        setHasOptionsMenu(true)

        loginViewModel.init(WarehouseDatabase.getDatabase(requireContext()))

        setDrawerLocked(true)

        if (!login) {
            SharedPrefUtil.setSavedUserId("none")
            SharedPrefUtil.setCurrentUserId("none")
            setCurrentUser("none")
        }

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

        }

        binding.loginButton.setOnClickListener {
            if (noInputErrors()) {
                login(
                    binding.userIdEditText.text.toString(),
                    binding.passwordEditText.text.toString()
                )
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.menuAdd) {
            navigateViaNavGraph(LoginFragmentDirections.actionLoginFragmentToNavUsers())
            true
        } else super.onOptionsItemSelected(item)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}