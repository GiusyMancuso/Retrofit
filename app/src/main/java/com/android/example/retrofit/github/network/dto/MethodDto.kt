package com.android.example.retrofit.github.network.dto

data class MethodDto(
    val fermentation: FermentationDto,
    val mash_temp: List<MashTempDto>,
    val twist: String
)