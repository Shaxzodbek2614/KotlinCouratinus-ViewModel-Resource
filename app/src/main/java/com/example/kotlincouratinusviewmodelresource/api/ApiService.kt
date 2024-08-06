package com.example.kotlincouratinusviewmodelresource.api

import com.example.kotlincouratinusviewmodelresource.models.MyTodo
import com.example.kotlincouratinusviewmodelresource.models.Request
import com.example.kotlincouratinusviewmodelresource.models.Responce
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("plan/")
    suspend fun getAllTodo(): List<MyTodo>

    @POST("plan/")
    suspend fun addTodo(@Body request: Request): Responce

    @PUT("plan/{id}/")
    suspend fun updateTodo(@Path("id") id:Int, @Body request: Request): Responce

    @DELETE("plan/{id}/")
    suspend fun deleteTodo(@Path("id") id: Int)

}