package com.cerridan.gw2wallet.data

import com.google.gson.annotations.SerializedName
import java.util.*

class GW2Character {
  class BasicInfo(
    @SerializedName("name") val name: String,
    @SerializedName("race") val race: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("profession") val profession: String,
    @SerializedName("level") val level: Int,
    @SerializedName("guild") val guildId: String,
    @SerializedName("age") val playedSeconds: String,
    @SerializedName("created") val createdAt: Date,
    @SerializedName("deaths") val deaths: Int,
    @SerializedName("title") val titleId: Int
  )
}