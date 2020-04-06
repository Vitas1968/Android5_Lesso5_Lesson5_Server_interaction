package ru.geekbrains.poplib.ui.caching

import io.reactivex.rxjava3.core.Single
import ru.geekbrains.poplib.mvp.model.entity.GithubRepository
import ru.geekbrains.poplib.mvp.model.entity.GithubUser
import ru.geekbrains.poplib.mvp.model.entity.room.RoomGithubRepository
import ru.geekbrains.poplib.mvp.model.entity.room.db.Database
import ru.geekbrains.poplib.mvp.model.network.NetworkStatus
import ru.geekbrains.poplib.mvp.model.caching.IRepositoriesCache
import java.lang.RuntimeException

class RoomRepositoriesCache(val networkStatus: NetworkStatus, val database: Database) :
    IRepositoriesCache {

    override fun isOnlineSingle() = networkStatus.isOnlineSingle()

    override fun cashingIntoDatabase(user: GithubUser, repos: List<GithubRepository>){
        repos.takeIf { it.isNotEmpty() }?.let {
            val roomRepos = repos.map {
                RoomGithubRepository(it.id, it.name, it.forksCount, user.login) }
            database.repositoryDao.insert(roomRepos)
        }
    }

    override fun getReposFromDatabase(user: GithubUser)=
        Single.create<List<GithubRepository>> { emitter ->
            database.userDao.findByLogin(user.login)?.let { roomUser ->
                val roomRepos = database.repositoryDao.findForUser(user.login)
                val repos = roomRepos.map {
                    GithubRepository(it.id, it.name, it.forksCount) }
                emitter.onSuccess(repos)
            } ?: let {
                emitter.onError(RuntimeException("No such repository in cache"))
            }
        }
}