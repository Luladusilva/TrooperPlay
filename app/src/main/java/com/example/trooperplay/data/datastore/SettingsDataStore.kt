package com.example.trooperplay.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.settingsDataStore by preferencesDataStore(name = "settings")

data class SettingsPreferences(
    val playerName: String = "",
    val musicEnabled: Boolean = true,
    val sfxEnabled: Boolean = true,
    val selectedAvatar: String = ""
)

class SettingsDataStore(private val context: Context) {

    private val KEY_PLAYER_NAME = stringPreferencesKey("player_name")
    private val KEY_MUSIC_ENABLED = booleanPreferencesKey("music_enabled")
    private val KEY_SFX_ENABLED = booleanPreferencesKey("sfx_enabled")
    private val KEY_SELECTED_AVATAR = stringPreferencesKey("selected_avatar")

    val settingsFlow: Flow<SettingsPreferences> =
        context.settingsDataStore.data.map { prefs ->
            SettingsPreferences(
                playerName = prefs[KEY_PLAYER_NAME] ?: "",
                musicEnabled = prefs[KEY_MUSIC_ENABLED] ?: true,
                sfxEnabled = prefs[KEY_SFX_ENABLED] ?: true,
                selectedAvatar = prefs[KEY_SELECTED_AVATAR] ?: ""
            )
        }

    suspend fun setPlayerName(name: String) {
        context.settingsDataStore.edit { it[KEY_PLAYER_NAME] = name }
    }

    suspend fun setMusicEnabled(enabled: Boolean) {
        context.settingsDataStore.edit { it[KEY_MUSIC_ENABLED] = enabled }
    }

    suspend fun setSfxEnabled(enabled: Boolean) {
        context.settingsDataStore.edit { it[KEY_SFX_ENABLED] = enabled }
    }

    suspend fun setAvatar(avatar: String) {
        context.settingsDataStore.edit { it[KEY_SELECTED_AVATAR] = avatar }
    }
}
