package com.cerridan.gw2wallet.api.request

class QueryArray<out T>(private val list: List<T>) {
  override fun toString() = list.joinToString(
      separator = ", ",
      prefix = "",
      postfix = ""
  )
}

fun <T> List<T>.toQuery() = QueryArray(this)