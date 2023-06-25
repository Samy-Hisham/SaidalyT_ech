package com.android.saidalytech.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.android.saidalytech.R
import com.android.saidalytech.adapter.AdapterRecycleTabs
import com.android.saidalytech.adapter.AdapterRecyclerItems
import com.android.saidalytech.databinding.FragmentHomeBinding
import com.android.saidalytech.model.ModelAllCategoriesItem
import com.android.saidalytech.model.ModelData
import com.android.saidalytech.uitls.MySharedPreference
import com.android.saidalytech.utils.showToast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
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

        binding.btn.setOnClickListener {
            val bsDialog = BottomSheetDialog(requireContext())
            val vieww = layoutInflater.inflate(R.layout.bottom_sheet, null)

            val btnAdd = vieww.findViewById<MaterialButton>(R.id.add)
            val btnClose = vieww.findViewById<ImageView>(R.id.close)

            btnAdd.setOnClickListener {

                val id = vieww.findViewById<EditText>(R.id.edit_search)

                val code = id.text.toString()

                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToRoshtaFragment(
                        code
                    )
                )
                bsDialog.dismiss()
            }

            btnClose.setOnClickListener {

                bsDialog.dismiss()
            }

            bsDialog.setCancelable(true)
            bsDialog.setContentView(vieww)
            bsDialog.show()
        }

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
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToItemDetailFragment(
                        id
                    )
                )
            }
        }

        binding.preCart.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToPreRequestFragment(
                    MySharedPreference.getUserId().toString()
                )
            )
        }

//        binding.editSearch.doOnTextChanged { text, start, before, count ->
//            filterData(text.toString())
////            adapterRecyclerItems.searchBy(text.toString())
//        }
    }


//    @SuppressLint("NotifyDataSetChanged")
//    fun filterData(query: String) {
//        val filteredData = list?.filter {
//            it.itemName.lowercase().contains(query.lowercase(), ignoreCase = true)
//        }
//
//        filteredData?.let {
//            adapterRecyclerItems.setData(it)
//            adapterRecyclerItems.notifyDataSetChanged()
//        }
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