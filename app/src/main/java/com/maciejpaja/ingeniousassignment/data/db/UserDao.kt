package com.maciejpaja.ingeniousassignment.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(user: List<UserEntity>)

    @Query("SELECT * FROM users")
    fun getUsers(): List<UserEntity>
}