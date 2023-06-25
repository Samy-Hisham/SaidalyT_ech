package com.android.saidalytech.ui.auth.edituser

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.android.saidalytech.R
import com.android.saidalytech.databinding.FragmentEditInfoBinding
import com.android.saidalytech.model.ModelRegister
import com.android.saidalytech.ui.auth.signup.RegisterViewModel
import com.android.saidalytech.uitls.MySharedPreference
import com.android.saidalytech.utils.Const
import com.android.saidalytech.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditInfoFragment : Fragment() {

    private var _binding: FragmentEditInfoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EditInfoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEditInfoBinding.bind(view)

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
                editName.setText(MySharedPreference.getUserName())
            } else if (email.isBlank()) {
                editEmail.setText(MySharedPreference.getUserEmail())
            } else if (phone.isBlank()) {
                editPhone.setText(MySharedPreference.getUserPhone())
            } else if (age.isBlank()) {
                editAge.setText(MySharedPreference.getUserAge())
            } else if (address.isBlank()) {
                editAddress.setText(MySharedPreference.getUserAddress())
            } else if (gender.equals("Gender")) {
                Toast.makeText(requireContext(), "select your gender", Toast.LENGTH_SHORT).show()
            } else if (pass.isBlank()) {
                editPass.error = getString(R.string.required)
            } else if (confirmedPass.isBlank()) {
                editPass.error = getString(R.string.required)
            } else if (confirmedPass != pass) {
                editConfirmPass.error = getString(R.string.not_match)
            } else {
                viewModel.editInfo(
                    ModelRegister(
                        address,
                        age.toInt(),
                        confirmedPass,
                        email,
                        name,
                        gender.toString(),
                        id = MySharedPreference.getUserId().toString(),
                        pass,
                        phone,
                        role = Const.USER
                    )
                )
                observe(name, email, gender.toString(), phone, address, age)
            }
        }
    }

    private fun observe(
        name: String,
        email: String,
        gender: String,
        phone: String,
        address: String,
        age: String
    ) {
        viewModel.apply {
            successMD.observe(viewLifecycleOwner) {
                showToast(requireContext(), getString(R.string.changes_saved))

                MySharedPreference.apply {
                    setUserName(name)
                    setUserEmail(email)
                    setUserGender(gender)
                    setUserPhone(phone)
                    setUserAddress(address)
                    setUserAge(age)
                }
            }
            failureMD.observe(viewLifecycleOwner){
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