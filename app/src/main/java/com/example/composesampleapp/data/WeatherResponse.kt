package com.example.composesampleapp.data

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.composesampleapp.R
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Response OverView
 */
@Serializable
data class WeatherResponse(
	val publicTimeFormatted : String,
	val title : String,
	val description : WeatherDescription,
	val forecasts: List<WeatherForecasts>,
	val location : WeatherLocation
)


//---------------------------------------------------------//
//予報概要
@Serializable
data class WeatherDescription(
	@SerialName(value = "bodyText") val descriptionBodyText : String
)

//予報内容
@Serializable
data class WeatherForecasts(
	val date : String,
	val dateLabel : String,
	@SerialName(value ="telop") val weatherLabel : String,
	@SerialName(value ="detail") val forecastDetail : WeatherForecastDetail,
	val temperature : WeatherTemperature,
	val chanceOfRain: ChanceOfRain,
	@SerialName(value ="image") val iconImage: WeatherIconImage
)

//予報の概要
@Serializable
data class WeatherForecastDetail(
	val weather : String? = null,
	val wind : String? = null,
	val wave : String? = null
)

//気温MAX-MIN
@Serializable
data class WeatherTemperature(
	val min : TemperatureCelsius,
	val max : TemperatureCelsius
)

//気温（摂氏）
@Serializable
data class TemperatureCelsius(
	@SerialName("celsius") val celsius: String? = null
)

//降水確率
@Serializable
data class ChanceOfRain(
	//0時から6時
	@SerialName(value ="T00_06") val lateNight : String,
	@SerialName(value ="T06_12") val morning : String,
	@SerialName(value ="T12_18") val afternoon : String,
	@SerialName(value ="T18_24") val night : String
)


@Serializable
data class WeatherIconImage(
	val title : String,
	val url : String,
	val width : Int,
	val height : Int,
)

//地理情報
@Serializable
data class WeatherLocation(
	val area : String,
	val prefecture : String,
	val district : String,
	val city : String
)