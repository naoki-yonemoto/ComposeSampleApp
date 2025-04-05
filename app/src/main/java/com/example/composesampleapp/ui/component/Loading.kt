package com.example.composesampleapp.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composesampleapp.R
import com.example.composesampleapp.color.ColorLoadingBackGround
import com.example.composesampleapp.color.ColorProgress

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier.fillMaxSize().background(ColorLoadingBackGround),
        contentAlignment = Alignment.Center
    ) {
        LoadingProgress()
    }
}

@Composable
fun LoadingProgress(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .wrapContentSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.loading_text),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier.padding(bottom = 16.dp)
        )

        CircularProgressIndicator(
            modifier = Modifier
                .width(64.dp)
                .height(64.dp),
            color = ColorProgress,
        )
    }
}

@Preview(name = "loading")
@Composable
fun PreviewLoadingView() {
    LoadingScreen(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    )
}

@Preview
@Composable
fun PreviewLoadingProgress() {
    LoadingProgress()
}