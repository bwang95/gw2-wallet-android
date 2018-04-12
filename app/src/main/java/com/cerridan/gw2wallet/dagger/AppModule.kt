package com.cerridan.gw2wallet.dagger

import android.app.Application
import com.cerridan.gw2wallet.R
import com.cerridan.gw2wallet.api.APIKeyInterceptor
import com.cerridan.gw2wallet.api.GW2API
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule(app: Application) {
  companion object {
    const val API_URL = "https://api.guildwars2.com"
  }

  private val API_KEY = app.getString(R.string.api_key)

  @Provides @Singleton fun provideGson() = GsonBuilder().create()

  @Provides @Singleton fun provideOkHttpClient() = OkHttpClient.Builder()
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