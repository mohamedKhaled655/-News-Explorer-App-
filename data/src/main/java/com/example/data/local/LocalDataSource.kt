package com.example.data.local

interface LocalDataSource {

    fun saveLastQuery(query: String)
    fun getLastQuery(): String?

}