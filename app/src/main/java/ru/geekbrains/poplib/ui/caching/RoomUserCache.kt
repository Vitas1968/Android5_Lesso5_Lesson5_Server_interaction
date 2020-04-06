package ru.geekbrains.poplib.ui.caching

import io.reactivex.rxjava3.core.Single
import ru.geekbrains.poplib.mvp.model.entity.GithubUser
import ru.geekbrains.poplib.mvp.model.entity.room.RoomGithubUser
import ru.geekbrains.poplib.mvp.model.entity.room.db.Database
import ru.geekbrains.poplib.mvp.model.network.NetworkStatus
import ru.geekbrains.poplib.mvp.model.caching.IUserCache
import java.lang.RuntimeException

class RoomUserCache(val networkStatus: NetworkStatus,
                    val database: Database) :
    IUserCache {

    override fun isOnlineSingle() = networkStatus.isOnlineSingle()

    override fun cashingIntoDatabase(user : GithubUser, username: String) {
            val roomUser = database.userDao.findByLogin(username)?.apply {
                avatarUrl = user.avatarUrl
                reposUrl = user.reposUrl
            } ?: RoomGithubUser(user.login, user.avatarUrl, user.reposUrl)
            database.userDao.insert(roomUser)
        }

    override fun getUserFromDatabase(username: String)=
        Single.create<GithubUser> { emitter ->
            database.userDao.findByLogin(username)?.let { roomUser ->
                emitter.onSuccess(GithubUser(roomUser.login, roomUser.avatarUrl, roomUser.reposUrl))
            } ?: let {
                emitter.onError(RuntimeException("No such user in cache"))
            }
        }


}