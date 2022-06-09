package com.android.example.retrofit.punkapi.network

import com.android.example.retrofit.punkapi.network.dto.RepoResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PunkapiService {
    @GET("{drink}")
    suspend fun listRepos(@Path("drink") user: String, @Query("per_page") pageSize: Int): RepoResult
}