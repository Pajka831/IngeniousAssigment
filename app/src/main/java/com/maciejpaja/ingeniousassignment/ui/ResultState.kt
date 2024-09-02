package com.maciejpaja.ingeniousassignment.ui

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class ResultState<out R> {

    data class Success<out T>(val data: T) : ResultState<T>()
    data class Error(val throwable: Throwable) : ResultState<Nothing>()
    data object Loading : ResultState<Nothing>()

}