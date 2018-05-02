package com.cerridan.gw2wallet.util

import java.util.*

object Formatter {
  fun formatCoins(coppers: Int): String {
    class Money(coppers: Int) {
      val gold = coppers / 10000
      val silver = (coppers / 100) % 100
      val copper = coppers % 100
    }

    val money = Money(coppers)
    return (if (money.gold <= 0) "" else String.format(Locale.US, "%,dg ", money.gold)) +
           (if (money.silver <= 0) "" else "${money.silver}s ") +
           "${money.copper}c"
  }
}