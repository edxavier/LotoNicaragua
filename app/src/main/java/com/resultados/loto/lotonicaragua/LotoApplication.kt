package com.resultados.loto.lotonicaragua

import android.app.Application
import com.google.android.libraries.ads.mobile.sdk.MobileAds
import com.google.android.libraries.ads.mobile.sdk.initialization.InitializationConfig
import com.resultados.loto.lotonicaragua.ads.AppOpenManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LotoApplication: Application() {
    private lateinit var appOpenManager:AppOpenManager

    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.IO).launch {
            val config = InitializationConfig.Builder("ca-app-pub-9964109306515647~2745407984").build()
            MobileAds.initialize(this@LotoApplication, config) { }
        }
        appOpenManager = AppOpenManager(this);
    }
}