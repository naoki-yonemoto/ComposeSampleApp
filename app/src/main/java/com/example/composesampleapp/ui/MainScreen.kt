package com.example.composesampleapp.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composesampleapp.color.ColorLightBlue
import com.example.composesampleapp.color.ColorLightCoral
import com.example.composesampleapp.color.ColorLightGreen
import com.example.composesampleapp.color.ColorLightPink
import com.example.composesampleapp.color.ColorTextGray
import com.example.composesampleapp.ui.component.MinusButton
import com.example.composesampleapp.ui.component.PlusButton
import com.example.composesampleapp.viwemodel.MainViewModel

private const val TAG: String = "MainScreen"

@Composable
fun MainScreen(
    modifier: Modifier = Modifier, vm: MainViewModel
) {
    Scaffold(modifier = modifier, topBar = { TopAppBar() }) { padding ->
        BaseView(
            Modifier
                .padding(padding)
                .fillMaxSize()
        )
    }
}

@Composable
private fun TopAppBar() {
    TopAppBar(
        title = { Text("Sample Compose") },
        backgroundColor = ColorLightBlue,
        contentColor = ColorTextGray
    )
}

@Composable
private fun BaseView(modifier: Modifier = Modifier) {
    // UIの更新などでこのComposable関数がRecomposeが起こった場合、普通の変数は初期化されてしまう
    var count = 0
    //= remember{}はReComposeする場合値を保持してくれるが、State状態ではない場合Composable関数は変更を検知してくれない
    var countRememberD = remember { 0 }
    //UIに追従するような変数にしたい場合はState<T>型で宣言してby remember{State<T>}のようにしてComposable関数に認識させないといけない
    var countRemember by remember { mutableIntStateOf(0) }


    Column(
        modifier = modifier
            .padding(16.dp)
            .background(ColorLightPink),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TextLabel(count = countRemember)

        Row(
            modifier = modifier.background(ColorLightCoral),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PlusButton { countRemember++ }
            MinusButton { countRemember-- }
        }

    }
}

@Composable
fun TextLabel(
    modifier: Modifier = Modifier, count: Int
) {

    //↓Composeされると発火するLambda関数
    SideEffect { Log.d("$TAG: TextLabel", "composed / Recomposed") }

    Text(
        text = count.toString(),
        modifier = modifier
            .background(color = ColorLightGreen)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        fontSize = 52.sp,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic
    )
}


@Preview(name = "Main")
@Composable
fun PreviewMainScreen() {
    MainScreen(vm = MainViewModel())
}

@Preview(name = "TextLabel")
@Composable
fun PreviewTextLabel() {
    TextLabel(count = 100)
}