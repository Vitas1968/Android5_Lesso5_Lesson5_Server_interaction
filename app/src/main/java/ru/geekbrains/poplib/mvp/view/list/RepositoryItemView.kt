package ru.geekbrains.poplib.mvp.view.list

interface RepositoryItemView {
    var pos: Int

    fun setTitle(text: String)
}