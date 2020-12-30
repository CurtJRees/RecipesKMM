package com.curtjrees.recipes.androidApp.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

data class Resource<out T>(val status: Status, val data: T?, val error: Throwable?) {

    companion object {
        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(data: T?, error: Throwable): Resource<T> {
            return Resource(Status.ERROR, data, error)
        }
    }

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }
}

val <T> Resource<T>?.isLoading: Boolean get() = this?.status == Resource.Status.LOADING
val <T> Resource<T>?.isSuccess: Boolean get() = this?.status == Resource.Status.SUCCESS
val <T> Resource<T>?.isError: Boolean get() = this?.status == Resource.Status.ERROR
val <T> Resource<T>?.isComplete: Boolean get() = this?.status == Resource.Status.ERROR || this?.status == Resource.Status.SUCCESS
val <T> Resource<T>?.isNullOrLoading: Boolean get() = this == null || this.isLoading


//TODO: Fix this, since onStart isn't being called
//fun <T : Any> Flow<T>.wrapResource(getPreviousData: () -> T?): Flow<Resource<T>> =
//    this
//        .map { Resource.success(it) }
//        .catch { emit(Resource.error(error = it, data = getPreviousData.invoke())) }
//        .onStart { emit(Resource.success(getPreviousData.invoke())) }

fun <T> Flow<Resource<T>>.debounceLoading(time: Long = 330L) = this.debounce {
    if(it.isLoading) time else 0L
}