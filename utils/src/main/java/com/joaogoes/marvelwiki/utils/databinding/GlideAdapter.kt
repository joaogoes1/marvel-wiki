package com.joaogoes.marvelwiki.utils.databinding

import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.joaogoes.marvelwiki.utils.R

@BindingAdapter("imageUrl", requireAll = false)
fun loadImage(
    view: AppCompatImageView,
    imageUrl: String?,
) {
    Glide
        .with(view.context)
        .load(imageUrl)
        .centerCrop()
        .placeholder(R.drawable.ic_marvel_logo)
        .error(R.drawable.ic_marvel_logo)
        .dontAnimate()
        .into(view)
}

fun ImageView.loadImage(imageUrl: String?) {
    Glide
        .with(context)
        .load(imageUrl)
        .centerCrop()
        .placeholder(R.drawable.ic_marvel_logo)
        .error(R.drawable.ic_marvel_logo)
        .dontAnimate()
        .into(this)
}
