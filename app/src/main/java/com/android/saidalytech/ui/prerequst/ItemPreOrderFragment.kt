package com.android.saidalytech.ui.prerequst

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.android.saidalytech.R
import com.android.saidalytech.adapter.AdapterItemSelectedRecycle
import com.android.saidalytech.databinding.FragmentItemPreOrderBinding
import com.android.saidalytech.model.Item
import com.android.saidalytech.uitls.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemPreOrderFragment : Fragment() {

    private var _binding: FragmentItemPreOrderBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PreOrderViewModel by viewModels()

    private val adapter: AdapterItemSelectedRecycle by lazy { AdapterItemSelectedRecycle() }

    var orderId = 0
    var userId = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item_pre_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentItemPreOrderBinding.bind(view)

        orderId = ItemPreOrderFragmentArgs.fromBundle(requireArguments()).orderId
        userId = ItemPreOrderFragmentArgs.fromBundle(requireArguments()).userId

        viewModel.getPreOrder(userId)
        observe()
    }

    private fun observe() {
        viewModel.apply {
            successMD.observe(viewLifecycleOwner) {

                val data = it

                val filterList = data.filter {
                    it.orderId == orderId
                }.map {
                    it.items
                }.map {
                    it.toMutableList()
                    adapter.setList(it as MutableList<Item>)
                    binding.recycleItem.adapter = adapter
                }.toMutableList()

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