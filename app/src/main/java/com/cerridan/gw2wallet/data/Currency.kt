package com.cerridan.gw2wallet.data

import com.google.gson.annotations.SerializedName

class Currency(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("icon") val iconUrl: String,
    @SerializedName("order") val order: Int
)
