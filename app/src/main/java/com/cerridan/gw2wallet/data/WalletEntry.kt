package com.cerridan.gw2wallet.data

import com.google.gson.annotations.SerializedName

class WalletEntry(
    @SerializedName("id") val id: Int,
    @SerializedName("value") val value: Int
)
