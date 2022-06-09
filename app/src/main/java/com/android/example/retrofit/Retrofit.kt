package com.android.example.retrofit

import android.app.Application
import com.android.example.retrofit.github.network.GithubProvider

class Retrofit : Application() {

    private val githubProvider = GithubProvider()
    val mainViewModelFactory = MyViewModelFactory(githubProvider)

    override fun onCreate() {
        super.onCreate()
    }
}