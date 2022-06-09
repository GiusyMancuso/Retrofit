package com.android.example.retrofit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.example.retrofit.github.usecase.GithubSearchViewModel
import com.android.example.retrofit.github.network.GithubProvider

class MyViewModelFactory(private val githubProvider: GithubProvider) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GithubSearchViewModel::class.java)){
            return GithubSearchViewModel(githubProvider) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}