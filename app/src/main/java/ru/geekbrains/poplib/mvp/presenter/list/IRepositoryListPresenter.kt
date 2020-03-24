package ru.geekbrains.poplib.mvp.presenter.list

import ru.geekbrains.poplib.mvp.view.list.RepositoryItemView

interface IRepositoryListPresenter {
    var itemClickListener: ((RepositoryItemView) -> Unit)?
    fun getCount(): Int
    fun bindView(view: RepositoryItemView)
}