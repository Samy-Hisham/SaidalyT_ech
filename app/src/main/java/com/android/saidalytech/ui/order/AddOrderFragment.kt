package com.android.saidalytech.ui.order

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.saidalytech.R
import com.android.saidalytech.adapter.AdapterItemRecycle
import com.android.saidalytech.databinding.FragmentAddOrderBinding
import com.android.saidalytech.model.ModelItemDetail
import com.android.saidalytech.ui.home.HomeViewModel
import com.android.saidalytech.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddOrderFragment : Fragment() {

    private var _binding: FragmentAddOrderBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: HomeViewModel by activityViewModels()
    private val orderViewModel: OrderViewModel by viewModels()

    var list: MutableList<ModelItemDetail>? = null

    private val adapter: AdapterItemRecycle by lazy { AdapterItemRecycle() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_order, container, false)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddOrderBinding.bind(view)

        observe()
        onClick()
        observeForUi()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun observeForUi() {
        sharedViewModel.currentItemActionIndex.observe(viewLifecycleOwner) { itemX ->
            adapter.setList(sharedViewModel.shoppingCartItems)
            adapter.notifyDataSetChanged()
        }
    }

    private fun observe() {
        adapter.setList(sharedViewModel.shoppingCartItems)
        binding.recycle.adapter = adapter
    }

    private fun onClick() {

        binding.addMore.setOnClickListener {
            findNavController().navigate(R.id.action_addOrderFragment_to_homeFragment)
        }

        binding.buy.setOnClickListener {

            orderViewModel.sendOrder(sharedViewModel.shoppingCartItems)

            orderViewModel.successMD.observe(viewLifecycleOwner) {
                showToast(requireContext(), "Your Order Is Added")
                findNavController().navigate(R.id.action_addOrderFragment_to_homeFragment)

            }
            orderViewModel.failureMD.observe(viewLifecycleOwner) {
                showToast(requireContext(), it)
            }
            orderViewModel.progressMD.observe(viewLifecycleOwner) {
                if (it == true) {
                    binding.progress.visibility = View.VISIBLE
                } else {
                    binding.progress.visibility = View.GONE
                }
            }
        }

        adapter.onUserClicks = object : AdapterItemRecycle.OnUserClicks {
            override fun onClickPlus(id: Int) {
                sharedViewModel.incrementItemQuantity(id)
            }

            override fun onClickMines(id: Int) {
                sharedViewModel.decrementItemQuantity(id)
            }

        }
    }

}
