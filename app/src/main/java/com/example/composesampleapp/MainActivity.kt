package com.example.composesampleapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composesampleapp.ui.MainScreen
import com.example.composesampleapp.viwemodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

	override fun onCreate(savedInstanceState : Bundle?) {
		super.onCreate(savedInstanceState)
		
		//ComposeのEntryPoint
		//setContentからは@Composeの関数が呼べる
		setContent {
			//実際使うとViewModelはComposeでは扱いにくかったりする...。
			val viewModel : MainViewModel = viewModel()
			MainScreen(vm = viewModel)
		}
	}
}