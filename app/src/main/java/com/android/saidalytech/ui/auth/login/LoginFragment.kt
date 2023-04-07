package com.android.saidalytech.ui.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.saidalytech.R
import com.android.saidalytech.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

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
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
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
                    observe()
                }
            }
        }
    }

    private fun observe() {

    }
}