package com.android.saidalytech.ui.auth.login

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.saidalytech.R
import com.android.saidalytech.databinding.FragmentLoginBinding
import com.android.saidalytech.uitls.MySharedPreference
import com.android.saidalytech.uitls.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)

        onClick()
    }

    private fun onClick() {

        binding.txtRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnLogin.setOnClickListener {

            validation()
        }
        binding.showPass.setOnClickListener {
            binding.showPass.visibility = View.GONE
            binding.editPass.transformationMethod = HideReturnsTransformationMethod.getInstance()
            binding.hidePass.visibility = View.VISIBLE
        }
        binding.hidePass.setOnClickListener {
            binding.hidePass.visibility = View.GONE
            binding.editPass.transformationMethod = PasswordTransformationMethod.getInstance()
            binding.showPass.visibility = View.VISIBLE
        }
    }

    private fun validation() {

        binding.apply {

            val email = editEmail.text.toString().trim()
            val pass = editPass.text.toString().trim()

            when {
                email.isBlank() -> {
                    editEmail.error = getString(R.string.required)
                }
                pass.isBlank() -> {
                    editPass.error = getString(R.string.required)
                }
                else -> {
                    loginViewModel.login(email, pass)
                    observe()
                }
            }
        }
    }

    private fun observe() {

        loginViewModel.loginLiveData.observe(viewLifecycleOwner) {

            val data = it

            MySharedPreference.apply {
                setUserName(data.fullName)
                setUserEmail(data.email)
                setUserTOKEN(data.token)
                setUserId(data.userId)
            }

            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }

        loginViewModel.failureMD.observe(viewLifecycleOwner) {
            showToast(requireContext(), it)
        }
        loginViewModel.progressMD.observe(viewLifecycleOwner) {

            if (it == true) {
                binding.progress.visibility = View.VISIBLE
            } else {
                binding.progress.visibility = View.GONE
            }
        }
    }
}