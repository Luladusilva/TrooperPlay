package com.example.trooperplay.ui.customize

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trooperplay.data.remote.dto.PersonDto
import com.example.trooperplay.data.repository.TrooperRepository
import kotlinx.coroutines.launch

class CustomizeViewModel(
    private val repository: TrooperRepository
) : ViewModel() {

    var peopleList by mutableStateOf(emptyList<PersonDto>())
        private set

    var isLoading by mutableStateOf(false)

    init {
        loadCharacters()
    }

    fun loadCharacters() {
        viewModelScope.launch {
            isLoading = true
            peopleList = repository.getPeople()
            isLoading = false
        }
    }

}
