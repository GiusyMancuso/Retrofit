package com.android.example.retrofit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel : ViewModel() {
    private val retrofit = Retrofit.Builder().baseUrl("https://api.punkapi.com/v2/").addConverterFactory(
        GsonConverterFactory.create()).build()
    private val gitHubService = retrofit.create(GitHubService::class.java)

    private var _repos= MutableLiveData<RepoResult>()
    val repos: LiveData<RepoResult>
        get() = _repos

    private var _error= MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    fun retrieveRepos(user : String){
        CoroutineScope(Dispatchers.Main).launch{
            try{
            _repos.value = gitHubService.listRepos(user)
        } catch (e: Exception) {
            _error.value = e.localizedMessage
            }
        }

    }
}