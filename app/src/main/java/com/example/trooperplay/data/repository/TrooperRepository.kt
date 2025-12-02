package com.example.trooperplay.data.repository

import com.example.trooperplay.data.remote.DEFAULT_STARSHIP_URL
import com.example.trooperplay.data.remote.RetrofitClient
import com.example.trooperplay.data.remote.dto.PersonDto
import com.example.trooperplay.domain.model.StarWarsCharacter

class TrooperRepository {

    private val api = RetrofitClient.api

    // ✔ Esta función devuelve la lista cruda del API
    suspend fun getPeople(): List<PersonDto> {
        return api.getPeopleList().results
    }

    // ✔ Si quieres obtener un personaje completo con planeta/especie/nave
    suspend fun getCharacter(index: Int): StarWarsCharacter {
        val peopleList = api.getPeopleList().results
        val person = peopleList[index]

        // Homeworld
        val planetDto = api.getPlanet(person.homeworld)

        // Species
        val speciesName = if (person.species.isNotEmpty()) {
            api.getSpecies(person.species.first()).name
        } else {
            "Unknown"
        }

        // Default starship
        val starshipDto = api.getStarship(DEFAULT_STARSHIP_URL)

        return StarWarsCharacter(
            name = person.name,
            species = speciesName,
            planet = planetDto.name,
            starshipName = starshipDto.name,
            hyperdriveRating = starshipDto.hyperdriveRating
        )
    }
}
