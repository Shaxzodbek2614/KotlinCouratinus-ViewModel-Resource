package com.example.kotlincouratinusviewmodelresource.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kotlincouratinusviewmodelresource.repository.TodoRepository

class MyViewModelFactory(val todoRepository: TodoRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyTodoViewModel::class.java)){
            return MyTodoViewModel(todoRepository) as T
        }
        throw IllegalArgumentException("Error")
    }
}