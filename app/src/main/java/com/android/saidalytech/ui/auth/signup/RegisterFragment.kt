package com.android.saidalytech.ui.auth.signup

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.saidalytech.R
import com.android.saidalytech.databinding.FragmentRegisterBinding
import com.android.saidalytech.model.ModelRegister
import com.android.saidalytech.utils.Const
import com.android.saidalytech.uitls.MySharedPreference
import com.android.saidalytech.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val registerViewModel: RegisterViewModel by viewModels()
    var flag: Boolean = false

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

        binding.showConfirmedPass.setOnClickListener {
            binding.showConfirmedPass.visibility = View.GONE
            binding.editConfirmPass.transformationMethod =
                HideReturnsTransformationMethod.getInstance()
            binding.hideConfirmedPass.visibility = View.VISIBLE
        }
        binding.hideConfirmedPass.setOnClickListener {
            binding.hideConfirmedPass.visibility = View.GONE
            binding.editConfirmPass.transformationMethod =
                PasswordTransformationMethod.getInstance()
            binding.showConfirmedPass.visibility = View.VISIBLE
        }
    }

    private fun validation() {

        binding.apply {
            val name = editName.text.toString().trim()
            val email = editEmail.text.toString().trim()
            val pass = editPass.text.toString().trim()
            val confirmedPass = editConfirmPass.text.toString().trim()
            val phone = editPhone.text.toString().trim()
            val gender = spinnerGender.selectedItem
            val age = editAge.text.toString().trim()
            val address = editAddress.text.toString().trim()

            if (name.isBlank()) {
                editName.error = getString(R.string.required)
            } else if (email.isBlank()) {
                editEmail.error = getString(R.string.required)
            } else if (pass.isBlank()) {
                editPass.error = getString(R.string.required)
            } else if (confirmedPass.isBlank()) {
                editPass.error = getString(R.string.required)
            } else if (confirmedPass != pass) {
                editConfirmPass.error = getString(R.string.not_match)
            } else if (phone.isBlank()) {
                editPhone.error = getString(R.string.required)
            } else if (age.isBlank()) {
                editAge.error = getString(R.string.required)
            } else if (address.isBlank()) {
                editAddress.error = getString(R.string.required)
            } else if (gender.equals("Gender")) {
                Toast.makeText(requireContext(), "select your gender", Toast.LENGTH_SHORT).show()
            } else {
                registerViewModel.register(
                    ModelRegister(
                        address,
                        age.toInt(),
                        confirmedPass,
                        email,
                        name,
                        gender as String,
                        id = "",
                        pass,
                        phone,
                        role = Const.USER
                    )
                )

                observe(gender.toString(), phone, address, age)
            }
        }
    }


    private fun observe(gender: String, phone: String, address: String, age: String) {

        registerViewModel.apply {

            successMD.observe(viewLifecycleOwner) {
                flag = true
                val data = it

                showToast(requireContext(), getString(R.string.Successful_Register))

                MySharedPreference.apply {
                    setUserName(data.fullName)
                    setUserEmail(data.email)
                    setUserTOKEN(data.token)
                }
                if (flag) {
                    MySharedPreference.apply {
                        setUserGender(gender)
                        setUserAddress(address)
                        setUserPhone(phone)
                        setUserAge(age)
                    }
                }

                findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
            }
            failureMD.observe(viewLifecycleOwner) {

                showToast(requireContext(), it.error)
            }
            progressMD.observe(viewLifecycleOwner) {

                if (it == true) {
                    binding.progress.visibility = View.VISIBLE
                } else {
                    binding.progress.visibility = View.GONE
                }
            }
        }
    }
}