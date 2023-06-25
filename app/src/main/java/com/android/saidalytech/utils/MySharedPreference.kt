package com.android.saidalytech.uitls

import android.content.Context
import android.content.SharedPreferences
import com.android.saidalytech.model.ModelOrder

object MySharedPreference {

    private var mAppContext: Context? = null
    private const val SHARED_PREFERENCES_NAME = "saidaly tech"

    private const val USER_PHONE = "mobile"
    private const val USER_PASS = "pass"
    private const val USER_NAME = "username"
    private const val USER_ADDRESS = "address"
    private const val USER_TOKEN = "token"
    private const val USER_EMAIL = "user email"
    private const val USER_IMAGE = "user image"
    private const val USER_AGE = "user age"
    private const val USER_GENDER = "user gender"
    private const val USER_ID = "user id"
    private const val CUSTOMER_ID= "customer id"

    private const val ORDER = "order"


    private fun mySharedPreference() {}

    fun init(appContext: Context?) {
        mAppContext = appContext
    }

    private fun getSharedPreferences(): SharedPreferences {
        return mAppContext!!.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    fun setUserId(id: String) {

        val editor = getSharedPreferences().edit()
        editor.putString(USER_ID, id).apply()
    }

    fun getUserId(): String? {
        return getSharedPreferences().getString(USER_ID, "")
    }

    fun setCustomerId(id: String) {

        val editor = getSharedPreferences().edit()
        editor.putString(CUSTOMER_ID, id).apply()
    }

    fun getCustomerId(): String? {
        return getSharedPreferences().getString(CUSTOMER_ID, "")
    }

    fun setUserAge(age: String) {
        val editor = getSharedPreferences().edit()
        editor.putString(USER_AGE, age).apply()
    }

    fun getUserAge(): String? {
        return getSharedPreferences().getString(USER_AGE, "")
    }

    fun setUserEmail(email: String) {
        val editor = getSharedPreferences().edit()
        editor.putString(USER_EMAIL, email).apply()
    }

    fun setUserPhone(container: String) {

        val editor = getSharedPreferences().edit()
        editor.putString(USER_PHONE, container).apply()
    }

    fun getUserPhone(): String {
        return getSharedPreferences().getString(USER_PHONE, "")!!
    }

    fun setUserPass(container: String) {
        val editor = getSharedPreferences().edit()
        editor.putString(USER_PASS, container).apply()
    }

    fun getUserPass(): String {
        return getSharedPreferences().getString(USER_PASS, "")!!
    }

    fun setUserImage(container: String) {

        val editor = getSharedPreferences().edit()
        editor.putString(USER_IMAGE, container).apply()
    }

    fun getUserImage(): String {
        return getSharedPreferences().getString(USER_IMAGE, "")!!
    }


    fun getUserEmail(): String {
        return getSharedPreferences().getString(USER_EMAIL, "")!!
    }

    fun setUserName(name: String) {
        val editor = getSharedPreferences().edit()
        editor.putString(USER_NAME, name).apply()
    }

    fun getUserName(): String {
        return getSharedPreferences().getString(USER_NAME, "")!!
    }

    fun setUserAddress(email: String) {
        val editor = getSharedPreferences().edit()
        editor?.putString(USER_ADDRESS, email)?.apply()
    }

    fun getUserAddress(): String? {
        return getSharedPreferences().getString(USER_ADDRESS, "")!!
    }

    fun setUserGender(container: String) {
        val editor = getSharedPreferences().edit()
        editor?.putString(USER_GENDER, container)?.apply()
    }

    fun getUserGender(): String? {
        return getSharedPreferences().getString(USER_GENDER, "")!!
    }

    fun setUserTOKEN(id: String) {

        val editor = getSharedPreferences().edit()
        editor.putString(USER_TOKEN, id).apply()
    }

    fun getUserToken(): String? {
        return getSharedPreferences().getString(USER_TOKEN, "")!!
    }

}