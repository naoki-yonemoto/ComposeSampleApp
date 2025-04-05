package com.example.composesampleapp.ui.component

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun StandardText(text: String){
    Text(
        text = text,
        fontSize = 16.sp
    )
}

@Composable
fun StandardColorText(text: String,
                      color: Color
){
    Text(
        text = text,
        fontSize = 16.sp,
        color = color
    )
}