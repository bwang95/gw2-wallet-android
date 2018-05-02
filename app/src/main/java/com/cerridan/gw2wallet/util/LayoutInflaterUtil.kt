package com.cerridan.gw2wallet.util

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

@Suppress("UNCHECKED_CAST")
fun <T: View> LayoutInflater.castedInflate(
    @LayoutRes layout: Int,
    parent: ViewGroup,
    attachToParent: Boolean = false
): T = inflate(layout, parent, attachToParent) as T