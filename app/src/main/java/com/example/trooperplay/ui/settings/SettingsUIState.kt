package com.example.trooperplay.ui.settings

data class SettingsUIState(
    val playerName: String = "",
    val musicEnabled: Boolean = true,
    val sfxEnabled: Boolean = true,
    val selectedAvatar: String = "" // futuro: URL o ID Star Wars API
)
