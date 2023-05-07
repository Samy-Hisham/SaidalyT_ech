package com.android.saidalytech.network

import com.android.saidalytech.model.*
import retrofit2.http.*

interface WebServices {

    @POST("Authorization/LogIn")
    suspend fun login(
//        @Field("email") email: String,
//        @Field("password") password: String
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
    suspend fun sendOrder(model: ModelAddOrder): ModelAddOrder

//    @GET("Item/GetItemsWithCategoryId/{id}")
//    suspend fun getCategoryById(
//        @Path("id") id: Int,
//    ): ModelData

}