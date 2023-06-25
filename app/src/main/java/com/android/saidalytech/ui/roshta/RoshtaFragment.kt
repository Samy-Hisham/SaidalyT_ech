package com.android.saidalytech.ui.roshta

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.saidalytech.R
import com.android.saidalytech.adapter.AdapterRoshetarRecycle
import com.android.saidalytech.databinding.FragmentRoshtaBinding
import com.android.saidalytech.model.ItemX
import com.android.saidalytech.model.ItemXX
import com.android.saidalytech.ui.order.OrderViewModel
import com.android.saidalytech.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoshtaFragment : Fragment() {

    private lateinit var binding: FragmentRoshtaBinding

    private val viewModel: RoshetaViewModel by viewModels()
    private val orderViewModel: OrderViewModel by viewModels()

    private val adapter: AdapterRoshetarRecycle by lazy { AdapterRoshetarRecycle() }

    var id = ""

    var buyList: MutableList<ItemXX>? = null
    var order: MutableList<ItemX>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_roshta, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRoshtaBinding.bind(view)

        id = RoshtaFragmentArgs.fromBundle(requireArguments()).orderId
        viewModel.addRosheta(id = id)


        if (id.isNotBlank()) {
            observe()
        } else {
            showToast(requireContext(), "Error")
        }

        onClick()
    }

    private fun observe() {

        viewModel.apply {
            successMD.observe(viewLifecycleOwner) {

                val data = it

                val filterList = data.items
                adapter.setList(filterList)
                binding.recycleOrder.adapter = adapter

                buyList = filterList.toMutableList()

            }
            failureMD.observe(viewLifecycleOwner) {
                showToast(requireContext(), "fail")
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

    private fun convert(itemXXList: List<ItemXX>): List<ItemX> {
        return itemXXList.map { itemxx ->
            ItemX(
                45,
                itemxx.notes,
                itemxx.price.toDouble(),
                itemxx.qty,
                itemxx.imageName,
                itemxx.name
            )
        }
    }

    fun onClick() {

        binding.buy.setOnClickListener {

            if (buyList!!.isEmpty()) {
                showToast(requireContext(), "try again")
            } else {

                val items = convert(buyList!!)

                orderViewModel.sendOrder(items)

                orderViewModel.successMD.observe(viewLifecycleOwner) {
                    showToast(requireContext(), "Your Order Is Added")

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
        }
    }
}