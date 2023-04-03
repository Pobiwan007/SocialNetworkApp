package com.social2023Network.domain.model.countries

import com.google.gson.annotations.SerializedName

data class CountriesResponse (
    @SerializedName("name"         ) var name         : Name?             = Name(),
    @SerializedName("idd"          ) var idd          : Idd?              = Idd(),
    @SerializedName("flags"        ) var flags        : Flags?            = Flags()
)

data class Name(
    @SerializedName("official"   ) var official   : String?     = null
)

data class Idd(
    @SerializedName("root"     ) var root     : String?           = null,
    @SerializedName("suffixes" ) var suffixes : ArrayList<String> = arrayListOf()
)

data class Flags(
    @SerializedName("png" ) var png : String? = null,
)