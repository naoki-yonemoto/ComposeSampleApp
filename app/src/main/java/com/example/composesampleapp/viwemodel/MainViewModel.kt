package com.example.composesampleapp.viwemodel

import androidx.lifecycle.ViewModel
import com.example.composesampleapp.data.CityCode
import com.example.composesampleapp.ui.state.UiState
import com.example.composesampleapp.utils.ApiResultOf
import com.example.workshopsample1.repository.WeatherApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class MainViewModel
@Inject constructor(
    private val repository: WeatherApiRepository
) : ViewModel() {

    //(Shared)Flowを使って値を監視させる(observerパターン)
    //同時に状態（画面のローディング中などを連動して管理するとスマート(かも））
    private var _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Init)
    val uiState = _uiState.asStateFlow()

    private var weatherPoint: CityCode = CityCode.Tokyo

    //天気情報の取得 & ローディング中の状態の管理
    suspend fun getWeatherInformation(cityCode: CityCode) {
        _uiState.emit(UiState.Loading)
        weatherPoint = cityCode
        when(val result = repository.getWeatherInformation(cityCode.getCode())){
            is ApiResultOf.Success -> {
                _uiState.emit(UiState.Complete(result.value))
            }

            is ApiResultOf.Failure -> {
                _uiState.emit(UiState.Error)
            }
        }
    }
}