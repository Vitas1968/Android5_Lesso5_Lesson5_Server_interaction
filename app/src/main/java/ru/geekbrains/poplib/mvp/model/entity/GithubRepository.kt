package ru.geekbrains.poplib.mvp.model.entity

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

//https://api.github.com/users/googlesamples/repos
@Parcelize
data class GithubRepository(
    @Expose val id: String,
    @Expose val name: String,
    @Expose val forksCount: Int
) : Parcelable