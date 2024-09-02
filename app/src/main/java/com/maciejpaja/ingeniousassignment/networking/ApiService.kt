package com.maciejpaja.ingeniousassignment.networking

import com.maciejpaja.ingeniousassignment.models.remote.GitHubUserDetails
import com.maciejpaja.ingeniousassignment.models.remote.GitHubUserModel

interface ApiService {
    suspend fun getUsers(): Result<List<GitHubUserModel>>

    suspend fun getUserDetails(userId: Int): Result<GitHubUserDetails>
}