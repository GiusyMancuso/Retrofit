package com.android.example.retrofit.punkapi.network.dto

import com.android.example.retrofit.punkapi.usecase.model.PunkapiRepo

data class RepoDto(
    val abv: Double,
    val attenuation_level: Double,
    val boil_volume: BoilVolumeDto,
    val brewers_tips: String,
    val contributed_by: String,
    val description: String,
    val ebc: Int,
    val first_brewed: String,
    val food_pairing: List<String>,
    val ibu: Double,
    val id: Int,
    val image_url: String,
    val ingredients: IngredientsDto,
    val method: MethodDto,
    val name: String,
    val ph: Double,
    val srm: Double,
    val tagline: String,
    val target_fg: Int,
    val target_og: Double,
    val volume: VolumeDto
)

fun RepoDto.toPunkapiRepository() = PunkapiRepo(name= this.name, id= this.id)