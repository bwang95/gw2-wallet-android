package com.cerridan.gw2wallet.view

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.cerridan.gw2wallet.R
import com.cerridan.gw2wallet.data.Currency
import com.cerridan.gw2wallet.data.WalletEntry
import com.cerridan.gw2wallet.util.bindView
import com.squareup.picasso.Picasso
import java.util.Locale

class CurrencyItemView(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {
  private val iconView: ImageView by bindView(R.id.iv_currency_icon)
  private val nameView: TextView by bindView(R.id.tv_currency_name)
  private val quantityView: TextView by bindView(R.id.tv_currency_qty)

  fun setCurrency(currency: Currency, entry: WalletEntry) {
    Picasso.with(context).load(currency.iconUrl).into(iconView)
    nameView.text = currency.name
    quantityView.text = String.format(Locale.US, "%,d", entry.value)
  }
}
