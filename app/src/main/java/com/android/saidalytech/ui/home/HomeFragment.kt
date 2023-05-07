package com.android.saidalytech.ui.home

import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.saidalytech.R
import com.android.saidalytech.adapter.AdapterRecycleTabs
import com.android.saidalytech.adapter.AdapterRecyclerItems
import com.android.saidalytech.databinding.FragmentHomeBinding
import com.android.saidalytech.model.ModelAllCategoriesItem
import com.android.saidalytech.model.ModelData
import com.android.saidalytech.model.ModelDataItem
import com.android.saidalytech.uitls.MySharedPreference
import com.android.saidalytech.uitls.showToast
import dagger.hilt.android.AndroidEntryPoint

@Suppress("NAME_SHADOWING")
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModelViewModel: HomeViewModel by activityViewModels()
    private val adapterRecyclerItems: AdapterRecyclerItems by lazy { AdapterRecyclerItems() }
    private val adapterRecycleTabs: AdapterRecycleTabs by lazy { AdapterRecycleTabs() }

    var data: ModelData? = null
    var id: Int? = null

    val list: MutableList<ModelDataItem>? = null


    var flag: Boolean = false

    var modelAllCategoriesItem = ModelAllCategoriesItem(0, "All")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        observe()
        onClick()
    }

    private fun onClick() {

        binding.profile.setOnClickListener {
            flag = false
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }

        adapterRecycleTabs.onTapClick = object : AdapterRecycleTabs.OnTapClick {
            override fun onClick(item: ModelAllCategoriesItem) {

                if (item.id == 0) {
                    flag = true
                    observe()
                } else {
                    flag = false
                    getDataById(item)
                }
            }
        }

        adapterRecyclerItems.onUserClicks = object : AdapterRecyclerItems.OnUserClicks {
            override fun onClick(id: Int) {
                flag = false
                adapterRecycleTabs.selectedItem = id
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToItemDetailFragment(
                    id))
            }
        }

        binding.preCart.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToPreRequestFragment(
                MySharedPreference.getUserId().toString()))
        }

//        binding.editSearch.doOnTextChanged { text, start, before, count ->
//            filterData(text.toString())
//        }
    }

//    fun search(query: String): List<ModelDataItem> {
//        return data!!.filter {
//            it.itemName.contains(query, ignoreCase = true)
//        }
//    }

//    fun filterData(query: String) {
//        val filteredData = list?.filter { it.itemName.uppercase().contains(query, ignoreCase = true) }
//        adapterRecyclerItems.setData(filteredData!!)
//    }

    private fun getDataById(item: ModelAllCategoriesItem) {

        this.id = item.id

        sharedViewModelViewModel.apply {

            successMMD.observe(viewLifecycleOwner) {

                if (!flag) {
                    val data = it as ModelData

                    val filteredList = data.filter { modelDataItem ->
                        modelDataItem.categoryId == id
                    }.toMutableList()

                    adapterRecyclerItems.setList(filteredList)
                }
            }
            failureMMD.observe(viewLifecycleOwner) {
                showToast(requireContext(), it)
            }
            progressMMD.observe(viewLifecycleOwner) {
                if (it == true) {
                    binding.progress.visibility = View.VISIBLE
                } else {
                    binding.progress.visibility = View.GONE
                }
            }
        }
    }

    private fun observe() {

        sharedViewModelViewModel.getData(data.toString())
        sharedViewModelViewModel.apply {
            successMD.observe(viewLifecycleOwner) { it ->

                val data = it as ModelData

                if (flag) {
                    adapterRecyclerItems.setList(data)
                } else {
                    adapterRecyclerItems.setList(data)
                    binding.recycleItem.adapter = adapterRecyclerItems
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

        sharedViewModelViewModel.getAllCategories(data.toString())
        sharedViewModelViewModel.apply {
            successCategoryMD.observe(viewLifecycleOwner) {
                val data = it

                val mulist = data.toMutableList()
                mulist.add(0, ModelAllCategoriesItem(0, "All"))

                adapterRecycleTabs.list = mulist

                binding.recycleTabs.adapter = adapterRecycleTabs
            }
        }
    }
}