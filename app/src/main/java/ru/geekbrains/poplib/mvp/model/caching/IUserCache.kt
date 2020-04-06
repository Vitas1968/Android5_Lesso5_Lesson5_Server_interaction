package ru.geekbrains.poplib.mvp.model.caching

import io.reactivex.rxjava3.core.Single
import ru.geekbrains.poplib.mvp.model.entity.GithubUser

interface IUserCache {
   fun isOnlineSingle() : Single<Boolean>
    fun cashingIntoDatabase(user : GithubUser, username: String)
    fun getUserFromDatabase(username: String):Single<GithubUser>
}