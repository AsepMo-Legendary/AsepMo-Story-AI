package com.example.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {

    // Flickr API
    @GET("https://api.flickr.com/services/rest/")
    suspend fun getRecentPhotos(
        @Query("method") method: String = "flickr.photos.getRecent",
        @Query("api_key") apiKey: String,
        @Query("format") format: String = "json",
        @Query("nojsoncallback") noJsonCallback: Int = 1,
        @Query("per_page") perPage: Int = 10
    ): FlickrResponse

    // Blogger API
    @GET("https://www.googleapis.com/blogger/v3/blogs/{blogId}/posts")
    suspend fun getBlogPosts(
        @Path("blogId") blogId: String,
        @Query("key") apiKey: String,
        @Query("maxResults") maxResults: Int = 10
    ): BloggerResponse

    // YouTube API
    @GET("https://www.googleapis.com/youtube/v3/playlistItems")
    suspend fun getPlaylistItems(
        @Query("part") part: String = "snippet",
        @Query("playlistId") playlistId: String,
        @Query("key") apiKey: String,
        @Query("maxResults") maxResults: Int = 10
    ): YoutubeResponse
}
