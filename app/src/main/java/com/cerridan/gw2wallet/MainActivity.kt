package com.cerridan.gw2wallet

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import com.cerridan.gw2wallet.api.GW2API
import com.cerridan.gw2wallet.api.request.toQuery
import com.cerridan.gw2wallet.dagger.DaggerInjector
import com.cerridan.gw2wallet.data.Currency
import com.cerridan.gw2wallet.data.WalletEntry
import com.cerridan.gw2wallet.util.bindView
import com.cerridan.gw2wallet.view.CurrencyItemView
import io.reactivex.Observable
import io.reactivex.Observable.combineLatest
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers.io
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
  private val walletView: ListView by bindView(R.id.lv_main_wallet)

  @Inject lateinit var api: GW2API

  private val subscriptions = CompositeDisposable()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    DaggerInjector.appComponent.inject(this)

    val adapter = Adapter(this)
    walletView.adapter = adapter

    subscriptions.add(api.getWallet()
        .flatMap { entries ->
          combineLatest(
              Observable.just<List<WalletEntry>>(entries),
              api.getCurrencies(entries.map(WalletEntry::id).toQuery()).map { currencies ->
                currencies.sortedBy(Currency::order)
              },
              BiFunction<List<WalletEntry>, List<Currency>, Pair<List<WalletEntry>, List<Currency>>>(::Pair)
          )
        }
        .subscribeOn(io())
        .observeOn(mainThread())
        .subscribe { (entries, currencies) -> adapter.setCurrencies(currencies, entries) })
  }

  class Adapter(private val context: Context) : BaseAdapter() {
    private val entries = hashMapOf<Int, WalletEntry>()
    private var currencies = mutableListOf<Currency>()

    private val inflater by lazy { LayoutInflater.from(context) }

    fun setCurrencies(currencies: List<Currency>, entries: List<WalletEntry>) {
      this.entries.clear()
      this.currencies.clear()

      this.entries.putAll(entries.map { it.id to it })
      this.currencies.addAll(currencies)

      notifyDataSetChanged()
    }

    override fun getCount() = currencies.size

    override fun getItem(position: Int) = currencies[position]

    override fun getItemId(position: Int) = currencies[position].id.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
      val view = (convertView as? CurrencyItemView)
          ?: inflater.inflate(R.layout.item_main_currency, parent, false) as CurrencyItemView
      val currency = currencies[position]
      view.setCurrency(currency, entries[currency.id]!!)
      return view
    }
  }
}
