package com.android.saidalytech.ui.forgetpass

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.android.saidalytech.R
import com.android.saidalytech.databinding.FragmentForgetPassBinding
import com.android.saidalytech.model.ModelForgetPassword
import com.android.saidalytech.ui.home.HomeViewModel
import com.android.saidalytech.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgetPassFragment : Fragment() {

    private lateinit var binding: FragmentForgetPassBinding

    private val viewModel: ForgetPassViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forget_pass, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentForgetPassBinding.bind(view)

        onClick()
    }

    private fun onClick() {
        binding.btnSend.setOnClickListener {
            validation()
        }
    }

    private fun validation() {

        val email = binding.editEmail.text.toString()

        if (email.isBlank()) {
            binding.editEmail.error = getString(R.string.required)
        } else {
            viewModel.resetPass(ModelForgetPassword(email))
            observe()
        }
    }

    private fun observe() {

        viewModel.apply {
            successMD.observe(viewLifecycleOwner){
                showToast(requireContext(), "Check your email")
            }
            failureMD.observe(viewLifecycleOwner){
                showToast(requireContext(), it)
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