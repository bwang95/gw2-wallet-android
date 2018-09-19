package com.cerridan.gw2wallet.fragment

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ViewAnimator
import com.cerridan.gw2wallet.R
import com.cerridan.gw2wallet.api.GW2API
import com.cerridan.gw2wallet.dagger.DaggerInjector
import com.cerridan.gw2wallet.data.GW2Character
import com.cerridan.gw2wallet.util.bindView
import com.cerridan.gw2wallet.util.castedInflate
import com.cerridan.gw2wallet.util.displayedChildId
import com.cerridan.gw2wallet.view.CharacterItemView
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CharacterFragment: BaseFragment(R.layout.fragment_character) {
  @Inject lateinit var api: GW2API

  private val animator: ViewAnimator by bindView(R.id.va_character_animator)
  private val recycler: RecyclerView by bindView(R.id.rv_character_recycler)

  init { DaggerInjector.appComponent.inject(this) }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val adapter = Adapter(view.context)
    recycler.layoutManager = LinearLayoutManager(view.context)
    recycler.adapter = adapter

    val namesObservable = api.getCharacterNames()
        .subscribeOn(Schedulers.io())
        .replay()

    namesObservable
        .observeOn(mainThread())
        .doOnSubscribe { animator.displayedChildId = R.id.pb_character_progress }
        .doOnNext { animator.displayedChildId = R.id.rv_character_recycler }
        .subscribe(adapter::setNames)
        .unsubscribeOnDestroy()

    namesObservable
        .flatMapIterable { it }
        .flatMap { name ->
          api.getBasicCharacterInfo(name)
              .take(1)
        }
        .observeOn(mainThread())
        .subscribe(adapter::updateBasicInfo)
        .unsubscribeOnDestroy()

    namesObservable
        .connect()
        .unsubscribeOnDestroy()
  }

  class ViewHolder(val view: CharacterItemView): RecyclerView.ViewHolder(view)

  class Adapter(context: Context): RecyclerView.Adapter<ViewHolder>() {
    private val names = mutableListOf<String>()
    private val data = mutableMapOf<String, GW2Character.BasicInfo>()
    private val inflater by lazy { LayoutInflater.from(context) }

    init { setHasStableIds(true) }

    fun setNames(names: List<String>) {
      this.names.clear()
      this.names.addAll(names)
      notifyDataSetChanged()
    }

    fun updateBasicInfo(basicInfo: GW2Character.BasicInfo) {
      data[basicInfo.name] = basicInfo
      notifyItemChanged(names.indexOfFirst { it == basicInfo.name })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(inflater.castedInflate(R.layout.item_character, parent))

    override fun getItemId(position: Int) = names[position].hashCode().toLong()

    override fun getItemCount() = names.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.view.bind(names[position], data[names[position]])
  }
}