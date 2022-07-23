package com.android.example.retrofit.punkapi.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.example.retrofit.R
import com.android.example.retrofit.punkapi.usecase.model.PunkapiRepo

class RepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val repoName: TextView

    init {
        repoName = view.findViewById(R.id.repo_name)
    }
}

class RepoAdapter(private val repoResults: List<PunkapiRepo>) :
    RecyclerView.Adapter<RepoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val repoView =
            LayoutInflater.from(parent.context).inflate(R.layout.repolistitem, parent, false)
        return RepoViewHolder(repoView)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.repoName.text = repoResults[position].name
    }

    override fun getItemCount(): Int {
        return repoResults.size
    }
}