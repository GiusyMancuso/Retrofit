package com.android.example.retrofit.punkapi.network.dto

data class MethodDto(
    val fermentation: FermentationDto,
    val mash_temp: List<MashTempDto>,
    val twist: String
)