package ru.geekbrains.poplib.mvp.model.repo

import io.reactivex.rxjava3.schedulers.Schedulers
import ru.geekbrains.poplib.mvp.model.api.IDataSource
import ru.geekbrains.poplib.mvp.model.caching.IRepositoriesCache
import ru.geekbrains.poplib.mvp.model.entity.GithubUser

class GithubRepositoriesRepo(
    val api: IDataSource,
    val roomRepositoriesCache: IRepositoriesCache
) {

     fun getUserRepositories(user: GithubUser) = roomRepositoriesCache.isOnlineSingle().flatMap { isOnline ->
        if (isOnline) {
            val str=""
            api.getUserRepos(user.reposUrl)
                .map { repos ->
                    roomRepositoriesCache.cashingIntoDatabase(user,repos)
                    repos
                }
        } else {
            roomRepositoriesCache.getReposFromDatabase(user)
        }
    }.subscribeOn(Schedulers.io())
}