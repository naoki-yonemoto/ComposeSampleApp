package com.example.composesampleapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.composesampleapp.R.drawable
import com.example.composesampleapp.R.string
import com.example.composesampleapp.color.ColorMidBlue
import com.example.composesampleapp.color.ColorMidOrange
import com.example.composesampleapp.color.ColorProgress
import com.example.composesampleapp.color.ColorSkyBlue
import com.example.composesampleapp.data.ChanceOfRain
import com.example.composesampleapp.data.CityCode
import com.example.composesampleapp.data.WeatherForecasts
import com.example.composesampleapp.data.WeatherIconImage
import com.example.composesampleapp.data.WeatherResponse
import com.example.composesampleapp.data.WeatherTemperature
import com.example.composesampleapp.ui.component.BlackLineBorder
import com.example.composesampleapp.ui.component.LoadingScreen
import com.example.composesampleapp.ui.component.StandardColorText
import com.example.composesampleapp.ui.component.StandardText
import com.example.composesampleapp.ui.state.UiState
import com.example.composesampleapp.viwemodel.MainViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

@Composable
fun MainScreen(
    vm: MainViewModel = viewModel()
) {

    Scaffold { padding ->
        MainScreenOfState(
            modifier = Modifier
                .padding(padding),
            vm = vm
        )
    }
}

@Composable
private fun MainScreenOfState(
    modifier: Modifier = Modifier,
    vm: MainViewModel
) {

    LaunchedEffect(Unit) {
        //initial API Call
        //Composeされた直後にCoroutineを起動する
        vm.getWeatherInformation(CityCode.Tokyo)
    }
    val scope = rememberCoroutineScope()

    val uiState by vm.uiState.collectAsState()
    val onClickLocaleMenuSelect: (CityCode) -> Unit = {
        scope.launch {
            vm.getWeatherInformation(it)
        }
    }

    when (uiState) {
        is UiState.Loading -> {
            LoadingScreen()
        }

        is UiState.Complete -> {
            WeatherMainInfoScreen(
                modifier = modifier,
                response = uiState as UiState.Complete,
                onClickLocaleMenuSelect = onClickLocaleMenuSelect
            )
        }

        is UiState.Error -> {
            ErrorScreen()
        }

        is UiState.Init -> {}
    }
}

@Composable
private fun WeatherMainInfoScreen(
    modifier: Modifier = Modifier,
    response: UiState.Complete,
    onClickLocaleMenuSelect: (CityCode) -> Unit
) {
    val data = remember { response.data }

    LazyColumn(
        modifier = modifier
            .safeDrawingPadding()
            .background(Color.White)
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        //同じ方向にScrollするColumnにLazyColumnを入れることはできない
        item { DateHeaderText(date = data.publicTimeFormatted) }
        item { BlackLineBorder() }
        item {
            LocaleSelectHeader(
                title = data.title,
                onClickLocaleMenuSelect = onClickLocaleMenuSelect
            )
        }
        item { StandardText(text = data.description.descriptionBodyText) }

        val list = data.forecasts
        items(list.size) { index ->
            WeatherForecastsInfoContent(list[index])
        }
    }

}

@Composable
private fun SampleMethod(
    modifier: Modifier = Modifier,
    data: WeatherResponse,
    onClickLocaleMenuSelect: (CityCode) -> Unit
) {
    Column(
        modifier = modifier
            .safeDrawingPadding()
            .background(Color.White)
            .padding(8.dp)
            .wrapContentSize()
            .verticalScroll(rememberScrollState()), //縦にスクロールする
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DateHeaderText(date = data.publicTimeFormatted)
        LocaleSelectHeader(title = data.title, onClickLocaleMenuSelect = onClickLocaleMenuSelect)
        StandardText(text = data.description.descriptionBodyText)
        StandardText(text = data.description.descriptionBodyText)
    }
}

@Composable
private fun DateHeaderText(
    modifier: Modifier = Modifier,
    date: String
) {
    Text(
        text = stringResource(string.format_publication_dateTime, date),
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(bottom = 8.dp),
        fontSize = 24.sp,
        fontStyle = FontStyle.Italic,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Start,
    )
}

@Composable
private fun LocaleSelectHeader(
    modifier: Modifier = Modifier,
    title: String,
    onClickLocaleMenuSelect: (CityCode) -> Unit
) {
    var isDropDownExpanded by remember { mutableStateOf(false) }
    val menuList = listOf(CityCode.Tokyo, CityCode.Nagoya, CityCode.Osaka)

    Row(
        modifier = modifier
            .padding(top = 8.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            modifier = modifier.fillMaxSize().weight(1f),
            text = title,
            fontSize = 16.sp
        )

        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .clickable {
                    isDropDownExpanded = true
                }
        ) {
            Text(
                modifier = modifier.wrapContentWidth(),
                fontSize = 16.sp,
                color = ColorMidBlue,
                text = stringResource(string.select_other_locale)
            )

            Image(
                painter = painterResource(drawable.baseline_arrow_drop_down_24),
                contentDescription = "DropDown Icon"
            )

            DropdownMenu(
                modifier = Modifier.wrapContentSize(),
                scrollState = rememberScrollState(),
                offset = DpOffset(48. dp, 0.dp),
                expanded = isDropDownExpanded,
                onDismissRequest = { isDropDownExpanded = false }
            ) {
                menuList.forEach { cityCode ->
                    DropdownMenuItem(
                        onClick = {
                            isDropDownExpanded = false
                            onClickLocaleMenuSelect.invoke(cityCode)
                        }
                    ) {
                        Text(cityCode.getCityName())
                    }
                }
            }

        }
    }
}

@Composable
private fun WeatherForecastsInfoContent(item: WeatherForecasts) {
    Column(
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        BlackLineBorder()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            StandardText(
                text = stringResource(string.format_date_label, item.dateLabel),
            )

            WeatherForecastLabel(
                iconImage = item.iconImage,
                weatherLabel = item.weatherLabel
            )

            WeatherForecastTemperatureContent(
                temperature = item.temperature
            )

            WeatherForecastChanceOfRainContent(
                chanceOfRain = item.chanceOfRain
            )
        }
    }
}

@Composable
private fun WeatherForecastLabel(
    modifier: Modifier = Modifier,
    iconImage: WeatherIconImage,
    weatherLabel: String
) {
    Row(
        modifier = modifier
            .wrapContentSize()
            .padding(bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = iconImage.url,
            modifier = modifier
                .height(iconImage.height.dp)
                .width(iconImage.width.dp)
                .padding(end = 8.dp),
            contentDescription = "weather_icon",
            contentScale = ContentScale.Crop
        )

        Text(
            text = weatherLabel,
            color = ColorProgress,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun WeatherForecastTemperatureContent(
    modifier: Modifier = Modifier,
    temperature: WeatherTemperature
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        StandardColorText(
            text = stringResource(
                string.format_min_temperature,
                temperature.min.celsius ?: stringResource(string.none_temp)
            ),
            color = ColorSkyBlue
        )

        StandardColorText(
            stringResource(
                string.format_max_temperature,
                temperature.max.celsius ?: stringResource(string.none_temp)
            ),
            color = ColorMidOrange
        )
    }

}


@Composable
private fun WeatherForecastChanceOfRainContent(
    modifier: Modifier = Modifier,
    chanceOfRain: ChanceOfRain
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 8.dp)
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ChanceOfRainText(
                label = stringResource(string.late_night),
                rainPerText =
                    stringResource(string.chance_of_rain, chanceOfRain.lateNight)
            )
            ChanceOfRainText(
                label = stringResource(string.morning),
                rainPerText =
                    stringResource(string.chance_of_rain, chanceOfRain.morning)
            )
        }

        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ChanceOfRainText(
                label = stringResource(string.afternoon),
                rainPerText =
                    stringResource(string.chance_of_rain, chanceOfRain.afternoon)
            )
            ChanceOfRainText(
                label = stringResource(string.night),
                rainPerText =
                    stringResource(string.chance_of_rain, chanceOfRain.night)
            )
        }
    }
}

@Composable
private fun ChanceOfRainText(
    modifier: Modifier = Modifier,
    label: String,
    rainPerText: String
) {

    Column(
        modifier = modifier
            .wrapContentSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StandardText(text = label)

        StandardText(text = rainPerText)
    }

}


@Preview
@Composable
fun PreviewMainScreen() {

    val sampleData =
        """
        {"publicTimeFormatted":"2025/04/05 05:00:00","title":"東京都 東京 の天気","description":{"bodyText":"　東日本は高気圧に覆われていますが、伊豆諸島付近は気圧の谷となっています。\n\n　東京地方は、晴れとなっています。\n\n　５日は、高気圧に覆われますが、気圧の谷や湿った空気の影響を受ける見込みです。このため、晴れで夕方から曇りとなるでしょう。伊豆諸島では、雨や雷雨となる所がある見込みです。\n\n　６日は、気圧の谷や湿った空気の影響を受ける見込みです。このため、曇りで昼過ぎから時々雨となるでしょう。伊豆諸島では、雨で雷を伴う所がある見込みです。\n\n【関東甲信地方】\n　関東甲信地方は、晴れや曇りで、雨の降っている所があります。\n\n　５日は、高気圧に覆われますが、気圧の谷や湿った空気の影響を受ける見込みです。このため、晴れや曇りで、雨の降る所があるでしょう。伊豆諸島では雷を伴う見込みです。\n\n　６日は、気圧の谷や湿った空気の影響を受ける見込みです。このため、曇りや雨で、雷を伴う所があるでしょう。\n\n　関東地方と伊豆諸島の海上では、５日から６日にかけて、うねりを伴い波がやや高いでしょう。"},"forecasts":[{"date":"2025-04-05","dateLabel":"今日","telop":"晴のち曇","detail":{"weather":"晴れ　夕方　から　くもり","wind":"北の風　後　南の風　やや強く","wave":"０．５メートル　後　１メートル"},"temperature":{"min":{},"max":{"celsius":"17"}},"chanceOfRain":{"T00_06":"--%","T06_12":"0%","T12_18":"0%","T18_24":"10%"},"image":{"title":"晴のち曇","url":"https://www.jma.go.jp/bosai/forecast/img/110.svg","width":80,"height":60}},{"date":"2025-04-06","dateLabel":"明日","telop":"曇のち時々雨","detail":{"weather":"くもり　昼過ぎ　から　時々　雨","wind":"南の風　後　やや強く","wave":"０．５メートル　後　１メートル"},"temperature":{"min":{"celsius":"11"},"max":{"celsius":"18"}},"chanceOfRain":{"T00_06":"10%","T06_12":"20%","T12_18":"50%","T18_24":"50%"},"image":{"title":"曇のち時々雨","url":"https://www.jma.go.jp/bosai/forecast/img/212.svg","width":80,"height":60}},{"date":"2025-04-07","dateLabel":"明後日","telop":"曇り","detail":{},"temperature":{"min":{"celsius":"10"},"max":{"celsius":"21"}},"chanceOfRain":{"T00_06":"30%","T06_12":"30%","T12_18":"30%","T18_24":"30%"},"image":{"title":"曇り","url":"https://www.jma.go.jp/bosai/forecast/img/200.svg","width":80,"height":60}}],"location":{"area":"関東","prefecture":"東京都","district":"東京地方","city":"東京"}}
         """.trimIndent()

    val result = Json.decodeFromString<WeatherResponse>(sampleData)


    WeatherMainInfoScreen(response = UiState.Complete(result), onClickLocaleMenuSelect = {})
}


@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun PreviewForecastsList() {
    val sampleData =
        """
        {"publicTimeFormatted":"2025/04/05 05:00:00","title":"東京都 東京 の天気","description":{"bodyText":"　東日本は高気圧に覆われていますが、伊豆諸島付近は気圧の谷となっています。\n\n　東京地方は、晴れとなっています。\n\n　５日は、高気圧に覆われますが、気圧の谷や湿った空気の影響を受ける見込みです。このため、晴れで夕方から曇りとなるでしょう。伊豆諸島では、雨や雷雨となる所がある見込みです。\n\n　６日は、気圧の谷や湿った空気の影響を受ける見込みです。このため、曇りで昼過ぎから時々雨となるでしょう。伊豆諸島では、雨で雷を伴う所がある見込みです。\n\n【関東甲信地方】\n　関東甲信地方は、晴れや曇りで、雨の降っている所があります。\n\n　５日は、高気圧に覆われますが、気圧の谷や湿った空気の影響を受ける見込みです。このため、晴れや曇りで、雨の降る所があるでしょう。伊豆諸島では雷を伴う見込みです。\n\n　６日は、気圧の谷や湿った空気の影響を受ける見込みです。このため、曇りや雨で、雷を伴う所があるでしょう。\n\n　関東地方と伊豆諸島の海上では、５日から６日にかけて、うねりを伴い波がやや高いでしょう。"},"forecasts":[{"date":"2025-04-05","dateLabel":"今日","telop":"晴のち曇","detail":{"weather":"晴れ　夕方　から　くもり","wind":"北の風　後　南の風　やや強く","wave":"０．５メートル　後　１メートル"},"temperature":{"min":{},"max":{"celsius":"17"}},"chanceOfRain":{"T00_06":"--%","T06_12":"0%","T12_18":"0%","T18_24":"10%"},"image":{"title":"晴のち曇","url":"https://www.jma.go.jp/bosai/forecast/img/110.svg","width":80,"height":60}},{"date":"2025-04-06","dateLabel":"明日","telop":"曇のち時々雨","detail":{"weather":"くもり　昼過ぎ　から　時々　雨","wind":"南の風　後　やや強く","wave":"０．５メートル　後　１メートル"},"temperature":{"min":{"celsius":"11"},"max":{"celsius":"18"}},"chanceOfRain":{"T00_06":"10%","T06_12":"20%","T12_18":"50%","T18_24":"50%"},"image":{"title":"曇のち時々雨","url":"https://www.jma.go.jp/bosai/forecast/img/212.svg","width":80,"height":60}},{"date":"2025-04-07","dateLabel":"明後日","telop":"曇り","detail":{},"temperature":{"min":{"celsius":"10"},"max":{"celsius":"21"}},"chanceOfRain":{"T00_06":"30%","T06_12":"30%","T12_18":"30%","T18_24":"30%"},"image":{"title":"曇り","url":"https://www.jma.go.jp/bosai/forecast/img/200.svg","width":80,"height":60}}],"location":{"area":"関東","prefecture":"東京都","district":"東京地方","city":"東京"}}
         """.trimIndent()

    val result = Json.decodeFromString<WeatherResponse>(sampleData)
    val list = result.forecasts[0]


    WeatherForecastsInfoContent(list)
}