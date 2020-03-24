package ru.geekbrains.poplib.navigation

import ru.geekbrains.poplib.mvp.model.entity.GithubRepository
import ru.geekbrains.poplib.ui.fragment.RepositoriesFragment
import ru.geekbrains.poplib.ui.fragment.RepositoryFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {

    class RepositoriesScreen() : SupportAppScreen() {
        override fun getFragment() = RepositoriesFragment.newInstance()
    }

    class RepositoryScreen(val repository: GithubRepository) : SupportAppScreen() {
        override fun getFragment() = RepositoryFragment.newInstance(repository)
    }

}