package com.joaogoes.marvelwiki.utils.view

import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.viewbinding.ViewBinding

fun <T : ViewBinding> T.hideAll() {
    (root as ViewGroup).children.forEach { it.visibility = View.GONE }
}

fun <T : ViewBinding> T.showOnly(view: View) {
    (root as ViewGroup).children.forEach {
        if (it == view)
            it.visibility = View.VISIBLE
        else
            it.visibility = View.GONE
    }
}