package com.example.jsonapp

import com.google.gson.annotations.SerializedName
import org.json.JSONObject
import java.util.*

class CurrencyDetails {
    @SerializedName("date")
    var date: String? = null

    @SerializedName("eur")
    var eur: Datum? = null

    class Datum {
        @SerializedName("usd")
        var usd: String? = null

        @SerializedName("jpy")
        var jpy: String? = null

        @SerializedName("sar")
        var sar: String? = null

        @SerializedName("egp")
        var egp: String? = null

        @SerializedName("aud")
        var aud: String? = null

        @SerializedName("cad")
        var cad: String? = null
    }
}