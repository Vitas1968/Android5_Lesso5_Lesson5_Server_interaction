package ru.geekbrains.poplib.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_repository.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.geekbrains.poplib.R
import ru.geekbrains.poplib.mvp.model.entity.GithubRepository
import ru.geekbrains.poplib.mvp.presenter.RepositoryPresenter
import ru.geekbrains.poplib.mvp.view.RepositoryView
import ru.geekbrains.poplib.ui.App
import ru.geekbrains.poplib.ui.BackButtonListener

class RepositoryFragment : MvpAppCompatFragment(), RepositoryView, BackButtonListener {

    companion object {
        const val REPOSITORY_KEY = "repository"

        fun newInstance(repository: GithubRepository) = RepositoryFragment().apply {
            arguments = Bundle().apply {
                putParcelable(REPOSITORY_KEY, repository)
            }
        }
    }

    @InjectPresenter
    lateinit var presenter: RepositoryPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        View.inflate(context, R.layout.fragment_repository, null)

    @ProvidePresenter
    fun providePresenter() = RepositoryPresenter(arguments!![REPOSITORY_KEY] as GithubRepository, App.instance.router)

    override fun init() {

    }

    override fun setId(text: String) {
        tv_id.text = text
    }

    override fun setTitle(text: String) {
        tv_title.text = text
    }

    override fun setForksCount(text: String) {
        tv_forksCount.text = text
    }

    override fun backClicked() = presenter.backClicked()
}