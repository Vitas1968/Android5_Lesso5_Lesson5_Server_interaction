package ru.geekbrains.poplib.mvp.model.entity.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoomGithubUser(
    @PrimaryKey val login: String,
    var avatarUrl: String,
    var reposUrl: String
)