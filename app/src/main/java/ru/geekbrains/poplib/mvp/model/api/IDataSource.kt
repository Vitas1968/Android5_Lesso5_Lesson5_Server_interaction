package ru.geekbrains.poplib.mvp.model.api

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url
import ru.geekbrains.poplib.mvp.model.entity.GithubRepository
import ru.geekbrains.poplib.mvp.model.entity.GithubUser

// https://api.github.com/users/googlesamples

interface IDataSource {
    @GET("/users/{user}")
    fun getUser(@Path("user") username: String): Single<GithubUser>
    @GET
    fun getUserRepos(@Url url: String): Single<List<GithubRepository>>
}