package com.joaogoes.marvelwiki.utils.databinding

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("visible")
fun visible(view: View, isVisible: Boolean) {
    view.visibility = if (isVisible) View.VISIBLE else View.GONE
}