package com.example.firstapplication2

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

public interface TmdbServ{
    @GET("trending/movie/week")
    suspend fun lastmovies(@Query("api_key") api_key: String): TmdbMovieResult

    /* On ajoute la langue afin d'avoir le synopsis en français : */
    @GET("movie/{id}")
    suspend fun movie(@Path("id") id: String, @Query("api_key") api_key: String, @Query("language") language: String, @Query("append_to_response") append_to_response: String): Film

    @GET("search/movie")
    suspend fun searchmovies(@Query("api_key") api_key: String, @Query("query") searchtext: String): TmdbMovieResult

    @GET("trending/tv/week")
    suspend fun lastseries(@Query("api_key") api_key: String): TmdbSerieResult

    /* On ajoute la langue afin d'avoir le synopsis en français : */
    @GET("tv/{id}")
    suspend fun serie(@Path("id") id: String, @Query("api_key") api_key: String, @Query("language") language: String, @Query("append_to_response") append_to_response: String): Serie

    @GET("search/tv")
    suspend fun searchseries(@Query("api_key") api_key: String, @Query("query") searchtext: String): TmdbSerieResult

    @GET("trending/person/week")
    suspend fun lastactors(@Query("api_key") api_key: String): TmdbActorResult

    /* On ajoute la langue afin d'avoir le synopsis en français : */
    @GET("person/{id}")
    suspend fun actor(@Path("id") id: String, @Query("api_key") api_key: String, @Query("language") language: String): Acteur

    @GET("search/person")
    suspend fun searchactors(@Query("api_key") api_key: String, @Query("query") searchtext: String): TmdbActorResult
}