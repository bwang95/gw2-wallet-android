package com.cerridan.gw2wallet.dagger

import android.app.Application
import android.content.Context

class DaggerInjector(app: Application) {
  companion object {
    private lateinit var INSTANCE: DaggerInjector

    fun init(app: Application) {
      INSTANCE = DaggerInjector(app)
    }

    val appComponent
      get() = INSTANCE.appComponent
  }

  val appComponent: AppComponent = DaggerAppComponent.builder()
      .appModule(AppModule(app))
      .build()
}