package com.android.saidalytech.network

import com.android.saidalytech.model.*
import retrofit2.http.*

interface WebServices {

    @POST("Authorization/LogIn")
    suspend fun login(
        @Body modelLogin: ModelLogin,
    ): ModelLoginRegisterResponses

    @POST("Authorization/RegisterUser")
    suspend fun register(
        @Body modelRegister: ModelRegister,
    ): ModelLoginRegisterResponses

    @GET("Item/GetAllItems")
    suspend fun getItems(
        @Query("data") data: String,
    ): ModelData

    @GET("Category/GetAllCategoreis")
    suspend fun getAllCategories(
        @Query("data") data: String,
    ): ModelAllCategories

    @GET("Item/GetItemDetails/{id}:int")
    suspend fun getItemDetail(
        @Path("id") id: Int,
    ): ModelItemDetail

    @GET("Order/GetUserOrders/{userId}")
    suspend fun getPreOrder(
        @Path("userId") userId: String,
    ): ModelPreOrder

    @POST("Order/AddOrder")
    suspend fun sendOrder(@Body model: ModelAddOrder)

//    @POST("Authorization/RegisterUser")
//    suspend fun register(
//        @Body modelRegister: ModelRegister,
//    ): ModelLoginRegisterResponses

    @POST("Authorization/EditUser")
    suspend fun editInfo(
        @Body modelRegister: ModelRegister
    )

    @POST("Authorization/ResetMyPassword")
    suspend fun resetPassword(
      @Body modelForgetPassword: ModelForgetPassword
    )

    @GET("Order/GetRosheta/{id}")
    suspend fun addRosheta(
        @Path("id") id: String,
    ): ModelRosheta

}