package com.maciejpaja.ingeniousassignment.networking

import com.maciejpaja.ingeniousassignment.models.remote.GitHubUserDetails
import com.maciejpaja.ingeniousassignment.models.remote.GitHubUserModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ApiServiceImpl(private val client: HttpClient): ApiService {

    override suspend fun getUsers(): Result<List<GitHubUserModel>> = runCatching {
        client.get("$BASE_URL/users").body<List<GitHubUserModel>>()
    }

    override suspend fun getUserDetails(userId: Int): Result<GitHubUserDetails> = runCatching {
        client.get("$BASE_URL/user/$userId").body<GitHubUserDetails>()
    }


    companion object {
        private const val BASE_URL = "https://api.github.com"
    }
}