package ru.referon.rickandmorty.service

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.referon.rickandmorty.model.MainModel
import ru.referon.rickandmorty.model.Result

private val BASE_URL = "https://rickandmortyapi.com/api/"
private val retrofit by lazy {
    Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
}

interface ApiService {
    @GET("character")
    suspend fun getInfo(@Query("page") page: Int): Response<MainModel>

    @GET("character/{id}")
    suspend fun getPersonInfo(@Path("id") id: Int): Response<Result>

}
object Api {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}