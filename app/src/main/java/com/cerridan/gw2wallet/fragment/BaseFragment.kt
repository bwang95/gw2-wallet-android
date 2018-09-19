package com.cerridan.gw2wallet.fragment

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseFragment(@LayoutRes private val layout: Int): Fragment() {
  private val subscriptions = CompositeDisposable()

  final override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View = inflater.inflate(layout, container, false)

  @CallSuper
  override fun onDestroyView() {
    subscriptions.clear()
    super.onDestroyView()
  }

  protected fun Disposable.unsubscribeOnDestroy() = subscriptions.add(this)
}