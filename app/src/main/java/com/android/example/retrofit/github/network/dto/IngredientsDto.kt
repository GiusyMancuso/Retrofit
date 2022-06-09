package com.android.example.retrofit.github.network.dto

data class IngredientsDto(
    val hops: List<HopDto>,
    val malt: List<MaltDto>,
    val yeast: String
)