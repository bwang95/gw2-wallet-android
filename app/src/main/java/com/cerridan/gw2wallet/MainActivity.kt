package com.cerridan.gw2wallet

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import com.cerridan.gw2wallet.fragment.WalletFragment
import com.cerridan.gw2wallet.util.bindView
import io.reactivex.disposables.CompositeDisposable

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    fragmentManager.beginTransaction()
        .add(R.id.fl_main_fragment_container, WalletFragment())
        .commit()
  }
}
