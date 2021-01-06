package com.joaogoes.marvelwiki.presentation.bindingadapters

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.joaogoes.marvelwiki.R

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
