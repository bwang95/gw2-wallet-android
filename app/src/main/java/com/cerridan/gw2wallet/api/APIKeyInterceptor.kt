package com.cerridan.gw2wallet.api

import okhttp3.Interceptor
import okhttp3.Response

class APIKeyInterceptor(private val apiKey: String) : Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response {
    val request = chain.request()
    val builder = request.newBuilder()
    when (request.method()) {
      "POST" -> builder.addHeader("Authorization", "Bearer $apiKey")
      else -> {
        builder.url(
            request.url()
                .newBuilder()
                .addQueryParameter("access_token", apiKey)
                .build()
        )
      }
    }
    return chain.proceed(builder.build())
  }
}