package com.example.jozmusik.Data.Preferences

import android.content.Context

class PreferenceManager(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("JozMusik", Context.MODE_PRIVATE)

    fun saveUser(username: String, password: String) {
        sharedPreferences.edit()
            .putString("USERNAME", username)
            .putString("PASSWORD", password)
            .putBoolean("IS_LOGGED_IN", false)
            .apply()
    }

    fun getUsername(): String? {
        return sharedPreferences.getString("USERNAME", null)
    }

    fun getPassword(): String? {
        return sharedPreferences.getString("PASSWORD", null)
    }

    fun setLoggedIn(loggedIn: Boolean) {
        sharedPreferences.edit()
            .putBoolean("IS_LOGGED_IN", loggedIn)
            .apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("IS_LOGGED_IN", false)
    }

    fun clearUser() {
        sharedPreferences.edit().clear().apply()
    }
}