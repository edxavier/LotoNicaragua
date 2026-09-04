package com.resultados.loto.lotonicaragua.ads

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.google.android.libraries.ads.mobile.sdk.appopen.AppOpenAd
import com.google.android.libraries.ads.mobile.sdk.appopen.AppOpenAdEventCallback
import com.google.android.libraries.ads.mobile.sdk.common.AdRequest
import com.google.android.libraries.ads.mobile.sdk.common.AdLoadCallback
import com.google.android.libraries.ads.mobile.sdk.common.LoadAdError
import com.google.android.libraries.ads.mobile.sdk.common.FullScreenContentError
import com.resultados.loto.lotonicaragua.LotoApplication
import java.util.*


class AppOpenManager(myApplication: LotoApplication):LifecycleObserver, Application.ActivityLifecycleCallbacks {
    private var appOpenAd: AppOpenAd? = null
    private val myApplication: LotoApplication = myApplication
    private var isShowingAd = false
    private var currentActivity: Activity? = null
    private var loadTime: Long = 0
    private var isLoadingAd = false

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        showAdIfAvailable()
        Log.d(LOG_TAG, "onStart")
    }

    init {
        myApplication.registerActivityLifecycleCallbacks(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this);
    }

    /** Request an ad  */
    fun fetchAd() {
        Log.d(LOG_TAG, "Fetch ad.")
        if(isAdAvailable || isLoadingAd)
            return
        
        isLoadingAd = true
        
        val request = AdRequest.Builder(AD_UNIT_ID).build()
        AppOpenAd.load(request, object : AdLoadCallback<AppOpenAd> {
            override fun onAdLoaded(ad: AppOpenAd) {
                appOpenAd = ad
                loadTime = Date().time
                isLoadingAd = false
                Log.d(LOG_TAG, "Ad loaded.")
            }
            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                isLoadingAd = false
                Log.d(LOG_TAG, "Ad failed to load: ${loadAdError.message}")
            }
        })
    }

    /** Utility method that checks if ad exists and can be shown.  */
    private val isAdAvailable: Boolean
        get() =  appOpenAd != null && wasLoadTimeLessThanNHoursAgo(4);

    /** Utility method to check if ad was loaded more than n hours ago.  */
    private fun wasLoadTimeLessThanNHoursAgo(numHours: Long): Boolean {
        val dateDifference = Date().time - loadTime
        val numMilliSecondsPerHour: Long = 3600000
        return dateDifference < numMilliSecondsPerHour * numHours
    }

    companion object {
        private const val LOG_TAG = "AppOpenManager"
        private const val AD_UNIT_ID = "ca-app-pub-9964109306515647/2704920974"
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

    override fun onActivityStarted(activity: Activity) {
        currentActivity = activity;
    }

    override fun onActivityResumed(activity: Activity) {
        currentActivity = activity;
    }

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityStopped(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    override fun onActivityDestroyed(activity: Activity) {
        currentActivity = null
    }

    /** Shows the ad if one isn't already showing.  */
    private fun showAdIfAvailable() {
        // Only show ad if there is not already an app open ad currently showing
        // and an ad is available.
        if (!isShowingAd && isAdAvailable) {
            //Log.d(LOG_TAG, "Will show ad.")
            val eventCallback: AppOpenAdEventCallback =
                object : AppOpenAdEventCallback {
                    override fun onAdDismissedFullScreenContent() {
                        // Set the reference to null so isAdAvailable() returns false.
                        appOpenAd = null
                        isShowingAd = false
                        fetchAd()
                    }

                    override fun onAdFailedToShowFullScreenContent(error: FullScreenContentError) {
                        appOpenAd = null
                        isShowingAd = false
                        fetchAd()
                    }
                    override fun onAdShowedFullScreenContent() {
                        isShowingAd = true
                    }
                }
            appOpenAd?.adEventCallback = eventCallback
            currentActivity?.let { appOpenAd?.show(it) }
        } else {
            //Log.d(LOG_TAG, "Can not show ad.")
            fetchAd()
        }
    }
}
