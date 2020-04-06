package ru.geekbrains.poplib.mvp.model.entity.room

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = RoomGithubUser::class,
        parentColumns = ["login"],
        childColumns = ["userLogin"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class RoomCachedImage(
    @PrimaryKey
    var avatarUrl: String,
    val path: String,
    val userLogin: String
)