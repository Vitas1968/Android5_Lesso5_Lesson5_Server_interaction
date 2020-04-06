package ru.geekbrains.poplib.mvp.model.image

interface IImageLoader<T> {
    fun loadInto(url: String, container: T,userLogin:String)
}