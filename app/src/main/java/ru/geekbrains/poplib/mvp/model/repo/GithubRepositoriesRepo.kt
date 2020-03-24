package ru.geekbrains.poplib.mvp.model.repo

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.geekbrains.poplib.mvp.model.api.ApiHolder.api
import ru.geekbrains.poplib.mvp.model.api.IDataSource
import ru.geekbrains.poplib.mvp.model.entity.GithubRepository

class GithubRepositoriesRepo(val api: IDataSource) {

    fun getUserRepo(url: String) = api.getUserRepos(url).subscribeOn(Schedulers.io())
}