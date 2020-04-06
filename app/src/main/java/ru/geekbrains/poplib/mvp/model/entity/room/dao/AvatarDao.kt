package ru.geekbrains.poplib.mvp.model.entity.room.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.geekbrains.poplib.mvp.model.entity.room.RoomCachedImage

interface AvatarDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAvatar(roomCachedImage:RoomCachedImage)

    @Query("SELECT * FROM RoomCachedImage WHERE userLogin = :login")
    fun findPathAvaterUser(login: String): RoomCachedImage
}