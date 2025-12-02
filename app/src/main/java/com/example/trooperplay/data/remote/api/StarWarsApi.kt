package com.example.trooperplay.data.remote.api

import com.example.trooperplay.data.remote.dto.*
import retrofit2.http.GET
import retrofit2.http.Url

interface StarWarsApi {

    @GET("people/")
    suspend fun getPeopleList(): PeopleResponse

    @GET
    suspend fun getPlanet(@Url url: String): PlanetDto

    @GET
    suspend fun getSpecies(@Url url: String): SpeciesDto

    @GET
    suspend fun getStarship(@Url url: String): StarshipDto
}

data class PeopleResponse(
    val results: List<PersonDto>
)
