package com.cerridan.gw2wallet.api

import com.cerridan.gw2wallet.api.request.QueryArray
import com.cerridan.gw2wallet.data.Currency
import com.cerridan.gw2wallet.data.WalletEntry
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Brian on 10/3/2017.
 */

interface GW2API {
  @GET("/v2/currencies")
  fun getCurrencies(@Query("ids") ids: QueryArray<Int>): Observable<List<Currency>>

  @GET("/v2/account/wallet")
  fun getWallet(): Observable<List<WalletEntry>>
}
