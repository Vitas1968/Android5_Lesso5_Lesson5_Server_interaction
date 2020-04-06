package ru.geekbrains.poplib.ui.caching

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.geekbrains.poplib.mvp.model.caching.ICachedImage
import ru.geekbrains.poplib.mvp.model.entity.room.RoomCachedImage
import ru.geekbrains.poplib.mvp.model.entity.room.db.Database
import ru.geekbrains.poplib.mvp.model.network.NetworkStatus

class RoomImageCache(val networkStatus: NetworkStatus, val database: Database):ICachedImage {
    override fun isOnlineSingle()= networkStatus.isOnlineSingle()
    override fun saveImageToDB(path:String, avatarUrl: String, userLogin: String){
        path?.let {
           val avatarUser=RoomCachedImage(avatarUrl,path,userLogin)
            database.avatarDao.insertAvatar(avatarUser)
        }

    }
    override fun getImageFromDB(userLogin: String)= Single.create<RoomCachedImage> { emitter ->
        database.avatarDao.findPathAvaterUser(userLogin)?.let {
            emitter.onSuccess(it)
        }
        emitter.onError(RuntimeException("No such avatar in cache"))
        }.subscribeOn(Schedulers.io())
    }
