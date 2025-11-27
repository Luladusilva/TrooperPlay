package com.example.trooperplay.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        Text("Comand-Center", style = MaterialTheme.typography.headlineMedium)

        // Nombre del jugador
        OutlinedTextField(
            value = state.playerName,
            onValueChange = { viewModel.updateName(it) },
            label = { Text("Spacial-name") },
            modifier = Modifier.fillMaxWidth()
        )

        // Música
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Void Resonance")
            Spacer(Modifier.weight(1f))
            Switch(
                checked = state.musicEnabled,
                onCheckedChange = { viewModel.setMusicEnabled(it) }
            )
        }

        // SFX
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Galactic effects")
            Spacer(Modifier.weight(1f))
            Switch(
                checked = state.sfxEnabled,
                onCheckedChange = { viewModel.setSfxEnabled(it) }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Turn back to space")
        }
    }
}
