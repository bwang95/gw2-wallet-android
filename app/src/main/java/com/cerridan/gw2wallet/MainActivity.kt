package com.cerridan.gw2wallet

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import com.cerridan.gw2wallet.fragment.CharacterFragment
import com.cerridan.gw2wallet.fragment.WalletFragment
import com.cerridan.gw2wallet.util.bindView

class MainActivity : AppCompatActivity() {
  private val drawerLayout: DrawerLayout by bindView(R.id.dl_main_layout)
  private val navigationView: NavigationView by bindView(R.id.nv_main_nav_drawer)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    navigationView.setCheckedItem(R.id.mi_drawer_character)
    navigationView.setNavigationItemSelectedListener { item ->
      supportFragmentManager.beginTransaction()
          .replace(R.id.fl_main_fragment_container, when (item.itemId) {
            R.id.mi_drawer_character -> CharacterFragment()
            R.id.mi_drawer_wallet -> WalletFragment()
            else -> throw IllegalArgumentException("Unrecognized menu item id $item")
          })
          .commit()
      drawerLayout.closeDrawer(navigationView, true)
      true
    }

    supportFragmentManager.beginTransaction()
        .add(R.id.fl_main_fragment_container, CharacterFragment())
        .commit()
  }
}
