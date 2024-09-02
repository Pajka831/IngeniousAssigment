package com.maciejpaja.ingeniousassignment.data.repository

import com.maciejpaja.ingeniousassignment.data.db.UserDao
import com.maciejpaja.ingeniousassignment.data.db.UserEntity
import com.maciejpaja.ingeniousassignment.data.mappers.mapRemoteDetailsToDetails
import com.maciejpaja.ingeniousassignment.data.mappers.mapRemoteUserToUserList
import com.maciejpaja.ingeniousassignment.models.Details
import com.maciejpaja.ingeniousassignment.models.User
import com.maciejpaja.ingeniousassignment.networking.ApiService
import com.maciejpaja.ingeniousassignment.ui.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl(
    private val apiService: ApiService,
    private val userDao: UserDao
) : UserRepository {
    override suspend fun getUsers(): Flow<ResultState<List<User>>> {
        return flow {
            emit(ResultState.Loading)
            val dbUsers = userDao.getUsers()
            if (dbUsers.isNotEmpty()) emit(ResultState.Success(dbUsers.map { it.toUser() }))
            val userList = apiService.getUsers()

            userList.fold(
                onSuccess = {
                    userDao.insertUsers(mapRemoteUserToUserList(it).map { user ->
                        UserEntity.fromUser(
                            user
                        )
                    })
                    emit(ResultState.Success(mapRemoteUserToUserList(it)))
                },
                onFailure = { emit(ResultState.Error(it)) }
            )
        }
    }

    override suspend fun getUserDetails(userId: Int): Flow<ResultState<Details>> {
        return flow {
            emit(ResultState.Loading)
            apiService.getUserDetails(userId).fold(
                onSuccess = {
                    emit(ResultState.Success(mapRemoteDetailsToDetails(it)))
                },
                onFailure = {
                    emit(ResultState.Error(it))
                }
            )
        }
    }

}
