package com.cerridan.gw2wallet.fragment

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup

abstract class BaseFragment(): Fragment() {
  abstract val layout: Int

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup,
      savedInstanceState: Bundle?
  ) = inflater.inflate(layout, container, false)
}