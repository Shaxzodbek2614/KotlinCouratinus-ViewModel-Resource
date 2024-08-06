package com.example.kotlincouratinusviewmodelresource.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlincouratinusviewmodelresource.api.ApiClient
import com.example.kotlincouratinusviewmodelresource.models.MyTodo
import com.example.kotlincouratinusviewmodelresource.models.Request
import com.example.kotlincouratinusviewmodelresource.models.Responce
import com.example.kotlincouratinusviewmodelresource.repository.TodoRepository
import com.example.kotlincouratinusviewmodelresource.utils.Resource
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class MyTodoViewModel(val todoRepository: TodoRepository) : ViewModel() {
    private val liveData = MutableLiveData<Resource<List<MyTodo>>>()
    fun getAllTodo(): MutableLiveData<Resource<List<MyTodo>>> {

        viewModelScope.launch {
            liveData.postValue(Resource.loading())

            try {
                coroutineScope {
                    val async = async {
                        todoRepository.getAllTodo()
                    }.await()

                    liveData.postValue(Resource.success(async))
                }
            } catch (e: Exception) {
                liveData.postValue(Resource.error(e.message.toString()))
            }
        }

        return liveData
    }

    private val postLiveData = MutableLiveData<Resource<Responce>>()
    fun addTodo(request: Request):MutableLiveData<Resource<Responce>> {
        postLiveData.postValue(Resource.loading())
        viewModelScope.launch {
            try {
                coroutineScope {
                    val async = async {
                        todoRepository.addTodo(request)
                    }.await()
                    postLiveData.postValue(Resource.success(async))
                    getAllTodo()
                }
            } catch (e: Exception) {
                postLiveData.postValue(Resource.error(e.message.toString()))
            }

        }
        return postLiveData
    }

    val updateLiveData = MutableLiveData<Resource<Responce>>()
    fun updateTodo(id:Int,request: Request):MutableLiveData<Resource<Responce>> {
        updateLiveData.postValue(Resource.loading())
        viewModelScope.launch {
            try {
                coroutineScope {
                    val async = async {
                        todoRepository.updateTodo(id, request)
                    }.await()
                    updateLiveData.postValue(Resource.success(async))
                    getAllTodo()
                }
            }catch (e:Exception){
                updateLiveData.postValue(Resource.error(e.message.toString()))
            }
        }
        return updateLiveData
    }

    fun deleteTodo(id :Int){
        viewModelScope.launch {
            try {
                coroutineScope {
                    launch {
                        todoRepository.deleteTodo(id)
                        getAllTodo()
                    }
                }
            }catch (e:Exception){}

        }
    }
}