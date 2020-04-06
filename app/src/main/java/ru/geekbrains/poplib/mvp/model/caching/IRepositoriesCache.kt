package ru.geekbrains.poplib.mvp.model.caching

import io.reactivex.rxjava3.core.Single
import ru.geekbrains.poplib.mvp.model.entity.GithubRepository
import ru.geekbrains.poplib.mvp.model.entity.GithubUser

interface IRepositoriesCache {
    fun isOnlineSingle() : Single<Boolean>
    fun cashingIntoDatabase(user: GithubUser, repos: List<GithubRepository>)
    fun getReposFromDatabase(user: GithubUser) : Single<List<GithubRepository>>
}