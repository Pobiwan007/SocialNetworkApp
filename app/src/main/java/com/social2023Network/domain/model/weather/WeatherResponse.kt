package com.social2023Network.domain.model.weather

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherResponse(
    @SerializedName("location" ) var location : Location? = Location(),
    @SerializedName("current"  ) var current  : Current?  = Current(),
    @SerializedName("forecast" ) var forecast : Forecast? = Forecast()
) : Parcelable

@Parcelize
data class Location (

    @SerializedName("name"            ) var name           : String? = null,
    @SerializedName("region"          ) var region         : String? = null,
    @SerializedName("country"         ) var country        : String? = null,
    @SerializedName("lat"             ) var lat            : Double? = null,
    @SerializedName("lon"             ) var lon            : Double? = null,
    @SerializedName("tz_id"           ) var tzId           : String? = null,
    @SerializedName("localtime_epoch" ) var localtimeEpoch : Int?    = null,
    @SerializedName("localtime"       ) var localtime      : String? = null

) : Parcelable

@Parcelize
data class Condition (

    @SerializedName("text" ) var text : String? = null,
    @SerializedName("icon" ) var icon : String? = null,
    @SerializedName("code" ) var code : Int?    = null

) : Parcelable

@Parcelize
data class Current (

    @SerializedName("last_updated_epoch" ) var lastUpdatedEpoch : String?       = null,
    @SerializedName("last_updated"       ) var lastUpdated      : String?    = null,
    @SerializedName("temp_c"             ) var tempC            : String?       = null,
    @SerializedName("temp_f"             ) var tempF            : Double?    = null,
    @SerializedName("is_day"             ) var isDay            : String?       = null,
    @SerializedName("condition"          ) var condition        : Condition? = Condition(),
    @SerializedName("wind_mph"           ) var windMph          : Double?    = null,
    @SerializedName("wind_kph"           ) var windKph          : String?       = null,
    @SerializedName("wind_degree"        ) var windDegree       : String?       = null,
    @SerializedName("wind_dir"           ) var windDir          : String?    = null,
    @SerializedName("pressure_mb"        ) var pressureMb       : String?       = null,
    @SerializedName("pressure_in"        ) var pressureIn       : Double?    = null,
    @SerializedName("precip_mm"          ) var precipMm         : String?    = null,
    @SerializedName("precip_in"          ) var precipIn         : String?       = null,
    @SerializedName("humidity"           ) var humidity         : String?       = null,
    @SerializedName("cloud"              ) var cloud            : String?       = null,
    @SerializedName("feelslike_c"        ) var feelslikeC       : String?       = null,
    @SerializedName("feelslike_f"        ) var feelslikeF       : Double?    = null,
    @SerializedName("vis_km"             ) var visKm            : String?       = null,
    @SerializedName("vis_miles"          ) var visMiles         : String?       = null,
    @SerializedName("uv"                 ) var uv               : String?       = null,
    @SerializedName("gust_mph"           ) var gustMph          : Double?    = null,
    @SerializedName("gust_kph"           ) var gustKph          : String?       = null

) : Parcelable


@Parcelize
data class Forecastday (

    @SerializedName("date"       ) var date      : String?         = null,
    @SerializedName("date_epoch" ) var dateEpoch : Int?            = null,
    @SerializedName("day"        ) var day       : Day?            = Day(),
    @SerializedName("hour"       ) var hour      : ArrayList<Hour> = arrayListOf()

) : Parcelable

@Parcelize
data class Forecast (
    @SerializedName("forecastday" ) var forecastday : ArrayList<Forecastday> = arrayListOf()

) : Parcelable

@Parcelize
data class Hour (

    @SerializedName("time_epoch"     ) var timeEpoch    : Int?       = null,
    @SerializedName("time"           ) var time         : String?    = null,
    @SerializedName("temp_c"         ) var tempC        : Double?    = null,
    @SerializedName("temp_f"         ) var tempF        : Double?    = null,
    @SerializedName("is_day"         ) var isDay        : String?       = null,
    @SerializedName("condition"      ) var condition    : Condition? = Condition(),
    @SerializedName("wind_mph"       ) var windMph      : Double?    = null,
    @SerializedName("wind_kph"       ) var windKph      : Double?    = null,
    @SerializedName("wind_degree"    ) var windDegree   : String?       = null,
    @SerializedName("wind_dir"       ) var windDir      : String?    = null,
    @SerializedName("pressure_mb"    ) var pressureMb   : String?       = null,
    @SerializedName("pressure_in"    ) var pressureIn   : Double?    = null,
    @SerializedName("precip_mm"      ) var precipMm     : String?       = null,
    @SerializedName("precip_in"      ) var precipIn     : String?       = null,
    @SerializedName("humidity"       ) var humidity     : String?       = null,
    @SerializedName("cloud"          ) var cloud        : String?       = null,
    @SerializedName("feelslike_c"    ) var feelslikeC   : Double?    = null,
    @SerializedName("feelslike_f"    ) var feelslikeF   : Double?    = null,
    @SerializedName("windchill_c"    ) var windchillC   : Double?    = null,
    @SerializedName("windchill_f"    ) var windchillF   : Double?    = null,
    @SerializedName("heatindex_c"    ) var heatindexC   : Double?    = null,
    @SerializedName("heatindex_f"    ) var heatindexF   : Double?    = null,
    @SerializedName("dewpoint_c"     ) var dewpointC    : Double?    = null,
    @SerializedName("dewpoint_f"     ) var dewpointF    : Double?    = null,
    @SerializedName("will_it_rain"   ) var willItRain   : String?       = null,
    @SerializedName("chance_of_rain" ) var chanceOfRain : String?       = null,
    @SerializedName("will_it_snow"   ) var willItSnow   : String?       = null,
    @SerializedName("chance_of_snow" ) var chanceOfSnow : String?       = null,
    @SerializedName("vis_km"         ) var visKm        : String?       = null,
    @SerializedName("vis_miles"      ) var visMiles     : String?       = null,
    @SerializedName("gust_mph"       ) var gustMph      : Double?    = null,
    @SerializedName("gust_kph"       ) var gustKph      : Double?    = null,
    @SerializedName("uv"             ) var uv           : String?       = null

): Parcelable

@Parcelize
data class Day (

    @SerializedName("maxtemp_c"            ) var maxtempC          : Double?    = null,
    @SerializedName("maxtemp_f"            ) var maxtempF          : String?       = null,
    @SerializedName("mintemp_c"            ) var mintempC          : Double?    = null,
    @SerializedName("mintemp_f"            ) var mintempF          : String?       = null,
    @SerializedName("avgtemp_c"            ) var avgtempC          : Double?    = null,
    @SerializedName("avgtemp_f"            ) var avgtempF          : Double?    = null,
    @SerializedName("maxwind_mph"          ) var maxwindMph        : Double?    = null,
    @SerializedName("maxwind_kph"          ) var maxwindKph        : Double?    = null,
    @SerializedName("totalprecip_mm"       ) var totalprecipMm     : Double?    = null,
    @SerializedName("totalprecip_in"       ) var totalprecipIn     : Double?    = null,
    @SerializedName("totalsnow_cm"         ) var totalsnowCm       : Double?    = null,
    @SerializedName("avgvis_km"            ) var avgvisKm          : String?       = null,
    @SerializedName("avgvis_miles"         ) var avgvisMiles       : String?       = null,
    @SerializedName("avghumidity"          ) var avghumidity       : String?       = null,
    @SerializedName("daily_will_it_rain"   ) var dailyWillItRain   : String?       = null,
    @SerializedName("daily_chance_of_rain" ) var dailyChanceOfRain : String?       = null,
    @SerializedName("daily_will_it_snow"   ) var dailyWillItSnow   : String?       = null,
    @SerializedName("daily_chance_of_snow" ) var dailyChanceOfSnow : String?       = null,
    @SerializedName("condition"            ) var condition         : Condition? = Condition(),
    @SerializedName("uv"                   ) var uv                : String?       = null

): Parcelable