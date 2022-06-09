package com.android.example.retrofit.punkapi.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.example.retrofit.punkapi.network.PunkapiProvider
import com.android.example.retrofit.punkapi.usecase.model.PunkapiRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PunkapiSearchViewModel(private val punkapiProvider: PunkapiProvider) : ViewModel() {

    sealed class PunkapiSearchEvent {
        data class RetrieveUserRepos(val name: String) : PunkapiSearchEvent()
    }

    sealed class PunkapiSearchResult {
        data class Result(val repos: List<PunkapiRepository>) : PunkapiSearchResult()
        data class Error(val message: String) : PunkapiSearchResult()
    }

    private var _result = MutableLiveData<PunkapiSearchResult>()
    val result: LiveData<PunkapiSearchResult>
        get() = _result

//

    fun send(event: PunkapiSearchEvent) {
        when (event) {
            is PunkapiSearchEvent.RetrieveUserRepos -> retrieveRepos(event.name)
        }
    }

    private fun retrieveRepos(drink: String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                _result.value = PunkapiSearchResult.Result(punkapiProvider.getDrinkRepos(drink))
            } catch (e: Exception) {
                _result.value =
                    PunkapiSearchResult.Error("error retrieving repos: ${e.localizedMessage}")
            }
        }

    }
}