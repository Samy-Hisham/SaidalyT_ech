package com.android.saidalytech.ui.itemdetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.android.saidalytech.R
import com.android.saidalytech.databinding.FragmentItemDetailBinding
import com.android.saidalytech.model.ModelDataItem
import com.android.saidalytech.ui.home.HomeViewModel
import com.android.saidalytech.utils.showToast
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemDetailFragment : Fragment() {

    private var _binding: FragmentItemDetailBinding? = null
    private val binding get() = _binding!!

    private val itemDetailViewModel: ItemDetailViewModel by activityViewModels()
    private val sharedViewModel: HomeViewModel by activityViewModels()

    var itemId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentItemDetailBinding.bind(view)

        itemId = ItemDetailFragmentArgs.fromBundle(requireArguments()).id

        itemDetailViewModel.getItemDetail(itemId)
        observe()

    }

    private fun onClick(itemImage: String, itemName: String, itemId: Int, itemPrice: Double) {
        binding.addCart.setOnClickListener {
            val newItemToAdd = ModelDataItem(
                -1,
                itemImage,
                itemId,
                itemName,
                itemPrice
            )
            sharedViewModel.addItemToCart(newItemToAdd)

            findNavController().navigate(R.id.action_itemDetailFragment_to_addOrderFragment)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observe() {

        itemDetailViewModel.apply {
            successMD.observe(viewLifecycleOwner) {

                val data = it


                data.apply {
                    binding.nameItem.text = itemName
                    binding.nameCompany.text = company
                    binding.nameComposition.text = composition
                    binding.activeIngredient.text = activeIngredient
                    binding.pamphlet.text = pamphlet
                    binding.dosage.text = dosage
                    binding.price.text = "$salesPrice EGP"
                }

                Glide.with(binding.root.context)
                    .load(data?.imageName)
                    .into(binding.imgItem)

                onClick(it.imageName, it.itemName, it.itemId, it.salesPrice)
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