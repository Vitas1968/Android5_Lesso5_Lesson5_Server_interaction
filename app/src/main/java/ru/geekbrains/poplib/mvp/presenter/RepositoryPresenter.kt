package ru.geekbrains.poplib.mvp.presenter

import moxy.InjectViewState
import moxy.MvpPresenter
import ru.geekbrains.poplib.mvp.model.entity.GithubRepository
import ru.geekbrains.poplib.mvp.view.RepositoryView
import ru.terrakok.cicerone.Router

@InjectViewState
class RepositoryPresenter(val githubRepository: GithubRepository, val router: Router) : MvpPresenter<RepositoryView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        viewState.setId(githubRepository.id)
        viewState.setTitle(githubRepository.name)
        viewState.setForksCount(githubRepository.forksCount.toString())
    }

    fun backClicked() : Boolean {
        router.exit()
        return true
    }
}
