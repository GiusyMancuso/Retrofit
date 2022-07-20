package com.android.example.retrofit

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.android.example.retrofit.punkapi.network.PunkapiProvider

class Retrofit : Application() {

    private val punkapiProvider = PunkapiProvider()
    lateinit var preferences : SharedPreferences
    lateinit var mainViewModelFactory: MyViewModelFactory

    override fun onCreate() {
        super.onCreate()
        preferences = getSharedPreferences("app", Context.MODE_PRIVATE)
        mainViewModelFactory = MyViewModelFactory(punkapiProvider, preferences)
    }
}