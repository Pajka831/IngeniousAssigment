package com.maciejpaja.ingeniousassignment.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maciejpaja.ingeniousassignment.data.repository.UserRepository
import com.maciejpaja.ingeniousassignment.models.Details
import com.maciejpaja.ingeniousassignment.ui.ResultState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val userRepository: UserRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _event = MutableSharedFlow<DetailEvent>()
    val event = _event.asSharedFlow()

    private val _detailsState = MutableStateFlow<Details?>(null)
    val detailsState = _detailsState.asStateFlow()

    fun getUserDetails(userId: Int) {
        viewModelScope.launch(ioDispatcher) {
            userRepository.getUserDetails(userId).collect {
                    when (it) {
                        is ResultState.Success -> {
                            _detailsState.value = it.data
                        }

                        is ResultState.Error -> {
                            _event.emit(
                                DetailEvent.Error(
                                    it.throwable.message ?: "Can't download user data"
                                )
                            )
                        }

                        is ResultState.Loading -> {
                            _event.emit(DetailEvent.Loading)
                        }
                    }
                }
        }
    }
}