package com.maciejpaja.ingeniousassignment.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maciejpaja.ingeniousassignment.models.User

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "login")
    val login: String,
    @ColumnInfo(name = "avatarUrl")
    val avatarUrl: String
) {

        fun toUser(): User {
            return User(
                id = this.id,
                login = this.login,
                avatarUrl = this.avatarUrl
            )
        }
        companion object {
            fun fromUser(user: User): UserEntity {
                return UserEntity(
                    id = user.id,
                    login = user.login,
                    avatarUrl = user.avatarUrl
                )
            }
        }
}
