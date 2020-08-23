package com.resultados.loto.lotonicaragua.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.airbnb.lottie.LottieAnimationView
import com.resultados.loto.lotonicaragua.ScopeFragment
import com.resultados.loto.lotonicaragua.R
import com.resultados.loto.lotonicaragua.setHidden
import com.resultados.loto.lotonicaragua.setVisible
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.UnknownHostException


class ResultsFragment : ScopeFragment() {

    private lateinit var homeViewModel: ResultsViewModel

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
    ): View? {
        setHasOptionsMenu(true)
        //binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        homeViewModel = ViewModelProviders.of(this).get(ResultsViewModel::class.java)
        return inflater.inflate(R.layout.fragment_home, container, false)
        //return binding.root
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        resultsContainer.setHidden()
        cargarResultados()
    }

    private fun initViews() {
        resultsContainer = requireActivity().findViewById(R.id.results_container)
        loadingIndicator = requireActivity().findViewById(R.id.loading_indicator)
        lottie = requireActivity().findViewById(R.id.animation_view)
        messageTitle = requireActivity().findViewById(R.id.message_title)
        messageBody = requireActivity().findViewById(R.id.message_body)

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

    }

    private fun cargarResultados(){
        launch(Dispatchers.IO) {
            try {
                loadingIndicator.setVisible()
                resultsContainer.setHidden()
                //binding.loadingIndicator.fadeZoomIn()
                showLoading()
                val doc = homeViewModel.getLastResults()
                doc?.let {
                    val diaria = doc.getElementById("home_diaria").select("span>strong")

                    val juega3 = doc.getElementById("home_juga3").select("span>strong")
                    val supercombo1 = doc.getElementById("home_combo").select("span>strong")

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
                    }catch (e:Exception){
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
                    loadingIndicator.setHidden()
                    resultsContainer.setVisible()
                    //binding.resultsContainer.scaleIn()
                }
            }catch (e: UnknownHostException){
                //binding.resultsContainer.fadeOut()
                //binding.resultsContainer.scaleOut()
                resultsContainer.setHidden()
                showError("Error de conexión",
                    "No fue posible acceder a los resultados",
                    R.raw.women_no_internet, true)
            }
            catch (e:Exception){
                resultsContainer.setHidden()
                val errMessage = if(e.message!=null)
                    e.message!!
                else
                    "Error desconocido"
                showError("Ha ocurrido un error", errMessage, R.raw.error_animation, false)
            }

        }
    }

    private fun showError(title:String, message:String, animation:Int, loop:Boolean){
        lottie.setAnimation(animation)
        lottie.loop(loop)
        lottie.playAnimation()
        messageTitle.text = title
        messageBody.text = message
    }

    private fun showLoading(){
        lottie.setAnimation(R.raw.search_animation)
        lottie.loop(true)
        lottie.playAnimation()
        messageTitle.text = "Consultando resultados"
        messageBody.text = "Por favor espera."
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_refresh->{
                cargarResultados()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}