package com.example.kotlincouratinusviewmodelresource.repository

import com.example.kotlincouratinusviewmodelresource.api.ApiService
import com.example.kotlincouratinusviewmodelresource.models.Request

class TodoRepository(val apiService: ApiService) {
    suspend fun getAllTodo() = apiService.getAllTodo()
    suspend fun addTodo(request: Request) = apiService.addTodo(request)
    suspend fun updateTodo(id:Int,request: Request) = apiService.updateTodo(id,request)
    suspend fun deleteTodo(id:Int) = apiService.deleteTodo(id)



}