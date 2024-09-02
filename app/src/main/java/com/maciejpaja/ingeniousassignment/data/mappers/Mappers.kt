package com.maciejpaja.ingeniousassignment.data.mappers

import com.maciejpaja.ingeniousassignment.models.Details
import com.maciejpaja.ingeniousassignment.models.User
import com.maciejpaja.ingeniousassignment.models.remote.GitHubUserDetails
import com.maciejpaja.ingeniousassignment.models.remote.GitHubUserModel

fun mapRemoteUserToUserList(gitHubUserListModel: List<GitHubUserModel>) =
    gitHubUserListModel.map {
        User(
            id = it.id,
            login = it.login,
            avatarUrl = it.avatarUrl
        )
    }

fun mapRemoteDetailsToDetails(gitHubUserDetails: GitHubUserDetails) = Details(
    name = gitHubUserDetails.name,
    login = gitHubUserDetails.login,
    email = gitHubUserDetails.email,
    location = gitHubUserDetails.location,
    avatarUrl = gitHubUserDetails.avatarUrl
)