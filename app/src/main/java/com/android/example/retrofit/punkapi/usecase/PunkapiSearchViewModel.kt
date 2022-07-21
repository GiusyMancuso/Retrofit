package com.android.example.retrofit.punkapi.usecase

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.example.retrofit.punkapi.network.PunkapiProvider
import com.android.example.retrofit.punkapi.usecase.model.PunkapiRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

sealed class PunkapiSearchEvent {
    data class RetrieveUserRepos(val name: String) : PunkapiSearchEvent()
}

sealed class PunkapiSearchViewModelEvent {
    data class PunkapiSearchResult(val repos: List<PunkapiRepository>) : PunkapiSearchViewModelEvent()
    data class PunkapiSearchError(val message: String) : PunkapiSearchViewModelEvent()
    object FirstTimeUser: PunkapiSearchViewModelEvent()
}
const val KEY_FIRST_TIME_USER= "first_time_user"

class PunkapiSearchViewModel(private val punkapiProvider: PunkapiProvider, preferences: SharedPreferences) : ViewModel() {

    val result = MutableSharedFlow<PunkapiSearchViewModelEvent>()

    init {
        checkFirstTimeUser(preferences)
    }

    private fun checkFirstTimeUser(preferences: SharedPreferences){
        val firstTimeUser = preferences.getBoolean(KEY_FIRST_TIME_USER, true)

        if (firstTimeUser){
            preferences.edit().putBoolean(KEY_FIRST_TIME_USER, false).apply()

            viewModelScope.launch {
                result.emit(PunkapiSearchViewModelEvent.FirstTimeUser)
            }

        }
    }

    fun send(event: PunkapiSearchEvent) {
        when (event) {
            is PunkapiSearchEvent.RetrieveUserRepos -> retrieveRepos(event.name)
        }
    }

    private fun retrieveRepos(drink: String) {
        viewModelScope.launch {
            try {
                result.emit(PunkapiSearchViewModelEvent.PunkapiSearchResult(punkapiProvider.getDrinkRepos(drink)))
            } catch (e: Exception) {
                result.emit(
                    PunkapiSearchViewModelEvent.PunkapiSearchError("error retrieving repos: ${e.localizedMessage}"))
            }
        }

    }
}