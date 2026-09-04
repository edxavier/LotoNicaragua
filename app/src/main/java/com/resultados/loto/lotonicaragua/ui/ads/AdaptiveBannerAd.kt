package com.resultados.loto.lotonicaragua.ui.ads

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.widget.FrameLayout
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.libraries.ads.mobile.sdk.banner.AdSize
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAd
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAdRequest
import com.google.android.libraries.ads.mobile.sdk.common.AdLoadCallback
import com.google.android.libraries.ads.mobile.sdk.common.LoadAdError

@Composable
fun AdaptiveBannerAd(
    adUnitId: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val activity = context as? Activity ?: return

    var isAdLoaded by remember { mutableStateOf(false) }
    var isAdFailed by remember { mutableStateOf(false) }

    if (isAdFailed) return

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        if (!isAdLoaded) {
            BannerShimmer()
        }

        AndroidView(
            modifier = Modifier.fillMaxWidth().height(50.dp),
            factory = { ctx ->
                val frameLayout = FrameLayout(ctx)
                
                val display = activity.windowManager.defaultDisplay
                val outMetrics = DisplayMetrics()
                display.getMetrics(outMetrics)
                val adWidth = (outMetrics.widthPixels / outMetrics.density).toInt()
                
                val adSize = AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(ctx, adWidth)
                val adRequest = BannerAdRequest.Builder(adUnitId, adSize).build()
                
                BannerAd.load(adRequest, object : AdLoadCallback<BannerAd> {
                    override fun onAdLoaded(ad: BannerAd) {
                        Handler(Looper.getMainLooper()).post {
                            isAdLoaded = true
                            frameLayout.removeAllViews()
                            frameLayout.addView(ad.getView(activity))
                        }
                    }

                    override fun onAdFailedToLoad(error: LoadAdError) {
                        Handler(Looper.getMainLooper()).post {
                            isAdFailed = true
                        }
                    }
                })
                
                frameLayout
            }
        )
    }
}

@Composable
fun BannerShimmer() {
    val shimmerTransition = rememberInfiniteTransition(label = "bannerShimmer")
    val alpha by shimmerTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )
    val color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = alpha)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(color)
    )
}
