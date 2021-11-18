package com.resultados.loto.lotonicaragua

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.resultados.loto.lotonicaragua.ads.AppOpenManager


class LotoApplication: Application() {
    private lateinit var appOpenManager:AppOpenManager

    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this) { }
        appOpenManager = AppOpenManager(this);
    }
}