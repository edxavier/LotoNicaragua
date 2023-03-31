package com.resultados.loto.lotonicaragua

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.ads.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.resultados.loto.lotonicaragua.databinding.ActivityMainBinding
import com.resultados.loto.lotonicaragua.ui.DestinoCompartirApp
import com.resultados.loto.lotonicaragua.ui.DestinoValorarApp

class MainActivity : ScopeActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_main)

        //val toolbar: Toolbar = findViewById(R.id.toolbar)
        getRemoteConfig()
        setSupportActionBar(binding.appBarMain.toolbar)

        try{ configurarBanner() }catch (e:Exception){}

        //val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        //val navView: NavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        navController.navigatorProvider.addNavigator(DestinoCompartirApp(this))
        navController.navigatorProvider.addNavigator(DestinoValorarApp(this))

        val inflater = navController.navInflater
        navController.graph  = inflater.inflate(R.navigation.mobile_navigation)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
            R.id.nav_tools, R.id.nav_share, R.id.nav_send), binding.drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

    }

    private fun getRemoteConfig() {
        try {
            val remoteConfig = Firebase.remoteConfig
            val configSettings = remoteConfigSettings { minimumFetchIntervalInSeconds = 60 }
            remoteConfig.setConfigSettingsAsync(configSettings)
            remoteConfig.setDefaultsAsync(
                mapOf("loto_url" to "http://64.225.47.75:7000/")
            )
            //mapOf("loto_url" to "https://www.loto.com.ni/")
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

        val adRequest = AdRequest.Builder()
            .build()

        val adView =  AdView(this)
        //val adViewContainer: FrameLayout = findViewById(R.id.adViewContainer)
        binding.appBarMain.contentMain.adViewContainer.addView(adView)

        adView.setHidden()
        adView.setAdSize(getAdSize())
        adView.adUnitId = getString(R.string.ads_banner)

        adView.loadAd(adRequest)
        adView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                super.onAdLoaded()
                adView.setVisible()
            }
        }
        //nav_view.menu.findItem(R.id.destino_ocultar_publicidad).isVisible = false
    }

    private fun getAdSize(): AdSize {
        //Determine the screen width to use for the ad width.
        val display = windowManager.defaultDisplay
        val outMetrics = DisplayMetrics()
        display.getMetrics(outMetrics)
        val widthPixels = outMetrics.widthPixels.toFloat()
        val density = outMetrics.density

        //you can also pass your selected width here in dp
        val adWidth = (widthPixels / density).toInt()

        //return the optimal size depends on your orientation (landscape or portrait)
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth)
    }
}
