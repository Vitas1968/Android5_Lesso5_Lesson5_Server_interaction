package ru.geekbrains.poplib.mvp.model.caching

import io.reactivex.rxjava3.core.Single
import ru.geekbrains.poplib.mvp.model.entity.room.RoomCachedImage

interface ICachedImage {
    fun isOnlineSingle() : Single<Boolean>
    fun saveImageToDB(path:String, avatarUrl: String, userLogin: String)
    fun getImageFromDB(userLogin: String) : Single<RoomCachedImage>
}