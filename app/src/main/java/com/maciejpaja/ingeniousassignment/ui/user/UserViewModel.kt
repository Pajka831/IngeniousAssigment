package com.maciejpaja.ingeniousassignment.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maciejpaja.ingeniousassignment.data.repository.UserRepository
import com.maciejpaja.ingeniousassignment.models.User
import com.maciejpaja.ingeniousassignment.ui.ResultState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel(
    private val userRepository: UserRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel() {

    private val _event = MutableSharedFlow<UserEvent>()
    val event = _event.asSharedFlow()

    private val _usersState = MutableStateFlow<List<User>?>(null)
    val usersState = _usersState.asStateFlow()

    private var userList: List<User> = listOf()

    private var textChangeJob: Job? = null

    init {
        downloadUsersData()
    }

    private fun downloadUsersData() {
        viewModelScope.launch(ioDispatcher) {
            userRepository.getUsers()
                .collect{
                    when(it){
                        is ResultState.Success -> {
                            userList = it.data
                            _usersState.value = userList
                        }
                        is ResultState.Error -> {
                            _event.emit(UserEvent.Error(it.throwable.message?: "Can't download user list"))
                        }

                        is ResultState.Loading -> {
                            _event.emit(UserEvent.Loading)
                        }
                    }
                }
        }
    }

    fun onSearchInputChange(input: String) {
        textChangeJob?.cancel()
        textChangeJob = viewModelScope.launch(ioDispatcher) {
            delay(500)
            _usersState.value = filterUserList(input)
        }
    }

    private fun filterUserList(input: String): List<User> {
        return userList.filter { user ->
            user.login.contains(input)
        }
    }
}