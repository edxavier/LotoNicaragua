package com.resultados.loto.lotonicaragua

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.ads.*
import com.google.android.material.navigation.NavigationView
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.resultados.loto.lotonicaragua.ui.DestinoCompartirApp
import com.resultados.loto.lotonicaragua.ui.DestinoValorarApp

class MainActivity : ScopeActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        getRemoteConfig()
        setSupportActionBar(toolbar)
        configurarBanner()

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        navController.navigatorProvider.addNavigator(DestinoCompartirApp(this))
        navController.navigatorProvider.addNavigator(DestinoValorarApp(this))

        val inflater = navController.navInflater
        navController.graph  = inflater.inflate(R.navigation.mobile_navigation)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
            R.id.nav_tools, R.id.nav_share, R.id.nav_send), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun getRemoteConfig() {
        try {
            val remoteConfig = Firebase.remoteConfig
            val configSettings = remoteConfigSettings { minimumFetchIntervalInSeconds = 60 }
            remoteConfig.setConfigSettingsAsync(configSettings)
            remoteConfig.setDefaultsAsync(
                mapOf("loto_url" to "https://www.loto.com.ni/")
            )
            remoteConfig.getString("cargos_fijos")
            remoteConfig.fetchAndActivate().addOnCompleteListener { }
        }catch (e:Exception){}
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun configurarBanner() {
        val requestConfig = RequestConfiguration.Builder()
            .setTestDeviceIds(arrayOf(
                "AC5F34885B0FE7EF03A409EB12A0F949",
                AdRequest.DEVICE_ID_EMULATOR
            ).toList())
            .build()
        MobileAds.setRequestConfiguration(requestConfig)

        val adView:AdView = findViewById(R.id.adView)
        adView.visibility = View.GONE
        val adRequest = AdRequest.Builder()
            .build()
        adView.loadAd(adRequest)
        adView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                super.onAdLoaded()
                adView.visibility = View.VISIBLE
            }
        }
        //nav_view.menu.findItem(R.id.destino_ocultar_publicidad).isVisible = false
    }
}
