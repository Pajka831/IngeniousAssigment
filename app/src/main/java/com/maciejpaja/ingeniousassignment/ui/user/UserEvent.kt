package com.maciejpaja.ingeniousassignment.ui.user

sealed class UserEvent {
    data class Error(val error: String) : UserEvent()
    data object Loading : UserEvent()
}