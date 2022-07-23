package com.android.example.retrofit

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.android.example.retrofit.punkapi.RetrofitDatabase
import com.android.example.retrofit.punkapi.network.PunkapiProvider

class Retrofit : Application() {

    private val punkapiProvider = PunkapiProvider()
    lateinit var preferences : SharedPreferences
    lateinit var mainViewModelFactory: MyViewModelFactory
    lateinit var database: RetrofitDatabase

    override fun onCreate() {
        super.onCreate()
        preferences = getSharedPreferences("app", Context.MODE_PRIVATE)

        database = Room.databaseBuilder(
            applicationContext,
            RetrofitDatabase::class.java, "app-database"
        ).build()

        mainViewModelFactory = MyViewModelFactory(punkapiProvider, preferences, database)
    }
}