package com.android.example.retrofit.punkapi

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.example.retrofit.punkapi.repository.dao.RepoDao
import com.android.example.retrofit.punkapi.repository.entity.RepoEntity

@Database(entities = [RepoEntity::class], version = 1)
abstract class RetrofitDatabase : RoomDatabase() {
    abstract fun repoDao(): RepoDao
}