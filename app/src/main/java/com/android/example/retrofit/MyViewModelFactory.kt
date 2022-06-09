package com.android.example.retrofit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.example.retrofit.punkapi.usecase.PunkapiSearchViewModel
import com.android.example.retrofit.punkapi.network.PunkapiProvider

class MyViewModelFactory(private val punkapiProvider: PunkapiProvider) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PunkapiSearchViewModel::class.java)){
            return PunkapiSearchViewModel(punkapiProvider) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}