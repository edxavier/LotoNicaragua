package com.resultados.loto.lotonicaragua

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAd
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAdRequest
import com.google.android.libraries.ads.mobile.sdk.banner.AdSize
import com.google.android.libraries.ads.mobile.sdk.common.AdLoadCallback
import com.google.android.libraries.ads.mobile.sdk.common.LoadAdError
import com.google.android.libraries.ads.mobile.sdk.MobileAds
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.google.firebase.remoteconfig.remoteConfig
import com.resultados.loto.lotonicaragua.databinding.ActivityMainBinding
import com.resultados.loto.lotonicaragua.ui.DestinoCompartirApp
import com.resultados.loto.lotonicaragua.ui.DestinoValorarApp

class MainActivity : ScopeActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private var bannerAd: BannerAd? = null

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

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
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


    override fun onDestroy() {
        bannerAd?.destroy()
        super.onDestroy()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun configurarBanner() {
        val adSize = getAdSize()
        val adUnitId = getString(R.string.ads_banner)

        val adRequest = BannerAdRequest.Builder(adUnitId, adSize).build()

        BannerAd.load(adRequest, object : AdLoadCallback<BannerAd> {
            override fun onAdLoaded(ad: BannerAd) {
                runOnUiThread {
                    bannerAd?.destroy()
                    bannerAd = ad
                    binding.appBarMain.contentMain.adViewContainer.removeAllViews()
                    binding.appBarMain.contentMain.adViewContainer.addView(ad.getView(this@MainActivity))
                }
            }

            override fun onAdFailedToLoad(error: LoadAdError) {
                // Handle error
            }
        })
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
