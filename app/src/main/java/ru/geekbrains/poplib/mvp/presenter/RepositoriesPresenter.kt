package ru.geekbrains.poplib.mvp.presenter

import io.reactivex.rxjava3.core.Scheduler
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.geekbrains.poplib.mvp.model.entity.GithubRepository
import ru.geekbrains.poplib.mvp.model.repo.GithubRepositoriesRepo
import ru.geekbrains.poplib.mvp.model.repo.GithubUsersRepo
import ru.geekbrains.poplib.mvp.presenter.list.IRepositoryListPresenter
import ru.geekbrains.poplib.mvp.view.RepositoriesView
import ru.geekbrains.poplib.mvp.view.list.RepositoryItemView
import ru.geekbrains.poplib.navigation.Screens
import ru.terrakok.cicerone.Router
import timber.log.Timber

@InjectViewState
class RepositoriesPresenter(
    val mainThreadScheduler: Scheduler,
    val router: Router,
    val repositoriesRepo: GithubRepositoriesRepo,
    val usersRepo: GithubUsersRepo
) : MvpPresenter<RepositoriesView>() {


    class RepositoryListPresenter : IRepositoryListPresenter {
        val repositories = mutableListOf<GithubRepository>()
        override var itemClickListener: ((RepositoryItemView) -> Unit)? = null

        override fun getCount() = repositories.size

        override fun bindView(view: RepositoryItemView) {
            val repository = repositories[view.pos]
            view.setTitle(repository.name)
        }
    }

    val repositoryListPresenter = RepositoryListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadUser()

        repositoryListPresenter.itemClickListener = { itemView ->
            val repository = repositoryListPresenter.repositories[itemView.pos]
            router.navigateTo(Screens.RepositoryScreen(repository))
        }
    }

    fun loadUser() {
       //val userName="googlesamples"
        val userName="Vitas1968"
        usersRepo.getUser(userName)
            .observeOn(mainThreadScheduler)
            .flatMap{
                viewState.setUsername(it.login)
                viewState.loadAvatar(it.avatarUrl, it.login)
                return@flatMap repositoriesRepo.getUserRepositories(it)
            }
            .observeOn(mainThreadScheduler)
            .subscribe({
                loadRepos(it)
            }, {
                Timber.e(it)
            })
    }

    fun loadRepos(list: List<GithubRepository>?) {
        list?.let {
            repositoryListPresenter.repositories.clear()
            repositoryListPresenter.repositories.addAll(list)
            viewState.updateList()
        }

    }

    fun backClicked(): Boolean {
        router.exit()
        return true
    }
}
