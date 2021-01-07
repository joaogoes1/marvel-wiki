package com.joaogoes.marvelwiki.presentation.bindingadapters

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("visible")
fun visible(view: View, isVisible: Boolean) {
    view.visibility = if (isVisible) View.VISIBLE else View.GONE
}