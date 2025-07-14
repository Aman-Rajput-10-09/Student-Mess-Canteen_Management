package com.example.userhostel.auth.data

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_NAME = "name"
        private const val KEY_HOSTEL = "hostelNo"
        private const val KEY_ROLL = "rollNumber"
        private const val KEY_TOKEN = "token"
    }

    fun saveUserSession(name: String, hostelNo: String, rollNumber: String, token: String) {
        prefs.edit().apply {
            putBoolean(KEY_IS_LOGGED_IN, true)
            putString(KEY_NAME, name)
            putString(KEY_HOSTEL, hostelNo)
            putString(KEY_ROLL, rollNumber)
            putString(KEY_TOKEN, token)
            apply()
        }
    }

    fun isLoggedIn(): Boolean = prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    fun getName(): String? = prefs.getString(KEY_NAME, null)
    fun getHostelNo(): String? = prefs.getString(KEY_HOSTEL, null)
    fun getRollNumber(): String? = prefs.getString(KEY_ROLL, null)
    fun getToken(): String? = prefs.getString(KEY_TOKEN, null)

    fun logout() {
        prefs.edit().clear().apply()
    }
}
