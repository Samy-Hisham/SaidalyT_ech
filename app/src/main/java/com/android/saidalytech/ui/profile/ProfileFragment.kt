package com.android.saidalytech.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.saidalytech.R
import com.android.saidalytech.databinding.FragmentProfileBinding
import com.android.saidalytech.uitls.MySharedPreference
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)

        showData()
        onClick()
    }

    private fun onClick() {

        binding.btn.setOnClickListener {
            MySharedPreference.setUserTOKEN(null.toString())
            findNavController().navigate(R.id.action_profileFragment_to_editInfoFragment)
        }
    }

    private fun showData() {

        binding.apply {
            MySharedPreference.apply {
                txtName.text = getUserName()
                txtEmail.text = getUserEmail()
                txtAddress.text = getUserAddress()
                txtPhone.text = getUserPhone()
                txtGender.text = getUserGender()
            }
        }
    }
}