package com.android.example.retrofit

import android.app.Application
import com.android.example.retrofit.punkapi.network.PunkapiProvider

class Retrofit : Application() {

    private val punkapiProvider = PunkapiProvider()
    val mainViewModelFactory = MyViewModelFactory(punkapiProvider)

    override fun onCreate() {
        super.onCreate()
    }
}