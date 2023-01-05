package com.example.firstapplication2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainViewModel : ViewModel() {

    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build();

    val service = retrofit.create(TmdbServ::class.java)

    /* Les listes :*/
    val movies = MutableStateFlow<List<Film>>(listOf())
    val series = MutableStateFlow<List<Serie>>(listOf())
    val actors = MutableStateFlow<List<Acteur>>(listOf())

    /* Les détails :*/
    val movie = MutableStateFlow<Film?>(null)
    val serie = MutableStateFlow<Serie?>(null)
    val actor = MutableStateFlow<Acteur?>(null)

    /* Paramètres :*/
    val langue = "fr"
    val key = "a70fdcaf6041b798cfa437ee72337ed1"
    val append = "credits"

    init{
        getFilmsInitiaux()
        getSeriesInitiales()
        getActeursWeek()
    }

    /*Listes :*/
    fun getFilmsInitiaux() {
        viewModelScope.launch {
            movies.value = service.lastmovies(key).results
        }
    }

    fun getSeriesInitiales() {
        viewModelScope.launch {
            series.value = service.lastseries(key).results
        }
    }

    fun getActeursWeek() {
        viewModelScope.launch {
            actors.value = service.lastactors(key).results
        }
    }

    /*Getters :*/
    fun getFilm(id : String) {
        viewModelScope.launch {
            movie.value = service.movie(id, key, langue, append)
        }
    }

    fun getSerie(id : String) {
        viewModelScope.launch {
            serie.value = service.serie(id, key, langue, append)
        }
    }

    fun getActor(id : String) {
        viewModelScope.launch {
            actor.value = service.actor(id, key, langue)
        }
    }

    /*Recherches :*/
    fun searchFilms(searchText : String) {
        viewModelScope.launch {
            movies.value = service.searchmovies(key, searchText).results
        }
    }

    fun searchSeries(searchText : String) {
        viewModelScope.launch {
            series.value = service.searchseries(key, searchText).results
        }
    }

    fun searchActors(searchText : String) {
        viewModelScope.launch {
            actors.value = service.searchactors(key, searchText).results
        }
    }
}