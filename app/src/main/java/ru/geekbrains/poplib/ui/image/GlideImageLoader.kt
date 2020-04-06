package ru.geekbrains.poplib.ui.image

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.geekbrains.poplib.mvp.model.caching.ICachedImage
import ru.geekbrains.poplib.mvp.model.image.IImageLoader
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.util.*

class GlideImageLoader(val cachedImage: ICachedImage) : IImageLoader<ImageView> {

    override fun loadInto(url: String, container: ImageView, userlogin:String) {
        cachedImage.isOnlineSingle().map{
            if (it){ //если сеть есть
                loadingImage(container, url,userlogin)
            } else{
                loadFromDB(userlogin,container)

            }
        }
       // loadingImage(container, url)
    }

    private fun loadFromDB(userLogin: String,container: ImageView) {
        userLogin?.let{ userLogin ->
            cachedImage.getImageFromDB(userLogin)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        Glide.with(container.context)
                            .load(it.path)
                            .into(container)

                    }, {
                        Timber.e("Error load from DB")
                    }
                )
        }


    }

    private fun loadingImage(container: ImageView, url: String, userlogin:String) {
        Glide.with(container.context)
            .asBitmap()
            .load(url)
            .listener(object : RequestListener<Bitmap> {

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    resource?.let {
                        cachingImage( it, container.context)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({path ->
                                cachedImage.saveImageToDB(path,url,userlogin)
                            }, {
                                Timber.e("Error save")
                            }

                            )
                    }

                    return true
                }
            })
            .into(container)
    }

    private fun cachingImage(bitmap: Bitmap, context: Context) = Single.create<String> {
        var filePath:String?=null
            context?.let {
                // имя файла пойдет в БД
                val fileName = UUID.randomUUID().toString() + ".png"
                val dst = File(it.getExternalFilesDir(null), fileName)
                filePath=dst.absolutePath
                val stream = FileOutputStream(dst)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                bitmap.recycle()
                stream.close()
            }
        it.onSuccess(filePath)
        }.subscribeOn(Schedulers.io())

    private fun saveImageToDB(url: String, filePath:String, userlogin:String){

    }
    private fun getImageFromDB(){

    }
}