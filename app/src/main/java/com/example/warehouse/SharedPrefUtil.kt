package com.example.warehouse

import android.content.Context
import android.content.SharedPreferences

object SharedPrefUtil {

    private const val PREF_CURRENT_USER_ID = "pref_current_user_id"

    private const val PREF_SAVED_USER_ID = "pref_current_user_id"

    private const val DEFAULT_USER_ID = "none"

    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(
            "${BuildConfig.APPLICATION_ID}.shared_preferences", Context.MODE_PRIVATE)
    }

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }

    fun getCurrentUserId(): String = getString(PREF_CURRENT_USER_ID, DEFAULT_USER_ID)
    fun getSavedUserId(): String = getString(PREF_SAVED_USER_ID, DEFAULT_USER_ID)

    fun setCurrentUserId(userId: String) = setString(PREF_CURRENT_USER_ID, userId)
    fun setSavedUserId(userId: String) = setString(PREF_SAVED_USER_ID, userId)

    private fun setString(name: String, value: String) {
        sharedPreferences.edit().putString(name, value).apply()
    }

    private fun getString(name: String, value: String = ""): String {
        return sharedPreferences.getString(name, value)?: return "none"
    }
}