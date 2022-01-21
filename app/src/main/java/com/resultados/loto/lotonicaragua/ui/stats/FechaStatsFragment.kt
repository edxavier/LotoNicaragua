package com.resultados.loto.lotonicaragua.ui.stats

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import com.resultados.loto.lotonicaragua.R
import com.resultados.loto.lotonicaragua.ScopeFragment
import com.resultados.loto.lotonicaragua.data.RequestResult
import com.resultados.loto.lotonicaragua.data.repo.RepoResults
import com.resultados.loto.lotonicaragua.databinding.AdNativeLayout2Binding
import com.resultados.loto.lotonicaragua.databinding.FragmentFechaStatsBinding
import com.resultados.loto.lotonicaragua.databinding.TopFechasItemBinding
import com.resultados.loto.lotonicaragua.setHidden
import com.resultados.loto.lotonicaragua.setupBarChartStyle
import kotlinx.coroutines.launch

class FechaStatsFragment : ScopeFragment() {

    private lateinit var navController: NavController
    private lateinit var binding: FragmentFechaStatsBinding
    private lateinit var repo: RepoResults
    private var nativeAd: NativeAd? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFechaStatsBinding.inflate(inflater)
        //return inflater.inflate(R.layout.fragment_previous, container, false)
        return binding.root
        //return inflater.inflate(R.layout.fragment_diaria_stats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        requireActivity().onBackPressedDispatcher.addCallback(this){ navController.navigateUp()}
        repo = RepoResults(requireContext())
        loadNativeAd()
        launch {
            try {
                val res = repo.fetchStatsFechas()
                if (res is RequestResult.StatsFecha) {
                    binding.progressBar2.setHidden()
                    binding.total.text = "Estadísticas de los últimos ${res.stats.total} sorteos"
                    //binding.textGallery.text = res.stats.total.toString()
                    val labels = listOf("1-5", "6-10", "11-15", "16-20", "21-25", "26-31")
                    val monthLabels = listOf(
                        "Ene",
                        "Feb",
                        "Mar",
                        "Abr",
                        "May",
                        "Jun",
                        "Jul",
                        "Ago",
                        "Sep",
                        "Oct",
                        "Nov",
                        "Dic"
                    )
                    val labels2: MutableList<String> = ArrayList()

                    res.stats.numbersFrequency.numbers.forEach {
                        labels2.add(
                            it.toInt().toString()
                        )
                    }
                    binding.histogram.setupBarChartStyle(labels)
                    binding.histogram2.setupBarChartStyle(labels2)
                    binding.histogram3.setupBarChartStyle(monthLabels)

                    binding.histogram.data = buildHistogramDataSet(res.stats.histogram)
                    binding.histogram2.data =
                        buildHistogramDataSet2(res.stats.numbersFrequency.frequency)
                    binding.histogram3.data = buildHistogramDataSet2(res.stats.monthHistogram)

                    res.stats.topMixin.forEach {
                        val adBinding = TopFechasItemBinding.inflate(layoutInflater)
                        adBinding.month.text = it.month
                        adBinding.day.text = it.day
                        adBinding.count.text = it.count
                        binding.mainContainer.addView(adBinding.root)
                    }
                    //binding.histogram.layoutParams.width=100*10;
                }
            }catch (e:Exception){
                Toast.makeText(requireContext(), "Error al cargar los datos", Toast.LENGTH_LONG).show()
                binding.progressBar2.setHidden()
            }
        }
    }


    private fun buildHistogramDataSet(hist:List<Float>): BarData {
        val entries: MutableList<BarEntry> = ArrayList()
        hist.forEachIndexed{ i, e  ->
            entries.add(BarEntry(i.toFloat(), e))
        }
        val pDataSet = BarDataSet(entries, "Porcentaje de coincidencias")
        pDataSet.color = ContextCompat.getColor(requireContext(), R.color.primaryDarkColor)
        pDataSet.valueTypeface = ResourcesCompat.getFont(requireContext(), R.font.source_sans_pro_semibold)
        pDataSet.valueTextSize = 10f

        val dataSets: MutableList<IBarDataSet> = ArrayList()
        dataSets.add(pDataSet)
        //data.barWidth = 0.9f

        return BarData(dataSets)

    }

    private fun buildHistogramDataSet2(hist:List<Float>): BarData {
        val entries: MutableList<BarEntry> = ArrayList()
        hist.forEachIndexed{ i, e  ->
            entries.add(BarEntry(i.toFloat(), e))
        }
        val pDataSet = BarDataSet(entries, "Porcentaje de coincidencias")
        pDataSet.color = ContextCompat.getColor(requireContext(), R.color.primaryDarkColor)
        pDataSet.valueTypeface = ResourcesCompat.getFont(requireContext(), R.font.source_sans_pro_semibold)
        pDataSet.valueTextSize = 10f

        val dataSets: MutableList<IBarDataSet> = ArrayList()
        dataSets.add(pDataSet)
        val data = BarData(dataSets)
        //data.barWidth = 0.9f

        return  data

    }


    @SuppressLint("InflateParams")
    private fun loadNativeAd(){
        val test = "ca-app-pub-3940256099942544/2247696110"
        val nativeCode = getString(R.string.ads_native)
        val builder = AdLoader.Builder(requireContext(), nativeCode)
        builder.forNativeAd { onNativeAd ->
            // If this callback occurs after the activity is destroyed, you must call
            // destroy and return or you may get a memory leak.
            try {
                if(isAdded) {
                    nativeAd?.destroy()
                    nativeAd = onNativeAd
                    val adBinding = AdNativeLayout2Binding.inflate(layoutInflater)
                    //val nativeAdview = AdNativeLayoutBinding.inflate(layoutInflater).root
                    binding.adsContainer.removeAllViews()
                    binding.adsContainer.addView(populateNativeAd(nativeAd!!, adBinding))
                }
            }catch (e:Exception){
                Toast.makeText(requireContext(), "IllegalStateException", Toast.LENGTH_LONG).show()
            }
        }

        val adLoader = builder.build()
        adLoader.loadAds(AdRequest.Builder().build(), 1)
    }

    private fun populateNativeAd(nativeAd: NativeAd, adView: AdNativeLayout2Binding): NativeAdView {
        val nativeAdView = adView.root
        try {
            with(adView) {
                adHeadline.text = nativeAd.headline
                nativeAdView.headlineView = adHeadline

                nativeAd.advertiser?.let {
                    adAdvertiser.text = it
                    adAdvertiser.visibility = View.VISIBLE
                    nativeAdView.advertiserView = adAdvertiser
                }
                nativeAd.icon?.let {
                    adIcon.setImageDrawable(it.drawable)
                    adIcon.visibility = View.VISIBLE
                    nativeAdView.iconView = adIcon
                }
                nativeAd.starRating?.let {
                    adStartRating.rating = it.toFloat()
                    adStartRating.visibility = View.VISIBLE
                    nativeAdView.starRatingView = adStartRating
                }
                nativeAd.callToAction?.let {
                    adBtnCallToAction.text = it
                    nativeAdView.callToActionView = adBtnCallToAction
                }
                nativeAd.body?.let {
                    adBodyText.text = it
                    nativeAdView.bodyView = adBodyText
                }
                adMedia.setMediaContent(nativeAd.mediaContent)
                adMedia.setImageScaleType(ImageView.ScaleType.FIT_XY)
                adMedia.visibility = View.VISIBLE
                nativeAdView.mediaView = adMedia
            }
        }catch (e: Exception){}
        nativeAdView.setNativeAd(nativeAd)
        return nativeAdView
    }



}