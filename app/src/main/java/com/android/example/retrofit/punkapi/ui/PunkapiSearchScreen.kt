package com.android.example.retrofit.punkapi.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.example.retrofit.R
import com.android.example.retrofit.Retrofit
import com.android.example.retrofit.punkapi.usecase.PunkapiSearchViewModel
import com.android.example.retrofit.punkapi.usecase.model.PunkapiRepository
import com.google.android.material.snackbar.Snackbar

class PunkapiSearchScreen : AppCompatActivity() {
    private lateinit var viewModel: PunkapiSearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel =
            (application as Retrofit).mainViewModelFactory.create(PunkapiSearchViewModel::class.java)

        observeRepos()
        viewModel.send(PunkapiSearchViewModel.PunkapiSearchEvent.RetrieveUserRepos("beers"))
    }

    private fun observeRepos() {

        viewModel.result.observe(this) {
            when (it) {
                is PunkapiSearchViewModel.PunkapiSearchResult.Result -> {
                    showRepos(it.repos)
                }

                is PunkapiSearchViewModel.PunkapiSearchResult.Error -> {
                    Snackbar.make(
                        findViewById(R.id.main_view), "Error retrieving repos: $it",
                        Snackbar.LENGTH_INDEFINITE
                    )
                        .setAction("Retry") {
                            viewModel.send(
                                PunkapiSearchViewModel.PunkapiSearchEvent.RetrieveUserRepos(
                                    "beers"
                                )
                            )
                        }.show()
                }
            }
        }
    }

    private fun showRepos(repoResults: List<PunkapiRepository>) {
        Log.d("PunkapiSearchScreen", "list of repos received, size: ${repoResults.size}")
        val list = findViewById<RecyclerView>(R.id.repo_list)
        list.visibility = View.VISIBLE
        val adapter = RepoAdapter(repoResults)
        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(this)
    }

}
