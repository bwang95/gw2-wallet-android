package com.cerridan.gw2wallet.api

import com.cerridan.gw2wallet.api.request.QueryArray
import com.cerridan.gw2wallet.data.Currency
import com.cerridan.gw2wallet.data.GW2Character
import com.cerridan.gw2wallet.data.WalletEntry
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GW2API {
  @GET("/v2/currencies")
  fun getCurrencies(@Query("ids") ids: QueryArray<Int>): Observable<List<Currency>>

  @GET("/v2/account/wallet")
  fun getWallet(): Observable<List<WalletEntry>>

  @GET("/v2/characters")
  fun getCharacterNames(): Observable<List<String>>

  @GET("/v2/characters/{name}/core")
  fun getBasicCharacterInfo(@Path("name") name: String): Observable<GW2Character.BasicInfo>
}
