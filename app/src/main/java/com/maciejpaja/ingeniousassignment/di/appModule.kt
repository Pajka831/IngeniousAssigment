package com.maciejpaja.ingeniousassignment.di

import androidx.room.Room
import com.maciejpaja.ingeniousassignment.data.db.UserDao
import com.maciejpaja.ingeniousassignment.data.db.UserDatabase
import com.maciejpaja.ingeniousassignment.data.repository.UserRepository
import com.maciejpaja.ingeniousassignment.data.repository.UserRepositoryImpl
import com.maciejpaja.ingeniousassignment.networking.ApiService
import com.maciejpaja.ingeniousassignment.networking.ApiServiceImpl
import com.maciejpaja.ingeniousassignment.networking.httpClient
import com.maciejpaja.ingeniousassignment.ui.detail.DetailViewModel
import com.maciejpaja.ingeniousassignment.ui.user.UserViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single<ApiService> { ApiServiceImpl(httpClient) }
    single<UserRepository> { UserRepositoryImpl(get(), get()) }
    single {
        Room.databaseBuilder(androidContext(), UserDatabase::class.java, "user_database")
            .build()
    }
    single<UserDao> {
        get<UserDatabase>().userDao()
    }
    factory { UserViewModel(get()) }
    factory { DetailViewModel(get()) }
}