package ru.geekbrains.poplib.mvp.model.repo

import io.reactivex.rxjava3.schedulers.Schedulers
import ru.geekbrains.poplib.mvp.model.api.IDataSource
import ru.geekbrains.poplib.mvp.model.caching.IUserCache

class GithubUsersRepo(val api: IDataSource, val roomUserCache: IUserCache) {

     fun getUser(username: String) = roomUserCache.isOnlineSingle().flatMap { isOnline ->
        if (isOnline) {
            api.getUser(username)
                .map { user ->
                    roomUserCache.cashingIntoDatabase(user,username)
                    user
                }
        } else {
            roomUserCache.getUserFromDatabase(username)
        }
    }.subscribeOn(Schedulers.io())

}
