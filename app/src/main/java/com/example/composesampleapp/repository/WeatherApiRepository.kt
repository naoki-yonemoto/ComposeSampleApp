package com.example.workshopsample1.repository

import com.example.workshopsample1.api.WeatherApiService
import com.example.composesampleapp.data.WeatherResponse
import com.example.composesampleapp.utils.ApiResultOf
import com.example.composesampleapp.utils.safeApiCall
import javax.inject.Inject

class WeatherApiRepository
@Inject constructor(
	private val apiService : WeatherApiService
) {
	
	suspend fun getWeatherInformation(cityCode: String) : ApiResultOf<WeatherResponse> {
		return safeApiCall(apiService.getWeatherInformation(cityCode))
	}
	
}

