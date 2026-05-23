package com.example.network

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

// Flickr Models
@JsonClass(generateAdapter = true)
data class FlickrResponse(val photos: PhotosPage?)

@JsonClass(generateAdapter = true)
data class PhotosPage(val photo: List<Photo>?)

@JsonClass(generateAdapter = true)
data class Photo(
    val id: String,
    val secret: String,
    val server: String,
    val farm: Int,
    val title: String?
) {
    fun getUrl() = "https://farm${farm}.staticflickr.com/${server}/${id}_${secret}.jpg"
}

// Blogger Models
@JsonClass(generateAdapter = true)
data class BloggerResponse(val items: List<BlogPost>?)

@JsonClass(generateAdapter = true)
data class BlogPost(
    val id: String,
    val title: String?,
    val content: String?,
    val url: String?
)

// YouTube Models
@JsonClass(generateAdapter = true)
data class YoutubeResponse(val items: List<PlaylistItem>?)

@JsonClass(generateAdapter = true)
data class PlaylistItem(val snippet: Snippet?)

@JsonClass(generateAdapter = true)
data class Snippet(
    val title: String?,
    val description: String?,
    val thumbnails: Thumbnails?,
    val resourceId: ResourceId?
)

@JsonClass(generateAdapter = true)
data class Thumbnails(val default: Thumbnail?, val high: Thumbnail?)

@JsonClass(generateAdapter = true)
data class Thumbnail(val url: String?)

@JsonClass(generateAdapter = true)
data class ResourceId(val videoId: String?)
