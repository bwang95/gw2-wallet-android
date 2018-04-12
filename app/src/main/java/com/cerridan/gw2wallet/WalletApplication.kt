package com.cerridan.gw2wallet

import android.app.Application
import com.cerridan.gw2wallet.dagger.DaggerInjector

class WalletApplication: Application() {
  override fun onCreate() {
    super.onCreate()

    DaggerInjector.init(this)
  }
}