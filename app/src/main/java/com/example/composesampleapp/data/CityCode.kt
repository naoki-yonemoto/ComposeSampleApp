package com.example.composesampleapp.data

sealed interface CityCode {

    fun getCode(): String
    fun getCityName(): String

    data object Tokyo : CityCode {
        private const val CODE = "130010"
        private const val NAME = "東京"
        override fun getCode(): String = CODE
        override fun getCityName(): String = NAME

    }

    data object Osaka : CityCode {
        private const val CODE = "270000"
        private const val NAME = "大阪"
        override fun getCode(): String = CODE
        override fun getCityName(): String = NAME

    }

    data object Nagoya : CityCode {
        private const val CODE = "230010"
        private const val NAME = "名古屋"
        override fun getCode(): String = CODE
        override fun getCityName(): String = NAME

    }
}