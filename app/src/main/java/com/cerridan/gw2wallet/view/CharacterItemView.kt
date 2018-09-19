package com.cerridan.gw2wallet.view

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.cerridan.gw2wallet.R
import com.cerridan.gw2wallet.data.GW2Character
import com.cerridan.gw2wallet.util.bindView
import com.squareup.phrase.Phrase

class CharacterItemView(context: Context, attrs: AttributeSet): LinearLayout(context, attrs) {
  private val classIconView: ImageView by bindView(R.id.iv_character_class_icon)
  private val nameView: TextView by bindView(R.id.tv_character_name)
  private val classView: TextView by bindView(R.id.tv_character_class)
  private val guildView: TextView by bindView(R.id.tv_character_guild)

  fun bind(name: String, basicInfo: GW2Character.BasicInfo?) {
    nameView.text = name

    basicInfo?.let { info ->
      classView.text = Phrase.from(context, R.string.character_class)
          .put("level", info.level)
          .put("gender", info.gender)
          .put("race", info.race)
          .put("profession", info.profession)
          .format()
      classView.visibility = VISIBLE
    }
  }
}