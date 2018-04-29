package com.cerridan.gw2wallet.fragment

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.DividerItemDecoration.VERTICAL
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ViewAnimator
import com.cerridan.gw2wallet.R
import com.cerridan.gw2wallet.api.GW2API
import com.cerridan.gw2wallet.api.request.toQuery
import com.cerridan.gw2wallet.dagger.DaggerInjector
import com.cerridan.gw2wallet.data.Currency
import com.cerridan.gw2wallet.data.WalletEntry
import com.cerridan.gw2wallet.util.bindView
import com.cerridan.gw2wallet.util.castedInflate
import com.cerridan.gw2wallet.util.displayedChildId
import com.cerridan.gw2wallet.view.CurrencyItemView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class WalletFragment: BaseFragment() {
  override val layout = R.layout.fragment_wallet

  @Inject lateinit var api: GW2API

  private val animator: ViewAnimator by bindView(R.id.va_wallet_animator)
  private val walletRecycler: RecyclerView by bindView(R.id.rv_wallet_recycler)

  private val subscriptions = CompositeDisposable()

  init { DaggerInjector.appComponent.inject(this) }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val adapter = Adapter(view.context)

    walletRecycler.adapter = adapter
    walletRecycler.layoutManager = LinearLayoutManager(view.context)
    walletRecycler.addItemDecoration(DividerItemDecoration(view.context, VERTICAL))

    subscriptions.add(api.getWallet()
        .subscribeOn(Schedulers.io())
        .flatMap { entries ->
          api.getCurrencies(entries.map(WalletEntry::id).toQuery()).map { currencies ->
            Pair(currencies.sortedBy(Currency::order), entries)
          }
        }
        .observeOn(AndroidSchedulers.mainThread())
        .firstElement()
        .doOnSubscribe { animator.displayedChildId = R.id.pb_wallet_progress }
        .doOnEvent { _, _ -> animator.displayedChildId = R.id.rv_wallet_recycler }
        .subscribe { (currencies, entries) -> adapter.setCurrencies(currencies, entries) })
  }

  class ViewHolder(val view: CurrencyItemView): RecyclerView.ViewHolder(view)

  class Adapter(val context: Context): RecyclerView.Adapter<ViewHolder>() {
    private val entries = hashMapOf<Int, WalletEntry>()
    private var currencies = mutableListOf<Currency>()

    val inflater by lazy { LayoutInflater.from(context) }

    init { setHasStableIds(true) }

    fun setCurrencies(currencies: List<Currency>, entries: List<WalletEntry>) {
      this.entries.clear()
      this.currencies.clear()

      this.entries.putAll(entries.map { it.id to it })
      this.currencies.addAll(currencies)

      notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(inflater.castedInflate(R.layout.item_main_currency, parent))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val currency = currencies[position]
      holder.view.setCurrency(currency, entries[currency.id]!!)
    }

    override fun getItemId(position: Int) = currencies[position].id.toLong()

    override fun getItemCount() = currencies.size
  }
}