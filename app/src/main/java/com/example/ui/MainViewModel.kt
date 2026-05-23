package com.example.ui

import android.os.Environment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.SettingsManager
import com.example.network.ApiServices
import com.example.network.BlogPost
import com.example.network.Photo
import com.example.network.PlaylistItem
import com.example.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.File

class MainViewModel(
    private val settingsManager: SettingsManager
) : ViewModel() {

    private val api: ApiServices = RetrofitClient.apiServices

    private val _photos = MutableStateFlow<List<Photo>>(emptyList())
    val photos: StateFlow<List<Photo>> = _photos.asStateFlow()

    private val _blogPosts = MutableStateFlow<List<BlogPost>>(emptyList())
    val blogPosts: StateFlow<List<BlogPost>> = _blogPosts.asStateFlow()

    private val _youtubeItems = MutableStateFlow<List<PlaylistItem>>(emptyList())
    val youtubeItems: StateFlow<List<PlaylistItem>> = _youtubeItems.asStateFlow()

    private val _files = MutableStateFlow<List<File>>(emptyList())
    val files: StateFlow<List<File>> = _files.asStateFlow()

    fun loadApiData() {
        viewModelScope.launch {
            try {
                val flickrKey = settingsManager.flickrApiKey.first()
                if (flickrKey.isNotEmpty()) {
                    val response = api.getRecentPhotos(apiKey = flickrKey)
                    _photos.value = response.photos?.photo ?: emptyList()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            try {
                val bloggerKey = settingsManager.bloggerApiKey.first()
                val bloggerId = settingsManager.bloggerId.first()
                if (bloggerKey.isNotEmpty() && bloggerId.isNotEmpty()) {
                    val response = api.getBlogPosts(blogId = bloggerId, apiKey = bloggerKey)
                    _blogPosts.value = response.items ?: emptyList()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            try {
                val ytKey = settingsManager.youtubeApiKey.first()
                val ytPlaylistId = settingsManager.youtubePlaylistId.first()
                if (ytKey.isNotEmpty() && ytPlaylistId.isNotEmpty()) {
                    val response = api.getPlaylistItems(playlistId = ytPlaylistId, apiKey = ytKey)
                    _youtubeItems.value = response.items ?: emptyList()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun loadLocalFiles() {
        val extPath = Environment.getExternalStorageDirectory()
        val folder = File(extPath, "FolderMe")
        if (folder.exists() && folder.isDirectory) {
            _files.value = folder.listFiles()?.toList() ?: emptyList()
        }
    }

    class Factory(private val settingsManager: SettingsManager) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(settingsManager) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
