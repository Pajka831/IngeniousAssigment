package com.maciejpaja.ingeniousassignment.data.repository

import com.maciejpaja.ingeniousassignment.models.Details
import com.maciejpaja.ingeniousassignment.models.User
import com.maciejpaja.ingeniousassignment.ui.ResultState
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUsers(): Flow<ResultState<List<User>>>
    suspend fun getUserDetails(userId: Int): Flow<ResultState<Details>>
}