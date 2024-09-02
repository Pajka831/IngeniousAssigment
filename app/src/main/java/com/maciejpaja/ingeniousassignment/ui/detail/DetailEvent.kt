package com.maciejpaja.ingeniousassignment.ui.detail

sealed class DetailEvent {
    data class Error(val error: String) : DetailEvent()
    data object Loading : DetailEvent()
}