package ru.geekbrains.poplib.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_repositories.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.geekbrains.poplib.R
import ru.geekbrains.poplib.mvp.model.api.ApiHolder
import ru.geekbrains.poplib.mvp.model.repo.GithubRepositoriesRepo
import ru.geekbrains.poplib.mvp.model.repo.GithubUsersRepo
import ru.geekbrains.poplib.mvp.presenter.RepositoriesPresenter
import ru.geekbrains.poplib.mvp.view.RepositoriesView
import ru.geekbrains.poplib.ui.App
import ru.geekbrains.poplib.ui.BackButtonListener
import ru.geekbrains.poplib.ui.adapter.RepositoriesRVAdapter
import ru.geekbrains.poplib.ui.image.GlideImageLoader


class RepositoriesFragment : MvpAppCompatFragment(), RepositoriesView, BackButtonListener {

    companion object {
        private const val PICK_IMAGE_REQUEST_ID = 1
        fun newInstance() = RepositoriesFragment()
    }

    @InjectPresenter
    lateinit var presenter: RepositoriesPresenter

    val imageLoader = GlideImageLoader()

    var adapter: RepositoriesRVAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        View.inflate(context, R.layout.fragment_repositories, null)


    @ProvidePresenter
    fun providePresenter() = RepositoriesPresenter(
        AndroidSchedulers.mainThread(),
        App.instance.router,
        GithubRepositoriesRepo(ApiHolder.api),
        GithubUsersRepo(ApiHolder.api)
    )


    override fun init() {
        rv_repos.layoutManager = LinearLayoutManager(context)
        adapter = RepositoriesRVAdapter(presenter.repositoryListPresenter)
        rv_repos.adapter = adapter

    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun setUsername(text: String) {
        tv_username.text = text
    }

    override fun loadAvatar(avatarUrl: String) {
        imageLoader.loadInto(avatarUrl, iv_avatar)
    }

    override fun backClicked() = presenter.backClicked()
}