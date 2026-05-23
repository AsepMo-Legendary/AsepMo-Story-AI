package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

data class GalleryItem(val name: String, val icon: ImageVector)

@Composable
fun GalleryContent() {
    val items = listOf(
        GalleryItem("Apk", Icons.Default.Android),
        GalleryItem("Image", Icons.Default.Image),
        GalleryItem("Ebook", Icons.Default.Book),
        GalleryItem("Audio", Icons.Default.Audiotrack),
        GalleryItem("Video", Icons.Default.VideoLibrary),
        GalleryItem("Lainnya", Icons.Default.FilePresent)
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items) { item ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(item.icon, contentDescription = item.name, modifier = Modifier.size(48.dp))
                Spacer(modifier = Modifier.height(8.dp))
                Text(item.name)
            }
        }
    }
}
