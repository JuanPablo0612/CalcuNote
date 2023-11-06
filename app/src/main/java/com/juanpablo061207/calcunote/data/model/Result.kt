package com.juanpablo061207.calcunote.data.model

sealed class Result<out R> {
    data class Success<out T>(val data: T?) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

fun <T> Result<T>.isSuccess() = this is Result.Success

fun <T> Result<T>.data() = (this as Result.Success).data!!

fun <T> Result<T>.exception() = (this as Result.Error).exception