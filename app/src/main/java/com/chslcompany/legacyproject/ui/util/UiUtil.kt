package com.chslcompany.legacyproject.ui.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestListener
import com.chslcompany.legacyproject.R
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineDispatcher


fun ImageView.buildImage(
    imageUrl: String?,
    error: Drawable? = null,
    placeholder: Drawable? = null,
    listener: RequestListener<Drawable>? = null
) {
    Glide.with(this)
        .load(imageUrl)
        .error(error)
        .centerCrop()
        .listener(listener)
        .placeholder(placeholder)
        .format(DecodeFormat.PREFER_RGB_565)
        .into(this)
}

fun CircleImageView.loadImage(
    imageUrl: String?
) =
    Glide.with(this)
        .load(imageUrl)
        .error(R.drawable.ic_round_account_circle)
        .centerCrop()
        .into(this)


interface DispatcherProvider {
    val main : CoroutineDispatcher
    val io : CoroutineDispatcher
    val default : CoroutineDispatcher
    val unconfined : CoroutineDispatcher
}

