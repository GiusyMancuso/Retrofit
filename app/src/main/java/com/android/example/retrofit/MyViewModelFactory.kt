package com.android.example.retrofit

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.example.retrofit.punkapi.RetrofitDatabase
import com.android.example.retrofit.punkapi.usecase.PunkapiSearchViewModel
import com.android.example.retrofit.punkapi.network.PunkapiProvider

class MyViewModelFactory(
    private val punkapiProvider: PunkapiProvider,
    private val preferences: SharedPreferences,
    private val database: RetrofitDatabase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PunkapiSearchViewModel::class.java)) {
            return PunkapiSearchViewModel(punkapiProvider, preferences, database.repoDao()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}