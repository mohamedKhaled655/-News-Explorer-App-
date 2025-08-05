package com.example.data.local

import android.content.SharedPreferences
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val sharedPreferences: SharedPreferences) : LocalDataSource {

    companion object {

        private const val KEY_LAST_QUERY = "last_query"
    }

    override fun saveLastQuery(query: String) {
        sharedPreferences.edit().putString(KEY_LAST_QUERY, query).apply()
    }

    override fun getLastQuery(): String? {
        return sharedPreferences.getString(KEY_LAST_QUERY, "android")
    }
}