package com.android.saidalytech.ui.prerequst

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.saidalytech.R
import com.android.saidalytech.adapter.AdapterPreOrderRecycle
import com.android.saidalytech.databinding.FragmentPreRequestBinding
import com.android.saidalytech.uitls.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PreRequestFragment : Fragment() {

    private var _binding: FragmentPreRequestBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PreOrderViewModel by viewModels()

    private val adapterPreOrderRecycle: AdapterPreOrderRecycle by lazy { AdapterPreOrderRecycle() }

    var userId = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pre_request, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPreRequestBinding.bind(view)

        userId = PreRequestFragmentArgs.fromBundle(requireArguments()).userId

        viewModel.getPreOrder(userId)
        observe()
        onClicK()
    }

    private fun onClicK() {
        adapterPreOrderRecycle.onUserClicks = object : AdapterPreOrderRecycle.OnUserClicks {
            override fun onClick(id: Int) {
                findNavController().navigate(PreRequestFragmentDirections.actionPreRequestFragmentToItemPreOrderFragment(
                    id, userId))
            }
        }
    }

    private fun observe() {
        viewModel.apply {
            successMD.observe(viewLifecycleOwner) {

                val data = it

                adapterPreOrderRecycle.setList(data)
                binding.recycleOrder.adapter = adapterPreOrderRecycle
            }
            failureMD.observe(viewLifecycleOwner) {
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