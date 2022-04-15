package ru.referon.rickandmorty.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.lang.Exception
import ru.referon.rickandmorty.FeedModel
import ru.referon.rickandmorty.repository.Repository

class ViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository()
    private val _data = MutableLiveData(FeedModel())
    val data: LiveData<FeedModel>
        get() = _data

    fun loadInfo(page: Int) {
        viewModelScope.launch {
            _data.value = FeedModel(loading = true)
            try {
                val result = repository.getInfo(page)
                if (!result.isSuccessful){
                    throw Exception()
                }
                val body = result.body() ?: throw Exception()
                _data.value = FeedModel(info = body)
            }catch (e: Exception){
                _data.value = FeedModel(error = true)
            }
        }
    }

    fun loadPersonInfo(id: Int) {
        viewModelScope.launch {
            _data.value = FeedModel(loading = true)
            try {
                val result = repository.getPersonInfo(id)
                if (!result.isSuccessful){
                    throw Exception()
                }
                val body = result.body() ?: throw Exception()
                _data.value = FeedModel(personInfo = body)
            }catch (e: Exception){
                _data.value = FeedModel(error = true)
            }
        }
    }
}