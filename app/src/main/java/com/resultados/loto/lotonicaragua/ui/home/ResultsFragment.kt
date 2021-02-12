package com.resultados.loto.lotonicaragua.ui.home

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.edit
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.airbnb.lottie.LottieAnimationView
import com.google.android.gms.ads.*
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import com.resultados.loto.lotonicaragua.*
import com.resultados.loto.lotonicaragua.R
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


    private lateinit var loadingIndicator: LinearLayout
    private lateinit var resultsContainer: LinearLayout
    private lateinit var lottie: LottieAnimationView
    private lateinit var messageTitle: TextView
    private lateinit var messageBody: TextView

    private lateinit var hora1Diaria: TextView
    private lateinit var hora2Diaria: TextView
    private lateinit var hora3Diaria: TextView
    private lateinit var fecha1Diaria: TextView
    private lateinit var fecha2Diaria: TextView
    private lateinit var fecha3Diaria: TextView
    private lateinit var ganador1Diaria: TextView
    private lateinit var ganador2Diaria: TextView
    private lateinit var ganador3Diaria: TextView

    private lateinit var hora1Juega3: TextView
    private lateinit var hora2Juega3: TextView
    private lateinit var hora3Juega3: TextView
    private lateinit var fecha1Juega3: TextView
    private lateinit var fecha2Juega3: TextView
    private lateinit var fecha3Juega3: TextView
    private lateinit var ganador1Juega3: TextView
    private lateinit var ganador2Juega3: TextView
    private lateinit var ganador3Juega3: TextView

    private lateinit var hora1Combo: TextView
    private lateinit var hora2Combo: TextView
    private lateinit var hora3Combo: TextView
    private lateinit var fecha1Combo: TextView
    private lateinit var fecha2Combo: TextView
    private lateinit var fecha3Combo: TextView
    private lateinit var p1ganador1Combo: TextView
    private lateinit var p1ganador2Combo: TextView
    private lateinit var p1ganador3Combo: TextView
    private lateinit var p2ganador1Combo: TextView
    private lateinit var p2ganador2Combo: TextView
    private lateinit var p2ganador3Combo: TextView

    private lateinit var fechaTerminacion2: TextView
    private lateinit var ganadorTerminacion2: TextView

    private lateinit var fechaLaGrande: TextView

    private lateinit var lg1: TextView
    private lateinit var lg2: TextView
    private lateinit var lg3: TextView
    private lateinit var lg4: TextView
    private lateinit var lg5: TextView
    private lateinit var lgOro: TextView



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
        initViews()
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

        loadNativeAd()
    }

    private fun initViews() {
        //resultsContainer = requireActivity().findViewById(R.id.results_container)
        //loadingIndicator = requireActivity().findViewById(R.id.loading_indicator)
        //lottie = requireActivity().findViewById(R.id.animation_view)
        //messageTitle = requireActivity().findViewById(R.id.message_title)
        //messageBody = requireActivity().findViewById(R.id.message_body)

        hora1Diaria = requireActivity().findViewById(R.id.txtHora1Diaria)
        hora2Diaria = requireActivity().findViewById(R.id.txtHora2Diaria)
        hora3Diaria = requireActivity().findViewById(R.id.txtHora3Diaria)
        fecha1Diaria = requireActivity().findViewById(R.id.fecha_diaria_10)
        fecha2Diaria = requireActivity().findViewById(R.id.fecha_diaria_2)
        fecha3Diaria = requireActivity().findViewById(R.id.fecha_diaria_9)
        ganador1Diaria = requireActivity().findViewById(R.id.ganador_diaria_10)
        ganador2Diaria = requireActivity().findViewById(R.id.ganador_diaria_2)
        ganador3Diaria = requireActivity().findViewById(R.id.ganador_diaria_9)

        hora1Juega3 = requireActivity().findViewById(R.id.txtHora1Juega3)
        hora2Juega3 = requireActivity().findViewById(R.id.txtHora2Juega3)
        hora3Juega3 = requireActivity().findViewById(R.id.txtHora3Juega3)
        fecha1Juega3 = requireActivity().findViewById(R.id.fecha_juega3_10)
        fecha2Juega3 = requireActivity().findViewById(R.id.fecha_juega3_2)
        fecha3Juega3 = requireActivity().findViewById(R.id.fecha_juega3_9)
        ganador1Juega3 = requireActivity().findViewById(R.id.ganador_juega3_10)
        ganador2Juega3 = requireActivity().findViewById(R.id.ganador_juega3_2)
        ganador3Juega3 = requireActivity().findViewById(R.id.ganador_juega3_9)

        hora1Combo = requireActivity().findViewById(R.id.txtHora1Combo)
        hora2Combo = requireActivity().findViewById(R.id.txtHora2Combo)
        hora3Combo = requireActivity().findViewById(R.id.txtHora3Combo)
        fecha1Combo = requireActivity().findViewById(R.id.fecha_supercombo_10)
        fecha2Combo = requireActivity().findViewById(R.id.fecha_supercombo_2)
        fecha3Combo = requireActivity().findViewById(R.id.fecha_supercombo_9)
        p1ganador1Combo = requireActivity().findViewById(R.id.ganador_scombo_p1_10)
        p1ganador2Combo = requireActivity().findViewById(R.id.ganador_scombo_p1_2)
        p1ganador3Combo = requireActivity().findViewById(R.id.ganador_scombo_p1_9)
        p2ganador1Combo = requireActivity().findViewById(R.id.ganador_scombo_p2_10)
        p2ganador2Combo = requireActivity().findViewById(R.id.ganador_scombo_p2_2)
        p2ganador3Combo = requireActivity().findViewById(R.id.ganador_scombo_p2_9)

        fechaTerminacion2 = requireActivity().findViewById(R.id.fecha_terminacion2)
        ganadorTerminacion2 = requireActivity().findViewById(R.id.ganador_terminacion2)

        fechaLaGrande = requireActivity().findViewById(R.id.fecha_lagrande)
        lg1 = requireActivity().findViewById(R.id.ganador_lg1)
        lg2 = requireActivity().findViewById(R.id.ganador_lg2)
        lg3 = requireActivity().findViewById(R.id.ganador_lg3)
        lg4 = requireActivity().findViewById(R.id.ganador_lg4)
        lg5 = requireActivity().findViewById(R.id.ganador_lg5)
        lgOro = requireActivity().findViewById(R.id.ganador_lg_oro)


        binding.diaria.cardDiaria.setOnClickListener {
            val action = ResultsFragmentDirections.actionNavHomeToPreviousResultsFragment(sorteo = ScraperHelper.DIARIA)
            navController.navigate(action)
        }
        binding.juega3.cardJuega3.setOnClickListener {
            val action = ResultsFragmentDirections.actionNavHomeToPreviousResultsFragment(sorteo = ScraperHelper.JUEGA3)
            navController.navigate(action)
        }

        binding.fechas.cardFechas.setOnClickListener {
            val action = ResultsFragmentDirections.actionNavHomeToPreviousResultsFragment(sorteo = ScraperHelper.FECHAS)
            navController.navigate(action)
        }

        binding.supercombo.cardSuperCombo.setOnClickListener {
            val action = ResultsFragmentDirections.actionNavHomeToPreviousResultsFragment(sorteo = ScraperHelper.SUPERCOMBO)
            navController.navigate(action)
        }

        binding.terminacion2.cardTerminacion2.setOnClickListener {
            val action = ResultsFragmentDirections.actionNavHomeToPreviousResultsFragment(sorteo = ScraperHelper.TERMINACION2)
            navController.navigate(action)
        }

    }

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
                            val diaria = doc.getElementById("home_diaria").select("span>strong")

                            val juega3 = doc.getElementById("home_juga3").select("span>strong")
                            val supercombo1 = doc.getElementById("home_combo").select("span>strong")
                            val sorteoFechas = doc.getElementById("home_fechas").select("span>strong")

                            val terminacion2 = doc.getElementById("home_t2").select("span>strong")
                            val lagrande = doc.getElementById("home_grande").select("span>strong")


                            val fechaDiaria =  doc.getElementById("home_diaria").select("p")
                            val fecha_t2 =  doc.getElementById("home_t2").select("p")
                            val fecha_lg =  doc.getElementById("home_grande").select("p")

                            try{

                                fecha1Diaria.text = fechaDiaria[0].html().split("<br>")[1]
                                fecha2Diaria.text =  fechaDiaria[1].html().split("<br>")[1]
                                fecha3Diaria.text =  fechaDiaria[2].html().split("<br>")[1]
                                hora1Diaria.text = fechaDiaria[0].html().split("<br>")[0]
                                hora2Diaria.text = fechaDiaria[1].html().split("<br>")[0]
                                hora3Diaria.text = fechaDiaria[2].html().split("<br>")[0]

                                fecha1Juega3.text = fechaDiaria[0].html().split("<br>")[1]
                                fecha2Juega3.text = fechaDiaria[1].html().split("<br>")[1]
                                fecha3Juega3.text = fechaDiaria[2].html().split("<br>")[1]
                                hora1Juega3.text = fechaDiaria[0].html().split("<br>")[0]
                                hora2Juega3.text = fechaDiaria[1].html().split("<br>")[0]
                                hora3Juega3.text = fechaDiaria[2].html().split("<br>")[0]


                                fecha1Combo.text = fechaDiaria[0].html().split("<br>")[1]
                                fecha2Combo.text = fechaDiaria[1].html().split("<br>")[1]
                                fecha3Combo.text = fechaDiaria[2].html().split("<br>")[1]
                                hora1Combo.text = fechaDiaria[0].html().split("<br>")[0]
                                hora2Combo.text = fechaDiaria[1].html().split("<br>")[0]
                                hora3Combo.text = fechaDiaria[2].html().split("<br>")[0]

                                fechaTerminacion2.text = fecha_t2[0].text()
                                fechaLaGrande.text = fecha_lg[0].text()
                            }catch (e: Exception){
                                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                            }

                            if(diaria.isNotEmpty()) {
                                ganador1Diaria.text = diaria[0].text()
                                ganador2Diaria.text = diaria[1].text()
                                ganador3Diaria.text = diaria[2].text()
                            }
                            if(juega3.isNotEmpty()) {
                                ganador1Juega3.text = juega3[0].text()
                                ganador2Juega3.text = juega3[1].text()
                                ganador3Juega3.text = juega3[2].text()
                            }
                            if(supercombo1.isNotEmpty()) {
                                p1ganador1Combo.text = supercombo1[0].text()
                                p1ganador2Combo.text = supercombo1[2].text()
                                p1ganador3Combo.text = supercombo1[4].text()
                                p2ganador1Combo.text = supercombo1[1].text()
                                p2ganador2Combo.text = supercombo1[3].text()
                                p2ganador3Combo.text = supercombo1[5].text()
                            }
                            if(sorteoFechas.isNotEmpty()){
                                try {
                                    ganador_fechas_dia1.text = sorteoFechas[0].text()
                                    ganador_fechas_dia2.text = sorteoFechas[2].text()
                                    ganador_fechas_dia3.text = sorteoFechas[4].text()
                                    ganador_fechas_mes1.text = sorteoFechas[1].text()
                                    ganador_fechas_mes2.text = sorteoFechas[3].text()
                                    ganador_fechas_mes3.text = sorteoFechas[5].text()
                                    fechas_fecha1.text = fechaDiaria[0].html().split("<br>")[1]
                                    fechas_fecha2.text = fechaDiaria[1].html().split("<br>")[1]
                                    fechas_fecha3.text = fechaDiaria[2].html().split("<br>")[1]
                                    txtHora1Fechas.text = fechaDiaria[0].html().split("<br>")[0]
                                    txtHora2Fechas.text = fechaDiaria[1].html().split("<br>")[0]
                                    txtHora3Fechas.text = fechaDiaria[2].html().split("<br>")[0]

                                }catch (e: Exception){}
                            }

                            if(terminacion2.isNotEmpty()) {
                                ganadorTerminacion2.text = terminacion2[0].text()
                            }
                            if(lagrande.isNotEmpty()) {
                                lg1.text = lagrande[0].text()
                                lg2.text = lagrande[1].text()
                                lg3.text = lagrande[2].text()
                                lg4.text = lagrande[3].text()
                                lg5.text = lagrande[4].text()
                                lgOro.text = lagrande[5].text()
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
        val nativeCode = getString(R.string.ads_native)
        val builder = AdLoader.Builder(requireContext(), nativeCode)

        builder.forNativeAd { nativeAd ->
            val adBinding = AdNativeLayoutBinding.inflate(layoutInflater)
            //val nativeAdview = AdNativeLayoutBinding.inflate(layoutInflater).root
            binding.adViewContainer.removeAllViews()
            binding.adViewContainer.addView(populateNativeAd(nativeAd, adBinding))
        }

        val adLoader = builder.withAdListener(object : AdListener(){
            override fun onAdFailedToLoad(p0: LoadAdError?) {
                super.onAdFailedToLoad(p0)
                Log.e("EDER", "${p0?.message}: ${p0?.cause.toString()}")
            }
        }).build()
        adLoader.loadAd(AdRequest.Builder().build())
    }

    private fun populateNativeAd(nativeAd: NativeAd, adView: AdNativeLayoutBinding): NativeAdView {
        val nativeAdView = adView.root
        with(adView){
            adHeadline.text = nativeAd.headline
            nativeAdView.headlineView = adHeadline
            nativeAd.advertiser?.let {
                adAdvertiser.text = it
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
            adMedia.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
            adMedia.visibility = View.VISIBLE
            nativeAdView.mediaView = adMedia
        }
        nativeAdView.setNativeAd(nativeAd)
        return nativeAdView
    }


}