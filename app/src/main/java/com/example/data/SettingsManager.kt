package com.example.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsManager(private val context: Context) {
    companion object {
        val THEME_DARK = booleanPreferencesKey("theme_dark")
        val FLICKR_API_KEY = stringPreferencesKey("flickr_api_key")
        val BLOGGER_API_KEY = stringPreferencesKey("blogger_api_key")
        val BLOGGER_ID = stringPreferencesKey("blogger_id")
        val YOUTUBE_API_KEY = stringPreferencesKey("youtube_api_key")
        val YOUTUBE_PLAYLIST_ID = stringPreferencesKey("youtube_playlist_id")
    }

    val isDarkTheme: Flow<Boolean> = context.dataStore.data.map { it[THEME_DARK] ?: false }
    val flickrApiKey: Flow<String> = context.dataStore.data.map { it[FLICKR_API_KEY] ?: "" }
    val bloggerApiKey: Flow<String> = context.dataStore.data.map { it[BLOGGER_API_KEY] ?: "" }
    val bloggerId: Flow<String> = context.dataStore.data.map { it[BLOGGER_ID] ?: "" }
    val youtubeApiKey: Flow<String> = context.dataStore.data.map { it[YOUTUBE_API_KEY] ?: "" }
    val youtubePlaylistId: Flow<String> = context.dataStore.data.map { it[YOUTUBE_PLAYLIST_ID] ?: "" }

    suspend fun saveDarkTheme(isDark: Boolean) {
        context.dataStore.edit { it[THEME_DARK] = isDark }
    }

    suspend fun saveFlickrApiKey(key: String) {
        context.dataStore.edit { it[FLICKR_API_KEY] = key }
    }

    suspend fun saveBloggerApiKey(key: String, blogId: String) {
        context.dataStore.edit { 
            it[BLOGGER_API_KEY] = key
            it[BLOGGER_ID] = blogId
        }
    }

    suspend fun saveYoutubeApiKey(key: String, playlistId: String) {
        context.dataStore.edit { 
            it[YOUTUBE_API_KEY] = key
            it[YOUTUBE_PLAYLIST_ID] = playlistId
        }
    }
}
