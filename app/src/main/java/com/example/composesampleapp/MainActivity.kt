package com.example.composesampleapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.composesampleapp.ui.MainScreen
import com.example.composesampleapp.viwemodel.MainViewModel

class MainActivity : ComponentActivity() {
	
	private val viewModel by lazy { MainViewModel() }
	
	override fun onCreate(savedInstanceState : Bundle?) {
		super.onCreate(savedInstanceState)
		
		//ComposeのEntryPoint
		//setContentからは@Composeの関数が呼べる
		setContent {
			MainScreen(vm = viewModel)
		}
	}
}