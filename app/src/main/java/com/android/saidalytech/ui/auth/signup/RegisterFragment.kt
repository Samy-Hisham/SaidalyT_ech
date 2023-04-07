package com.android.saidalytech.ui.auth.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.saidalytech.R
import com.android.saidalytech.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRegisterBinding.bind(view)

        onClick()
    }

    private fun onClick() {

        binding.btnSignUp.setOnClickListener {
            validation()
        }
    }

    private fun validation() {

        binding.apply {
            val name = editName.text.toString().trim()
            val email = editEmail.text.toString().trim()
            val pass = editPass.text.toString().trim()
            val phone = editPhone.text.toString().trim()
            val gender = spinnerGender.selectedItem
            val address = editAddress.text.toString().trim()

            when {
                name.isBlank() -> {
                    editName.error = getString(R.string.required)
                }
                email.isBlank() -> {
                    editEmail.error = getString(R.string.required)
                }
                pass.isBlank() -> {
                    editPass.error = getString(R.string.required)
                }
                phone.isBlank() -> {
                    editPhone.error = getString(R.string.required)
                }
                gender.equals("Gender") -> {
                    Toast.makeText(requireContext(), "select your gender", Toast.LENGTH_SHORT)
                        .show()
                }
                address.isBlank() -> {
                    editAddress.error = getString(R.string.required)
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