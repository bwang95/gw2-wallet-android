package com.cerridan.gw2wallet.dagger

import android.app.Application
import com.cerridan.gw2wallet.R
import com.cerridan.gw2wallet.api.APIKeyInterceptor
import com.cerridan.gw2wallet.api.GW2API
import com.cerridan.gw2wallet.util.ISO8601DateTypeAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import javax.inject.Singleton

@Module
class AppModule(private val app: Application) {
  companion object {
    const val API_URL = "https://api.guildwars2.com"
  }

  private val API_KEY = app.getString(R.string.api_key)
  private val cacheSize = app.resources.getInteger(R.integer.cache_size_mb) * 1024 * 1024L

  @Provides @Singleton fun provideGson() =
      GsonBuilder()
          .registerTypeAdapter(Date::class.java, ISO8601DateTypeAdapter())
          .create()

  @Provides @Singleton fun provideOkHttpCache() = Cache(app.cacheDir, cacheSize)

  @Provides @Singleton fun provideOkHttpClient(cache: Cache) = OkHttpClient.Builder()
      .cache(cache)
      .addInterceptor(APIKeyInterceptor(API_KEY))
      .addInterceptor(HttpLoggingInterceptor().apply { level = BODY })
      .build()

  @Provides @Singleton fun provideRetrofit(client: OkHttpClient, gson: Gson) = Retrofit.Builder()
      .baseUrl(API_URL)
      .client(client)
      .addConverterFactory(GsonConverterFactory.create(gson))
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .build()

  @Provides @Singleton fun provideApi(retrofit: Retrofit) = retrofit.create(GW2API::class.java)
}