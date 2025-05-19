package com.resultados.loto.lotonicaragua.ui.home

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.edit
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import com.resultados.loto.lotonicaragua.*
import com.resultados.loto.lotonicaragua.R
import com.resultados.loto.lotonicaragua.data.RequestResult
import com.resultados.loto.lotonicaragua.data.repo.RepoResults
import com.resultados.loto.lotonicaragua.databinding.AdNativeLayout2Binding
import com.resultados.loto.lotonicaragua.databinding.FragmentHomeBinding
import com.resultados.loto.lotonicaragua.ui.home.composes.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.util.*

class ResultsFragment : ScopeFragment() {

    private lateinit var homeViewModel: ResultsViewModel
    private var mInterstitialAd: InterstitialAd? = null
    private lateinit var navController: NavController
    private lateinit var binding: FragmentHomeBinding
    private var nativeAd: NativeAd? = null

    private lateinit var repoResults: RepoResults

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        binding = FragmentHomeBinding.inflate(inflater)
        homeViewModel = ViewModelProvider(requireActivity()).get(ResultsViewModel::class.java)
        //return inflater.inflate(R.layout.fragment_home, container, false)
        return binding.root
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repoResults = RepoResults(requireContext())

        navController = findNavController()
        requestInterstitialAds()
        binding.resultsContainer.setHidden()

        binding.cruzYPiramide.setContent {
            CruzPiramideOptions(onClick = {
                val action = ResultsFragmentDirections.actionNavHomeToLuckyNumbers()
                navController.navigate(action)
            })
        }
        cargarResultados()
        launch {
            delay(2000)
            showInterstitial()
        }
        val pref = requireContext().getSharedPreferences("LOTO_PREFS", Context.MODE_PRIVATE)

        loadNativeAd()
    }

    @SuppressLint("SetTextI18n")
    private fun cargarResultados(){
        launch{
            try {
                binding.loadingIndicator.setVisible()
                binding.resultsContainer.setHidden()
                //binding.loadingIndicator.fadeZoomIn()
                showLoading()
                getRecentResults()
            }catch (e: Exception){
                Log.e("EDER", e.toString())
                binding.resultsContainer.setHidden()
                val errMessage = if(e.message!=null)
                    e.message!!
                else
                    "Error desconocido \n Intenta nuevamente por favor"
                showError("Ha ocurrido un error",
                    "Error desconocido \n $errMessage \n Intenta nuevamente por favor",
                    R.raw.error_animation, false)
            }

        }
    }

    private fun showFailUI(title:String, message: String, composeView: ComposeView){
        composeView.setContent {
            CardNoData(title, message)
        }
    }

    private suspend fun getRecentResults(){
        try {
            val recentResults = repoResults.fetchRecentResults()
            if (recentResults is RequestResult.LotoRecentResults) {
                val results = recentResults.recentResults
                if (results.diaria.isNotEmpty()) {
                    binding.diariaComposeView.setContent {
                        CardDiaria(results.diaria, navController)
                    }
                }
                if(results.fechas.isNotEmpty()){
                    binding.fechasComposeView.setContent {
                        CardFechas(results = results.fechas, navController = navController)
                    }
                }
                if (results.juega3.isNotEmpty()) {
                    binding.juga3ComposeView.setContent {
                        CardJuega(results = results.juega3, navController = navController)
                    }
                }
                if (results.premia2.isNotEmpty()) {
                    binding.comboComposeView.setContent {
                        CardCombo(results = results.premia2, navController = navController)
                    }
                }
                if (results.terminacion.isNotEmpty()) {
                    binding.terminacionComposeView.setContent {
                        CardTerminacion(results = results.terminacion, navController = navController)
                    }
                }
            }else if(recentResults is RequestResult.Failure){
                val message = "No fue posible conectarse al servidor:${recentResults.status} - ${recentResults.text}"
                showFailUI(
                    "Resultados",
                    message,
                    binding.diariaComposeView
                )
                enviarEmail(message)
            }
        }catch (e: ConnectException){
            showFailUI("Resultados", "No fue posible conectarse al servidor", binding.diariaComposeView)
        }
        catch (e: SocketTimeoutException){
            showFailUI("Resultados", "No fue posible conectarse al servidor", binding.diariaComposeView)
        }
        binding.loadingIndicator.setHidden()
        binding.resultsContainer.setVisible()
    }

    private fun enviarEmail(content: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822" // Para que muestre solo apps de correo
            putExtra(Intent.EXTRA_EMAIL, arrayOf("edxavier05@gmail.com"))
            putExtra(Intent.EXTRA_SUBJECT, "Fallo al cargar resultados loto")
            putExtra(Intent.EXTRA_TEXT, "$content")
        }

        try {
            startActivity(Intent.createChooser(intent, "Enviar email con..."))
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(requireContext(), "No hay apps de correo instaladas.", Toast.LENGTH_SHORT).show()
        }
    }


    private fun showError(title: String, message: String, animation: Int, loop: Boolean){
        binding.loadingIndicator.setVisible()
        binding.animationView.setAnimation(animation)
        binding.animationView.loop(loop)
        binding.animationView.playAnimation()
        binding.messageTitle.text = title
        binding.messageBody.text = message
    }

    @SuppressLint("SetTextI18n")
    private fun showLoading(){
        binding.animationView.setAnimation(R.raw.meditation_wait)
        binding.animationView.loop(true)
        binding.animationView.playAnimation()
        binding.messageTitle.text = "Examinando resultados"
        binding.messageBody.text = "Por favor espera..."
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
    }
    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_refresh -> {
                cargarResultados()
                showInterstitial()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun requestInterstitialAds() {
        val adUnitId = resources.getString(R.string.ads_intersticial)
        InterstitialAd.load(requireActivity(), adUnitId, AdRequest.Builder().build(), object:
            InterstitialAdLoadCallback(){
            override fun onAdLoaded(p0: InterstitialAd) {
                super.onAdLoaded(p0)
                mInterstitialAd = p0
            }
        })
    }
    private fun showInterstitial() {
        val pref = requireContext().getSharedPreferences("LOTO_PREFS", Context.MODE_PRIVATE)

        val ne = pref.getInt("exec_count", 0)
        pref.edit { putInt("exec_count", ne + 1) }
        //Log.d("EDERne", "${ne+1}")
        //Log.d("EDERsh", "${Prefs.getInt("show_after", 3)}")
        if (ne + 1 == pref.getInt("show_after", 3)) {
            pref.edit { putInt("exec_count", 0) }
            val r = Random()
            val min = 2
            val max = 3
            val rnd = r.nextInt(max - min) + min
            pref.edit { putInt("show_after", rnd) }
            mInterstitialAd?.show(requireActivity())
        }

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
                    binding.adViewContainer.removeAllViews()
                    binding.adViewContainer.addView(populateNativeAd(nativeAd!!, adBinding))
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
                nativeAd.mediaContent?.let {
                    adMedia.setMediaContent(it)
                    adMedia.setImageScaleType(ImageView.ScaleType.FIT_XY)
                    adMedia.visibility = View.VISIBLE
                    nativeAdView.mediaView = adMedia
                }
            }
        }catch (e: Exception){}
        nativeAdView.setNativeAd(nativeAd)
        return nativeAdView
    }


}