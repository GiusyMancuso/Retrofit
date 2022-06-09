package com.android.example.retrofit.github.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.example.retrofit.R
import com.android.example.retrofit.Retrofit
import com.android.example.retrofit.github.usecase.GithubSearchViewModel
import com.android.example.retrofit.github.usecase.model.GithubRepository
import com.google.android.material.snackbar.Snackbar

class GithubSearchScreen : AppCompatActivity() {
    private lateinit var viewModel: GithubSearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel =
            (application as Retrofit).mainViewModelFactory.create(GithubSearchViewModel::class.java)

        observeRepos()
        viewModel.send(GithubSearchViewModel.GithubSearchEvent.RetrieveUserRepos("beers"))
    }

    private fun observeRepos() {

        viewModel.result.observe(this) {
            when (it) {
                is GithubSearchViewModel.GithubSearchResult.Result -> {
                    showRepos(it.repos)
                }

                is GithubSearchViewModel.GithubSearchResult.Error -> {
                    Snackbar.make(
                        findViewById(R.id.main_view), "Error retrieving repos: $it",
                        Snackbar.LENGTH_INDEFINITE
                    )
                        .setAction("Retry") {
                            viewModel.send(
                                GithubSearchViewModel.GithubSearchEvent.RetrieveUserRepos(
                                    "beers"
                                )
                            )
                        }.show()
                }
            }
        }
    }

    private fun showRepos(repoResults: List<GithubRepository>) {
        Log.d("GithubSearchScreen", "list of repos received, size: ${repoResults.size}")
        val list = findViewById<RecyclerView>(R.id.repo_list)
        list.visibility = View.VISIBLE
        val adapter = RepoAdapter(repoResults)
        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(this)
    }

}
