package com.example.trooperplay.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trooperplay.data.datastore.SettingsDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsDataStore: SettingsDataStore
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUIState())
    val uiState: StateFlow<SettingsUIState> = _uiState

    init {
        viewModelScope.launch {
            settingsDataStore.settingsFlow.collectLatest { prefs ->
                _uiState.value = _uiState.value.copy(
                    playerName = prefs.playerName,
                    musicEnabled = prefs.musicEnabled,
                    sfxEnabled = prefs.sfxEnabled,
                    selectedAvatar = prefs.selectedAvatar
                )
            }
        }
    }

    fun updateName(name: String) = viewModelScope.launch {
        settingsDataStore.setPlayerName(name)
    }

    fun setMusicEnabled(enabled: Boolean) = viewModelScope.launch {
        settingsDataStore.setMusicEnabled(enabled)
    }

    fun setSfxEnabled(enabled: Boolean) = viewModelScope.launch {
        settingsDataStore.setSfxEnabled(enabled)
    }

    fun setAvatar(avatar: String) = viewModelScope.launch {
        settingsDataStore.setAvatar(avatar)
    }
}
