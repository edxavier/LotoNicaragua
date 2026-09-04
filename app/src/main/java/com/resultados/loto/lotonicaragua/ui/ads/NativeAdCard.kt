package com.resultados.loto.lotonicaragua.ui.ads

import android.graphics.Outline
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Space
import android.widget.TextView
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.libraries.ads.mobile.sdk.common.*
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAd
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAdRequest
import com.google.android.libraries.ads.mobile.sdk.banner.AdSize
import com.google.android.libraries.ads.mobile.sdk.nativead.*
import com.resultados.loto.lotonicaragua.R
import com.resultados.loto.lotonicaragua.ui.home.composes.CardTopAccent
import androidx.core.graphics.toColorInt
import androidx.compose.foundation.isSystemInDarkTheme

private fun starString(rating: Double): String {
    val full = rating.toInt().coerceIn(0, 5)
    val empty = (5 - full).coerceAtLeast(0)
    return "\u2605".repeat(full) + "\u2606".repeat(empty)
}

@Composable
fun NativeAdCard(
    modifier: Modifier = Modifier,
    onAdFailed: () -> Unit = {}
) {
    val context = LocalContext.current
    val density = context.resources.displayMetrics.density

    val adUnitId = stringResource(R.string.ads_native)

    var nativeAd by remember { mutableStateOf<NativeAd?>(null) }
    var hasFailed by remember { mutableStateOf(false) }
    var showBannerFallback by remember { mutableStateOf(false) }

    LaunchedEffect(hasFailed) {
        if (hasFailed) {
            showBannerFallback = true
            onAdFailed()
        }
    }

    val adLoaderCallback = remember(context) {
        object : NativeAdLoaderCallback {
            override fun onNativeAdLoaded(ad: NativeAd) {
                Log.d("NativeAd", "Ad loaded")
                android.os.Handler(android.os.Looper.getMainLooper()).post {
                    nativeAd?.destroy()
                    nativeAd = ad
                }
            }

            override fun onAdFailedToLoad(error: LoadAdError) {
                Log.w("NativeAd", "Failed: ${error.message}")
                android.os.Handler(android.os.Looper.getMainLooper()).post {
                    hasFailed = true
                }
            }
        }
    }

    DisposableEffect(adUnitId) {
        val adTypes = listOf(NativeAd.NativeAdType.NATIVE)
        val adRequest = NativeAdRequest.Builder(adUnitId, adTypes).build()
        NativeAdLoader.load(adRequest, adLoaderCallback)
        onDispose { nativeAd?.destroy() }
    }

    val cardShape = RoundedCornerShape(20.dp)
    val isDark = isSystemInDarkTheme()
    val cardBg = if (isDark) MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
    val accentColor = MaterialTheme.colorScheme.primary

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = cardShape,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardBg
        )
    ) {
        CardTopAccent(accentColor)
        if (showBannerFallback) {
            val bannerId = stringResource(R.string.ads_fallback_banner)
            AndroidView(
                factory = { ctx ->
                    val adSize = AdSize.MEDIUM_RECTANGLE
                    val adRequest = BannerAdRequest.Builder(bannerId, adSize).build()
                    val container = FrameLayout(ctx)
                    BannerAd.load(adRequest, object : AdLoadCallback<BannerAd> {
                        override fun onAdLoaded(ad: BannerAd) {
                            android.os.Handler(android.os.Looper.getMainLooper()).post {
                                container.removeAllViews()
                                container.addView(ad.getView(ctx as android.app.Activity))
                            }
                        }
                        override fun onAdFailedToLoad(error: LoadAdError) {}
                    })
                    container
                },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            )
        } else {
            if (nativeAd == null) {
                NativeAdShimmer(cardShape)
            }

            nativeAd?.let { ad ->
                key(ad.hashCode()) {
                    val onSurfaceColor = MaterialTheme.colorScheme.onSurface.toArgb()
                    val secondaryColor = MaterialTheme.colorScheme.onSurfaceVariant.toArgb()
                    val mutedColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f).toArgb()
                    val ctaBgColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f).toArgb()
                    val ctaTextColor = MaterialTheme.colorScheme.primary.toArgb()
                    val adBadgeBg = MaterialTheme.colorScheme.surfaceVariant.toArgb()
                    val adBadgeText = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f).toArgb()
                    val starColor = "#FFB800".toColorInt()
                    val cardBgColor = cardBg.toArgb()

                    AndroidView(
                        factory = { ctx ->
                            val paddingPx = (12 * density).toInt()
                            val gapXs = (4 * density).toInt()
                            val gapSm = (6 * density).toInt()
                            val gapMd = (8 * density).toInt()
                            val iconSize = (40 * density).toInt()
                            val hasMedia = ad.mediaContent != null
                            val hasAdvertiser = !ad.advertiser.isNullOrBlank()
                            val hasBody = !ad.body.isNullOrBlank()
                            val starRating = ad.starRating
                            val hasStars = (starRating ?: 0.0) > 0
                            val hasCTA = !ad.callToAction.isNullOrBlank()

                            NativeAdView(ctx).apply {
                                val adView = this
                                setBackgroundColor(cardBgColor)

                                val content = LinearLayout(ctx).apply {
                                    orientation = LinearLayout.VERTICAL
                                    setPadding(paddingPx, paddingPx, paddingPx, paddingPx)
                                }

                                var mediaView: MediaView? = null
                                if (hasMedia) {
                                    mediaView = MediaView(ctx).apply {
                                        layoutParams = LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            (140 * density).toInt()
                                        ).apply {
                                            setMargins(0, 0, 0, gapMd)
                                        }
                                        clipToOutline = true
                                        outlineProvider = object : ViewOutlineProvider() {
                                            override fun getOutline(view: View, outline: Outline) {
                                                outline.setRoundRect(0, 0, view.width, view.height, 8 * density)
                                            }
                                        }
                                    }
                                    content.addView(mediaView)
                                }

                                val headerRow = LinearLayout(ctx).apply {
                                    orientation = LinearLayout.HORIZONTAL
                                    gravity = Gravity.CENTER_VERTICAL
                                }

                                if (ad.icon != null) {
                                    val iconView = ImageView(ctx).apply {
                                        layoutParams = LinearLayout.LayoutParams(iconSize, iconSize).apply {
                                            setMargins(0, 0, gapMd, 0)
                                        }
                                        clipToOutline = true
                                        outlineProvider = object : ViewOutlineProvider() {
                                            override fun getOutline(view: View, outline: Outline) {
                                                outline.setRoundRect(0, 0, view.width, view.height, 8 * density)
                                            }
                                        }
                                    }
                                    iconView.setImageDrawable(ad.icon!!.drawable)
                                    adView.iconView = iconView
                                    headerRow.addView(iconView)
                                }

                                val titleColumn = LinearLayout(ctx).apply {
                                    orientation = LinearLayout.VERTICAL
                                    layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                                }

                                val headlineView = TextView(ctx).apply {
                                    text = ad.headline ?: ""
                                    setTextColor(onSurfaceColor)
                                    textSize = 15f
                                    maxLines = 2
                                    ellipsize = android.text.TextUtils.TruncateAt.END
                                    typeface = android.graphics.Typeface.DEFAULT_BOLD
                                }
                                adView.headlineView = headlineView
                                titleColumn.addView(headlineView)

                                if (hasAdvertiser) {
                                    val advertiserView = TextView(ctx).apply {
                                        text = ad.advertiser
                                        setTextColor(mutedColor)
                                        textSize = 12f
                                        maxLines = 1
                                    }
                                    adView.advertiserView = advertiserView
                                    titleColumn.addView(advertiserView)
                                }
                                headerRow.addView(titleColumn)
                                content.addView(headerRow)

                                if (hasStars) {
                                    val starsView = TextView(ctx).apply {
                                        text = starString(starRating!!)
                                        setTextColor(starColor)
                                        textSize = 12f
                                    }
                                    adView.starRatingView = starsView
                                    content.addView(starsView)
                                }

                                if (hasBody) {
                                    val bodyView = TextView(ctx).apply {
                                        text = ad.body
                                        setTextColor(secondaryColor)
                                        textSize = 13f
                                        maxLines = 3
                                        ellipsize = android.text.TextUtils.TruncateAt.END
                                        layoutParams = LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                        ).apply {
                                            topMargin = gapSm
                                        }
                                    }
                                    adView.bodyView = bodyView
                                    content.addView(bodyView)
                                }

                                if (hasCTA) {
                                    val ctaView = TextView(ctx).apply {
                                        text = ad.callToAction
                                        textSize = 14f
                                        gravity = Gravity.CENTER
                                        setTextColor(ctaTextColor)
                                        typeface = android.graphics.Typeface.DEFAULT_BOLD
                                        background = android.graphics.drawable.GradientDrawable().apply {
                                            setColor(ctaBgColor)
                                            cornerRadius = 8 * density
                                        }
                                        setPadding(
                                            (20 * density).toInt(),
                                            (10 * density).toInt(),
                                            (20 * density).toInt(),
                                            (10 * density).toInt()
                                        )
                                    }
                                    adView.callToActionView = ctaView
                                    val ctaContainer = LinearLayout(ctx).apply {
                                        gravity = Gravity.END
                                        layoutParams = LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                        ).apply {
                                            topMargin = gapMd
                                        }
                                    }
                                    ctaContainer.addView(ctaView)
                                    content.addView(ctaContainer)
                                }

                                addView(content)

                                // Ad Badge
                                val adBadge = TextView(ctx).apply {
                                    text = "Ad"
                                    textSize = 10f
                                    setTextColor(adBadgeText)
                                    setPadding((4 * density).toInt(), 0, (4 * density).toInt(), 0)
                                    background = android.graphics.drawable.GradientDrawable().apply {
                                        setColor(adBadgeBg)
                                        cornerRadius = 3 * density
                                    }
                                    layoutParams = FrameLayout.LayoutParams(
                                        FrameLayout.LayoutParams.WRAP_CONTENT,
                                        FrameLayout.LayoutParams.WRAP_CONTENT,
                                        Gravity.TOP or Gravity.START
                                    ).apply {
                                        setMargins(gapSm, gapSm, 0, 0)
                                    }
                                }
                                addView(adBadge)

                                val adChoices = AdChoicesView(ctx)
                                adChoices.layoutParams = FrameLayout.LayoutParams(
                                    (20 * density).toInt(), (20 * density).toInt(),
                                    Gravity.TOP or Gravity.END
                                )
                                addView(adChoices)

                                registerNativeAd(ad, mediaView)
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}

@Composable
private fun NativeAdShimmer(cardShape: RoundedCornerShape) {
    val shimmerTransition = rememberInfiniteTransition(label = "adShimmer")
    val alpha by shimmerTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )
    val shimmer = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = alpha)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Row {
            Box(
                Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(shimmer)
            )
            Spacer(Modifier.width(8.dp))
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    Modifier
                        .fillMaxWidth(0.7f)
                        .height(10.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(shimmer)
                )
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(14.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(shimmer)
                )
                Box(
                    Modifier
                        .fillMaxWidth(0.5f)
                        .height(10.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(shimmer)
                )
                Box(
                    Modifier
                        .fillMaxWidth(0.8f)
                        .height(10.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(shimmer)
                )
            }
        }
        Spacer(Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(shimmer)
            )
            Box(
                Modifier
                    .width(76.dp)
                    .height(28.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(shimmer)
            )
        }
    }
}
