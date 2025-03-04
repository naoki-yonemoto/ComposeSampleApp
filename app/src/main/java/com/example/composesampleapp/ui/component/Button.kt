package com.example.composesampleapp.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composesampleapp.color.ColorLightGray


@Composable
fun PlusButton(
    modifier: Modifier = Modifier,
    onClickEvent: () -> Unit
) {
    Button(
        onClick = onClickEvent,
        modifier = modifier.height(56.dp),
        border = BorderStroke(2.dp, Color.Black),
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = ColorLightGray
        )
    ) {
        Text(
            text = "＋",
            modifier = modifier,
            color = Color.Black,
            fontSize = 20.sp
        )
    }
}

@Composable
fun MinusButton(
    modifier: Modifier = Modifier,
    onClickEvent: () -> Unit
) {
    Button(
        onClick = onClickEvent,
        modifier = modifier.height(56.dp),
        border = BorderStroke(2.dp, Color.Black),
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = ColorLightGray
        )
    ) {
        Text(
            text = "−",
            modifier = modifier,
            color = Color.Black,
            fontSize = 20.sp
        )
    }
}

@Preview
@Composable
fun PreviewPlusButton() {
    PlusButton(onClickEvent = {})
}

@Preview
@Composable
fun PreviewMinusButton() {
    MinusButton(onClickEvent = {})
}