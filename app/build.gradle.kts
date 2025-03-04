plugins {
	alias(libs.plugins.android.application)
	alias(libs.plugins.kotlin.android)
	alias(libs.plugins.hilt.android)
	alias(libs.plugins.ksp.android)
}

android {
	namespace = "com.example.composesampleapp"
	compileSdk = 35
	
	defaultConfig {
		applicationId = "com.example.composesampleapp"
		minSdk = 33
		targetSdk = 35
		versionCode = 1
		versionName = "1.0"
		
		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}
	
	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
		}
	}
	java {
		toolchain {
			languageVersion = JavaLanguageVersion.of(17)
		}
	}
	
	kotlinOptions {
		jvmTarget = JavaVersion.VERSION_17.toString()
	}
	
	
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}
	
	composeOptions {
		kotlinCompilerExtensionVersion = "1.5.14"
	}

	buildFeatures{
		buildConfig = true
		//Composeを有効化する
		compose = true
	}
}

dependencies {
	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.appcompat)
	implementation(libs.androidx.material)
	//kotlinx
	implementation(libs.kotlinx.coroutines.android)
	
	//hilt
	implementation(libs.hilt.android)
	ksp(libs.hilt.compiler)
	
	val composeBom = platform(libs.androidx.compose.bom)
	
	//Compose Bomで入れておく
	implementation(libs.androidx.activity.compose)
	implementation(composeBom)
	implementation(libs.bundles.compose)
	//ViewModelを使う予定なので入れておく
	implementation(libs.androidx.lifecycle.viewmodel.compose)
	
	
	testImplementation(libs.junit)
	testImplementation(composeBom)
	androidTestImplementation(composeBom)
	testImplementation(composeBom)
	debugImplementation(libs.bundles.composeDebug)
	androidTestImplementation(libs.androidx.junit)
	androidTestImplementation(libs.androidx.espresso.core)
	
	
	
}