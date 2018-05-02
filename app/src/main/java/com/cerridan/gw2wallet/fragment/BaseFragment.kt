package com.cerridan.gw2wallet.fragment

import android.app.Fragment
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseFragment(@LayoutRes private val layout: Int): Fragment() {
  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup,
      savedInstanceState: Bundle?
  ): View = inflater.inflate(layout, container, false)
}