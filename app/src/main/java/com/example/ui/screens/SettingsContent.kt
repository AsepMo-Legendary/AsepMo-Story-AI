package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.SettingsManager
import kotlinx.coroutines.launch

@Composable
fun SettingsContent(settingsManager: SettingsManager) {
    val scope = rememberCoroutineScope()
    val isDarkTheme by settingsManager.isDarkTheme.collectAsState(initial = false)
    val flickrKey by settingsManager.flickrApiKey.collectAsState(initial = "")
    val bloggerKey by settingsManager.bloggerApiKey.collectAsState(initial = "")
    val bloggerId by settingsManager.bloggerId.collectAsState(initial = "")
    val ytKey by settingsManager.youtubeApiKey.collectAsState(initial = "")
    val ytPlaylistId by settingsManager.youtubePlaylistId.collectAsState(initial = "")

    var fKeyInput by remember { mutableStateOf(flickrKey) }
    var bKeyInput by remember { mutableStateOf(bloggerKey) }
    var bIdInput by remember { mutableStateOf(bloggerId) }
    var yKeyInput by remember { mutableStateOf(ytKey) }
    var yIdInput by remember { mutableStateOf(ytPlaylistId) }

    LaunchedEffect(flickrKey, bloggerKey, bloggerId, ytKey, ytPlaylistId) {
        fKeyInput = flickrKey
        bKeyInput = bloggerKey
        bIdInput = bloggerId
        yKeyInput = ytKey
        yIdInput = ytPlaylistId
    }

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Dark Theme", style = MaterialTheme.typography.titleMedium)
                Switch(
                    checked = isDarkTheme,
                    onCheckedChange = { scope.launch { settingsManager.saveDarkTheme(it) } }
                )
            }
            Divider(modifier = Modifier.padding(vertical = 16.dp))
        }

        item {
            Text("Flickr API Settings", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = fKeyInput,
                onValueChange = { fKeyInput = it },
                label = { Text("API Key") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = { scope.launch { settingsManager.saveFlickrApiKey(fKeyInput) } },
                modifier = Modifier.padding(top = 8.dp)
            ) { Text("Save Flickr Key") }
            Divider(modifier = Modifier.padding(vertical = 16.dp))
        }

        item {
            Text("Blogger API Settings", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = bIdInput,
                onValueChange = { bIdInput = it },
                label = { Text("Blog ID") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = bKeyInput,
                onValueChange = { bKeyInput = it },
                label = { Text("API Key") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = { scope.launch { settingsManager.saveBloggerApiKey(bKeyInput, bIdInput) } },
                modifier = Modifier.padding(top = 8.dp)
            ) { Text("Save Blogger Settings") }
            Divider(modifier = Modifier.padding(vertical = 16.dp))
        }

        item {
            Text("YouTube API Settings", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = yIdInput,
                onValueChange = { yIdInput = it },
                label = { Text("Playlist ID") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = yKeyInput,
                onValueChange = { yKeyInput = it },
                label = { Text("API Key") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = { scope.launch { settingsManager.saveYoutubeApiKey(yKeyInput, yIdInput) } },
                modifier = Modifier.padding(top = 8.dp)
            ) { Text("Save YouTube Settings") }
            Divider(modifier = Modifier.padding(vertical = 16.dp))
        }

        item {
            Button(
                onClick = { /* Handle logout */ },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            ) { Text("Log Out") }
        }
    }
}
