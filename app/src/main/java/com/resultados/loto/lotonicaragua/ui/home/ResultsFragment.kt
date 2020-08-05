package com.resultados.loto.lotonicaragua.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.crecimiento.tablas.percentiles.oms.ui.ScopeFragment
import com.resultados.loto.lotonicaragua.*
import com.resultados.loto.lotonicaragua.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.card_diaria.*
import kotlinx.android.synthetic.main.card_juega3.*
import kotlinx.android.synthetic.main.card_lagrande.*
import kotlinx.android.synthetic.main.card_supercombo.*
import kotlinx.android.synthetic.main.card_terminacion2.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch
import java.net.UnknownHostException


class ResultsFragment : ScopeFragment() {

    private lateinit var homeViewModel: ResultsViewModel
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        homeViewModel = ViewModelProviders.of(this).get(ResultsViewModel::class.java)
        //return inflater.inflate(R.layout.fragment_home, container, false)
        return binding.root
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.resultsContainer.setHidden()
        cargarResultados()
    }

    private fun cargarResultados(){
        launch {
            try {
                binding.loadingIndicator.setVisible()
                binding.resultsContainer.setHidden()
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

                        binding.diaria.fechaDiaria10.text = fechaDiaria[0].html().split("<br>")[1]
                        binding.diaria.fechaDiaria2.text =  fechaDiaria[1].html().split("<br>")[1]
                        binding.diaria.fechaDiaria9.text =  fechaDiaria[2].html().split("<br>")[1]

                        binding.juega3.fechaJuega310.text = fechaDiaria[0].html().split("<br>")[1]
                        binding.juega3.fechaJuega32.text = fechaDiaria[1].html().split("<br>")[1]
                        binding.juega3.fechaJuega39.text = fechaDiaria[2].html().split("<br>")[1]


                        binding.supercombo.fechaSupercombo10.text = fechaDiaria[0].html().split("<br>")[1]
                        binding.supercombo.fechaSupercombo2.text = fechaDiaria[1].html().split("<br>")[1]
                        binding.supercombo.fechaSupercombo9.text = fechaDiaria[2].html().split("<br>")[1]

                       binding.terminacion2.fechaTerminacion2.text = fecha_t2[0].text()
                       binding.laGrande.fechaLagrande.text = fecha_lg[0].text()

                    }catch (e:Exception){
                        Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                    }

                    if(diaria.isNotEmpty()) {
                        with(binding.diaria){
                            ganadorDiaria10.text = diaria[0].text()
                            ganadorDiaria2.text = diaria[1].text()
                            ganadorDiaria9.text = diaria[2].text()
                        }
                    }
                    if(juega3.isNotEmpty()) {
                        with(binding.juega3){

                            ganadorJuega310.text = juega3[0].text()
                            ganadorJuega32.text = juega3[1].text()
                            ganadorJuega39.text = juega3[2].text()
                        }
                    }
                    if(supercombo1.isNotEmpty()) {
                        with(binding.supercombo){
                            ganadorScomboP110.text = supercombo1[0].text()
                            ganadorScomboP12.text = supercombo1[2].text()
                            ganadorScomboP19.text = supercombo1[4].text()

                            ganadorScomboP210.text = supercombo1[1].text()
                            ganadorScomboP22.text = supercombo1[3].text()
                            ganadorScomboP29.text = supercombo1[5].text()
                        }
                    }


                    if(terminacion2.isNotEmpty()) {
                        binding.terminacion2.ganadorTerminacion2.text = terminacion2[0].text()
                    }
                    if(lagrande.isNotEmpty()) {
                        with(binding.laGrande){
                            ganadorLg1.text = lagrande[0].text()
                            ganadorLg2.text = lagrande[1].text()
                            ganadorLg3.text = lagrande[2].text()
                            ganadorLg4.text = lagrande[3].text()
                            ganadorLg5.text = lagrande[4].text()
                            ganadorLgOro.text = lagrande[5].text()
                        }
                    }
                    binding.loadingIndicator.setHidden()
                    binding.resultsContainer.setVisible()
                    //binding.resultsContainer.scaleIn()
                }
            }catch (e: UnknownHostException){
                //binding.resultsContainer.fadeOut()
                //binding.resultsContainer.scaleOut()
                binding.resultsContainer.setHidden()
                showError("Error de conexión",
                    "No fue posible acceder a los resultados",
                    R.raw.women_no_internet, true)
            }
            catch (e:Exception){
                //binding.resultsContainer.fadeOut()
                //binding.resultsContainer.scaleOut()
                binding.resultsContainer.setHidden()
                val errMessage = if(e.message!=null)
                    e.message!!
                else
                    "Error desconocido"
                showError("Ha ocurrido un error", errMessage, R.raw.error_animation, false)
            }

        }
    }

    private fun showError(title:String, message:String, animation:Int, loop:Boolean){
        binding.animationView.setAnimation(animation)
        binding.animationView.loop(loop)
        binding.animationView.playAnimation()
        binding.messageTitle.text = title
        binding.messageBody.text = message
    }

    private fun showLoading(){
        binding.animationView.setAnimation(R.raw.search_animation)
        binding.animationView.loop(true)
        binding.animationView.playAnimation()
        binding.messageTitle.text = "Consultando resultados"
        binding.messageBody.text = "Por favor espera."
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