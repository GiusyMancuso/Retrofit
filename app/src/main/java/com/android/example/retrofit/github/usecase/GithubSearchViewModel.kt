package com.android.example.retrofit.github.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.example.retrofit.github.network.GithubProvider
import com.android.example.retrofit.github.usecase.model.GithubRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GithubSearchViewModel(private val githubProvider: GithubProvider) : ViewModel() {

    sealed class GithubSearchEvent {
        data class RetrieveUserRepos(val name: String) : GithubSearchEvent()
    }

    sealed class GithubSearchResult {
        data class Result(val repos: List<GithubRepository>) : GithubSearchResult()
        data class Error(val message: String) : GithubSearchResult()
    }

    private var _result = MutableLiveData<GithubSearchResult>()
    val result: LiveData<GithubSearchResult>
        get() = _result

//

    fun send(event: GithubSearchEvent) {
        when (event) {
            is GithubSearchEvent.RetrieveUserRepos -> retrieveRepos(event.name)
        }
    }

    private fun retrieveRepos(user: String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                _result.value = GithubSearchResult.Result(githubProvider.getUserRepos(user))
            } catch (e: Exception) {
                _result.value =
                    GithubSearchResult.Error("error retrieving repos: ${e.localizedMessage}")
            }
        }

    }
}