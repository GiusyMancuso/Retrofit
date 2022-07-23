package com.android.example.retrofit.punkapi.repository.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.example.retrofit.punkapi.usecase.model.PunkapiRepo

@Entity(tableName = "repo_entity")
data class RepoEntity (
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name")val name: String)

fun RepoEntity.toModel() : PunkapiRepo {
    return PunkapiRepo(id= this.id, name=this.name)
}

fun PunkapiRepo.toEntity(): RepoEntity{
    return RepoEntity(id= this.id, name=this.name)
}
