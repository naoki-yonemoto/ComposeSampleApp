package com.example.composesampleapp.ui.state

import com.example.composesampleapp.data.WeatherResponse

sealed interface UiState {
    data object Init: UiState
    data object Loading : UiState
    data object Error : UiState //エラーを詳細に把握したい場合はThrowableをもたせたり
    data class Complete(val data: WeatherResponse) : UiState
}