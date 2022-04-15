package ru.referon.rickandmorty.repository

import retrofit2.Response
import ru.referon.rickandmorty.model.MainModel
import ru.referon.rickandmorty.model.Result
import ru.referon.rickandmorty.service.Api

class Repository {

    suspend fun getInfo(page: Int): Response<MainModel> = Api.retrofitService.getInfo(page)
    suspend fun getPersonInfo(id: Int): Response<Result> = Api.retrofitService.getPersonInfo(id)
}