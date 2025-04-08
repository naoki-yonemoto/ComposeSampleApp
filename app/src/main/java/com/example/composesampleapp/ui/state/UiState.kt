package com.example.composesampleapp.ui.state

import com.example.composesampleapp.data.WeatherResponse

sealed interface MainScreenUiState {
    data object Init: MainScreenUiState
    data object Loading : MainScreenUiState
    data object Error : MainScreenUiState //エラーを詳細に把握したい場合はThrowableをもたせたり
    data class Complete(val data: WeatherResponse) : MainScreenUiState
}