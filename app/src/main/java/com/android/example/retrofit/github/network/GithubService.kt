package com.android.example.retrofit.github.network

import com.android.example.retrofit.github.network.dto.RepoResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubService {
    @GET("{user}")
    suspend fun listRepos(@Path("user") user: String, @Query("per_page") pageSize: Int): RepoResult
}