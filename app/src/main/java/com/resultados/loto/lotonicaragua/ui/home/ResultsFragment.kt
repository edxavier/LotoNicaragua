package com.resultados.loto.lotonicaragua.ui.home

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.edit
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.gms.ads.*
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import com.resultados.loto.lotonicaragua.*
import com.resultados.loto.lotonicaragua.R
import com.resultados.loto.lotonicaragua.data.repo.RepoResults
import com.resultados.loto.lotonicaragua.databinding.AdNativeLayoutBinding
import com.resultados.loto.lotonicaragua.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.card_diaria.*
import kotlinx.android.synthetic.main.card_fechas.*
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.*
import javax.net.ssl.SSLHandshakeException


class ResultsFragment : ScopeFragment() {

    private lateinit var homeViewModel: ResultsViewModel
    private var mInterstitialAd: InterstitialAd? = null
    private lateinit var navController: NavController
    private lateinit var binding: FragmentHomeBinding
    private var nativeAd: NativeAd? = null


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
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        requestInterstitialAds()
        binding.resultsContainer.setHidden()

        cargarResultados()
        launch {
            delay(2000)
            showInterstitial()
        }
        val pref = requireContext().getSharedPreferences("LOTO_PREFS", Context.MODE_PRIVATE)
        val dialogShown = pref.getBoolean("dialog_shown", false)
        if(!dialogShown){
            pref.edit { putBoolean("dialog_shown", true) }
            AlertDialog.Builder(context)
                .setTitle("Aviso")
                .setMessage("Consulta los resultados anteriores del mes en curso haciendo clic sobre el sorteo correspondiente") // Specifying a listener allows you to take an action before dismissing the dialog.
                .setPositiveButton(R.string.accept, null)
                //.setIcon(android.R.drawable.ic_dialog_alert)
                .show()
        }

        binding.diaria.btnDiaria.setOnClickListener {
            val action = ResultsFragmentDirections.actionNavHomeToPreviousResultsFragment(sorteo = ScraperHelper.DIARIA)
            navController.navigate(action)
        }
        binding.fechas.btnFechas.setOnClickListener {
            val action = ResultsFragmentDirections.actionNavHomeToPreviousResultsFragment(sorteo = ScraperHelper.FECHAS)
            navController.navigate(action)
        }
        binding.juega3.btnJuega3.setOnClickListener {
            val action = ResultsFragmentDirections.actionNavHomeToPreviousResultsFragment(sorteo = ScraperHelper.JUEGA3)
            navController.navigate(action)
        }
        binding.terminacion2.btnTerminacion2.setOnClickListener {
            val action = ResultsFragmentDirections.actionNavHomeToPreviousResultsFragment(sorteo = ScraperHelper.TERMINACION2)
            navController.navigate(action)
        }
        binding.supercombo.btnSpcombo.setOnClickListener {
            val action = ResultsFragmentDirections.actionNavHomeToPreviousResultsFragment(sorteo = ScraperHelper.SUPERCOMBO)
            navController.navigate(action)
        }
        binding.laGrande.btnLagrande.setOnClickListener { showWarning() }
        loadNativeAd()
    }

    fun showWarning(){
        AlertDialog.Builder(context)
            .setTitle("Aviso!")
            .setMessage("Momentaneamente se no se encuentra disponible esta opcion, estara completada en la siguiente actualizacion.")
            .setPositiveButton(R.string.accept, null)
            //.setIcon(android.R.drawable.ic_dialog_alert)
            .show()

    }
    @SuppressLint("SetTextI18n")
    private fun cargarResultados(){
        launch{
            try {
                binding.loadingIndicator.setVisible()
                binding.resultsContainer.setHidden()
                //binding.loadingIndicator.fadeZoomIn()
                showLoading()
                async { try { homeViewModel.getPreviousResults() }catch (e: Exception){} }
                val response = homeViewModel.getConnection()

                when {
                    response==null -> {
                        showError(
                            "Ha ocurrido un error", "No se recibio respuesta del servidor",
                            R.raw.error_animation, false
                        )
                        return@launch
                    }
                    response.statusCode()==200 -> {
                        val doc = homeViewModel.getResultsContent()
                        doc?.let {
                            var fechaSorteo = "----"
                            val fechaHeader = doc.getElementsByClass("et_pb_text_1").select("div").eachText()
                            if (fechaHeader.size>0){
                                try {
                                    fechaSorteo = fechaHeader[0].replace(',', '\n')
                                }catch (e:Exception){}
                            }


                            val diaria = doc.getElementsByClass("et_pb_text_2").select("div>div>span").eachText()
                            val fechasLoto = doc.getElementsByClass("et_pb_text_3").select("div>div>span").eachText()
                            val juega3 = doc.getElementsByClass("et_pb_text_4").select("div>div>span").eachText()
                            val spcombo = doc.getElementsByClass("et_pb_text_5").select("div>div>span").eachText()
                            val t2 = doc.getElementsByClass("et_pb_text_6").select("div>div>span").eachText()
                            val lg = doc.getElementsByClass("et_pb_text_7").select("div>div>span").eachText()
                            Log.e("EDER", lg.toString())

                            if(diaria.isNotEmpty()) {
                                binding.diaria.txtGanadorDiaria.text = "${diaria[0]}${diaria[1]}"
                                binding.diaria.txtFechaDiaria.text = fechaSorteo

                                binding.fechas.ganadorFechasDia1.text = fechasLoto[0]
                                binding.fechas.ganadorFechasMes1.text = fechasLoto[1]
                                binding.fechas.txtFechaFechas.text = fechaSorteo

                                binding.juega3.txtFechaJuega3.text = fechaSorteo
                                binding.juega3.txtGanadorJuega3.text = "${juega3[0]}${juega3[1]}${juega3[2]}"

                                binding.supercombo.fechaSupercombo.text = fechaSorteo
                                binding.supercombo.txtGanadorScomboP1.text = "${spcombo[0]}${spcombo[1]}"
                                binding.supercombo.txtGanadorScomboP2.text = "${spcombo[2]}${spcombo[3]}"

                                binding.terminacion2.ganadorTerminacion2.text = t2[0]
                                binding.laGrande.ganadorLg1.text = lg[0]
                                binding.laGrande.ganadorLg2.text = lg[1]
                                binding.laGrande.ganadorLg3.text = lg[2]
                                binding.laGrande.ganadorLg4.text = lg[3]
                                binding.laGrande.ganadorLg5.text = lg[4]
                                binding.laGrande.ganadorLgOro.text = lg[5]

                            }

                            binding.loadingIndicator.setHidden()
                            binding.resultsContainer.setVisible()
                            //binding.resultsContainer.scaleIn()
                        }
                    }
                    else -> {
                        val codeDescription = getCodeDescription(response.statusCode())
                        showError(
                            "Error al consultar los resultados",
                            "${response.statusCode()}: ${response.statusMessage()}\n\n $codeDescription",
                            R.raw.error_animation, false
                        )
                    }
                }


            }catch (e: UnknownHostException){
                binding.resultsContainer.setHidden()
                showError(
                    "Error de conexión",
                    "No fue posible acceder a los resultados",
                    R.raw.women_no_internet, true
                )
            }
            catch (e: SocketTimeoutException){
                binding.resultsContainer.setHidden()
                showError(
                    "Error de conexión",
                    "No fue posible acceder a los resultados, verifica tu conexión a internet e intenta nuevamente",
                    R.raw.women_no_internet, true
                )
            }
            catch (e: SSLHandshakeException){
                binding.resultsContainer.setHidden()
                showError(
                    "Error de conexión",
                    "No fue posible acceder a los resultados, verifica tu conexión a internet",
                    R.raw.women_no_internet, true
                )
            }
            catch (e: Exception){
                //Log.e("EDER", e.toString())
                e.printStackTrace()
                binding.resultsContainer.setHidden()
                var errMessage = if(e.message!=null)
                    e.message!!
                else
                    "Error desconocido"
                errMessage += "\n Intenta nuevamente por favor"
                showError("Ha ocurrido un error", errMessage, R.raw.error_animation, false)
            }

        }
    }

    private fun getCodeDescription(statusCode: Int): String {
        return when(statusCode){
            400 -> "La solicitud no se puede cumplir debido a una sintaxis incorrecta"
            403 -> "El servidor se niega a responder. El recurso no está disponible por alguna razón"
            404 -> "No se pudo encontrar el recurso solicitado."
            500 -> "El servidor encontró una condición inesperada que le impidió cumplir con la solicitud."
            502 -> "El servidor estaba actuando como puerta de enlace o proxy y recibió una respuesta no válida del servidor ascendente."
            else-> "Error de servidor desconocido"
        }
    }

    private fun showError(title: String, message: String, animation: Int, loop: Boolean){
        binding.animationView.setAnimation(animation)
        binding.animationView.loop(loop)
        binding.animationView.playAnimation()
        binding.messageTitle.text = title
        binding.messageBody.text = message
    }

    @SuppressLint("SetTextI18n")
    private fun showLoading(){
        binding.animationView.setAnimation(R.raw.search_animation)
        binding.animationView.loop(true)
        binding.animationView.playAnimation()
        binding.messageTitle.text = "Examinando resultados"
        binding.messageBody.text = "Por favor espera."
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
    }
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

        mInterstitialAd = InterstitialAd(activity)
        mInterstitialAd?.adUnitId = resources.getString(R.string.ads_intersticial)
        mInterstitialAd?.loadAd(AdRequest.Builder().build())
        mInterstitialAd?.adListener = object : AdListener() {
            override fun onAdClosed() {
                super.onAdClosed()
                requestInterstitialAds()
            }
        }
    }
    private fun showInterstitial() {
        val pref = requireContext().getSharedPreferences("LOTO_PREFS", Context.MODE_PRIVATE)

        val ne = pref.getInt("exec_count", 0)
        pref.edit { putInt("exec_count", ne + 1) }
        //Log.d("EDERne", "${ne+1}")
        //Log.d("EDERsh", "${Prefs.getInt("show_after", 3)}")
        if (ne + 1 == pref.getInt("show_after", 4)) {
            pref.edit { putInt("exec_count", 0) }
            val r = Random()
            val min = 3
            val max = 4
            val rnd = r.nextInt(max - min) + min
            pref.edit { putInt("show_after", rnd) }
            mInterstitialAd?.let {
                if(it.isLoaded)
                    it.show()
            }
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
                    val adBinding = AdNativeLayoutBinding.inflate(layoutInflater)
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

    private fun populateNativeAd(nativeAd: NativeAd, adView: AdNativeLayoutBinding): NativeAdView {
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