package com.android.saidalytech.ui.home

import android.content.ContentValues
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.saidalytech.model.*
import com.android.saidalytech.network.SingleLiveEvent
import com.android.saidalytech.network.WebServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("NAME_SHADOWING")
@HiltViewModel
class HomeViewModel
@Inject constructor(private val webServices: WebServices) : ViewModel() {

    private var _successMD = SingleLiveEvent<ModelData>()
    val successMD get() = _successMD

    private var _failureMD = SingleLiveEvent<ModelFailure>()
    val failureMD get() = _failureMD

    private var _progress = SingleLiveEvent<Boolean>()
    val progressMD get() = _progress

    private var _successMMD = MutableLiveData<ModelData>()
    val successMMD get() = _successMMD

    private var _failureMMD = MutableLiveData<ModelFailure>()
    val failureMMD get() = _failureMMD

    private var _progressM = MutableLiveData<Boolean>()
    val progressMMD get() = _progressM

    fun getData(data: String) {

        viewModelScope.launch(Dispatchers.IO) {
            progressMD.postValue(true)
            progressMMD.postValue(true)

            try {
                val data = webServices.getItems(data)
                successMD.postValue(data)
                progressMD.postValue(false)

                successMMD.postValue(data)
                progressMMD.postValue(false)

            } catch (e: Exception) {

                failureMD.postValue(ModelFailure(e.toString()))
                progressMD.postValue(false)

                failureMMD.postValue(ModelFailure(e.toString()))
                progressMMD.postValue(false)
            }
        }
    }

    private var _successCategoryMD = MutableLiveData<ModelAllCategories>()
    val successCategoryMD get() = _successCategoryMD

    private var _failureCategoryMD = MutableLiveData<ModelFailure>()
    val failureCategoryMD get() = _failureCategoryMD


    fun getAllCategories(data: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                val data = webServices.getAllCategories(data)
                successCategoryMD.postValue(data)

            } catch (e: Exception) {

                failureCategoryMD.postValue(ModelFailure(e.toString()))
            }
        }
    }

    /////
    private var _shoppingCartItems = mutableListOf<ItemX>()
    val shoppingCartItems get() = _shoppingCartItems.toList()

    fun addItemToCart(item: ModelDataItem) {
        val hasProduct = _shoppingCartItems.any { itemX ->
            itemX.itemId == item.itemId
        }

        if (hasProduct) {
            val currentClickedItem = _shoppingCartItems.find { itemX ->
                itemX.itemId == item.itemId
            }
            currentClickedItem?.qty?.plus(1)
            return
        }

        val newItemToAdd = ItemX(
            item.itemId,
            "",
            itemImage = item.imageName,
            price = item.salesPrice,
            qty = 1,
            itemName = item.itemName
        )
        _shoppingCartItems.add(newItemToAdd)
        return
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun removeItemFromCartByItemId(itemId: Int) {
        _shoppingCartItems.removeIf { item ->
            item.itemId == itemId
        }
        return
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private var _currentItemActionIndex = MutableLiveData<Unit>()
    val currentItemActionIndex get() = _currentItemActionIndex

    fun incrementItemQuantity(itemId: Int){
        val itemToUpdate = _shoppingCartItems.first { itemX -> itemX.itemId == itemId }
        val newQuantity = itemToUpdate.qty.plus(1)
        Log.e(ContentValues.TAG, "product before incrementItemQuantity: ${itemToUpdate.qty}", )

        val itemWithNewQuantity = ItemX(
            itemToUpdate.itemId,
            itemToUpdate.notes,
            itemToUpdate.price,
            newQuantity,
            itemToUpdate.itemImage,
            itemToUpdate.itemName
        )
        val itemIdx = _shoppingCartItems.indexOfFirst { itemX -> itemX.itemId == itemId }
        _shoppingCartItems[itemIdx] = itemWithNewQuantity
        _currentItemActionIndex.postValue(Unit)
        Log.e(ContentValues.TAG, "product after incrementItemQuantity: ${itemWithNewQuantity.qty}", )
    }

    fun decrementItemQuantity(itemId: Int){
        val itemToUpdate = _shoppingCartItems.first { itemX -> itemX.itemId == itemId }
        val newQuantity = itemToUpdate.qty.toInt() - 1
        Log.e(ContentValues.TAG, "product before decrementItemQuantity: ${itemToUpdate.qty}", )

        if (newQuantity < 1){
            val itemIdx = _shoppingCartItems.indexOfFirst { itemX -> itemX.itemId == itemId }
            _shoppingCartItems.remove(itemToUpdate)
            _currentItemActionIndex.postValue(Unit)
            return
        }
        val itemWithNewQuantity = ItemX(
            itemToUpdate.itemId,
            itemToUpdate.notes,
            itemToUpdate.price,
            newQuantity,
            itemToUpdate.itemImage,
            itemToUpdate.itemName
        )
        val itemIdx = _shoppingCartItems.indexOfFirst { itemX -> itemX.itemId == itemId }
        _shoppingCartItems[itemIdx] = itemWithNewQuantity
        _currentItemActionIndex.postValue(Unit)
        Log.e(ContentValues.TAG, "product after decrementItemQuantity: ${itemWithNewQuantity.qty}", )
    }
}