package com.android.example.retrofit.github.network

import com.android.example.retrofit.github.network.dto.toGithubRepository
import com.android.example.retrofit.github.usecase.model.GithubRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val PAGE_SIZE = 20

class GithubProvider {
    private val retrofit =
        Retrofit.Builder().baseUrl("https://api.punkapi.com/v2/").addConverterFactory(
            GsonConverterFactory.create()
        ).build()
    private val gitHubService = retrofit.create(GitHubService::class.java)

    suspend fun getUserRepos(user: String): List<GithubRepository> =
        gitHubService.listRepos(user, PAGE_SIZE).map { it.toGithubRepository() }
}