package com.cerridan.gw2wallet.dagger

import com.cerridan.gw2wallet.MainActivity
import com.cerridan.gw2wallet.fragment.WalletFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
  fun inject(mainActivity: MainActivity)
  fun inject(walletFragment: WalletFragment)
}