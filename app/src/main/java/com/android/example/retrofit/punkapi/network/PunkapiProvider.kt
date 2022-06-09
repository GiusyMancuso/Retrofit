package com.android.example.retrofit.punkapi.network

import com.android.example.retrofit.punkapi.network.dto.toPunkapiRepository
import com.android.example.retrofit.punkapi.usecase.model.PunkapiRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val PAGE_SIZE = 20

class PunkapiProvider {
    private val retrofit =
        Retrofit.Builder().baseUrl("https://api.punkapi.com/v2/").addConverterFactory(
            GsonConverterFactory.create()
        ).build()
    private val punkapiService = retrofit.create(PunkapiService::class.java)

    suspend fun getDrinkRepos(drink: String): List<PunkapiRepository> =
        punkapiService.listRepos(drink, PAGE_SIZE).map { it.toPunkapiRepository() }
}