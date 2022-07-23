package com.android.example.retrofit.punkapi.usecase

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.example.retrofit.punkapi.network.PunkapiProvider
import com.android.example.retrofit.punkapi.repository.dao.RepoDao
import com.android.example.retrofit.punkapi.repository.entity.toEntity
import com.android.example.retrofit.punkapi.repository.entity.toModel
import com.android.example.retrofit.punkapi.usecase.model.PunkapiRepo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

sealed class PunkapiSearchEvent {
    data class RetrieveUserRepos(val name: String) : PunkapiSearchEvent()
}

sealed class PunkapiSearchViewModelState {
    data class PunkapiSearchResult(val repos: List<PunkapiRepo>) :
        PunkapiSearchViewModelState()

    data class PunkapiSearchError(val message: String) : PunkapiSearchViewModelState()
    object FirstTimeUser : PunkapiSearchViewModelState()
}

const val KEY_FIRST_TIME_USER = "first_time_user"

class PunkapiSearchViewModel(
    private val punkapiProvider: PunkapiProvider,
    preferences: SharedPreferences,
    private val repoDao: RepoDao
) : ViewModel() {

    val result = MutableSharedFlow<PunkapiSearchViewModelState>()

    init {
        checkFirstTimeUser(preferences)
        setupDatabaseObserver()
    }

    private fun checkFirstTimeUser(preferences: SharedPreferences) {
        val firstTimeUser = preferences.getBoolean(KEY_FIRST_TIME_USER, true)

        if (firstTimeUser) {
            preferences.edit().putBoolean(KEY_FIRST_TIME_USER, false).apply()

            viewModelScope.launch {
                result.emit(PunkapiSearchViewModelState.FirstTimeUser)
            }

        }
    }

    fun send(event: PunkapiSearchEvent) {
        when (event) {
            is PunkapiSearchEvent.RetrieveUserRepos -> retrieveRepos(event.name)
        }
    }

    private fun setupDatabaseObserver() {
        viewModelScope.launch{
            repoDao.getAll().collect {
                result.emit(PunkapiSearchViewModelState.PunkapiSearchResult(
                    it.map {
                        entity -> entity.toModel()
                    }
                ))
            }
        }

    }

    private fun retrieveRepos(drink: String) {
        viewModelScope.launch {
            try {
                val dataFromNetwork = punkapiProvider.getDrinkRepos(
                    drink
                )
                repoDao.insertAll(*dataFromNetwork.map { repo -> repo.toEntity()}.toTypedArray())
            } catch (e: Exception) {
                result.emit(
                    PunkapiSearchViewModelState.PunkapiSearchError("error retrieving repos: ${e.localizedMessage}")
                )
            }
        }

    }
}