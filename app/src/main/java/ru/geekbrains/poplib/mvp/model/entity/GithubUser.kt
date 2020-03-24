package ru.geekbrains.poplib.mvp.model.entity

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

//https://api.github.com/users/googlesamples
@Parcelize
data class GithubUser(
    @Expose val login: String,
    @Expose val avatarUrl: String,
    @Expose val reposUrl: String
) : Parcelable