package ru.referon.rickandmorty

import ru.referon.rickandmorty.model.MainModel
import ru.referon.rickandmorty.model.Result

data class FeedModel(
    val info: MainModel? = null,
    val personInfo: Result? = null,
    val error: Boolean = false,
    val loading: Boolean = false
)
