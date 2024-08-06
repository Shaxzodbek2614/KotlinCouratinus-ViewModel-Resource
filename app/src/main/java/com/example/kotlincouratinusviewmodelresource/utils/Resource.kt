package com.example.kotlincouratinusviewmodelresource.utils

data class Resource<out T>(val status: Status,val data:T?, val message:String){
    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, "")
        }
        fun <T> error(message: String):Resource<T>{
            return Resource(Status.ERROR,null,message)
        }
        fun <T> loading():Resource<T>{
            return Resource(Status.LOADING,null,"")
        }
    }

}
