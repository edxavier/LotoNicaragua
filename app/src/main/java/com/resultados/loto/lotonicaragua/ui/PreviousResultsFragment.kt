package com.resultados.loto.lotonicaragua.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.google.android.gms.ads.InterstitialAd
import com.resultados.loto.lotonicaragua.*
import com.resultados.loto.lotonicaragua.databinding.FragmentHomeBinding
import com.resultados.loto.lotonicaragua.databinding.FragmentPreviousBinding
import com.resultados.loto.lotonicaragua.ui.home.ResultsViewModel
import kotlinx.android.synthetic.main.card_fechas.*
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import java.util.*


class PreviousResultsFragment : ScopeFragment() {

    private lateinit var homeViewModel: ResultsViewModel
    private lateinit var navController: NavController
    private lateinit var binding: FragmentPreviousBinding

    private lateinit var adapter:ResultsAdapter
    val mArgs: PreviousResultsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        //binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        homeViewModel = ViewModelProvider(requireActivity()).get(ResultsViewModel::class.java)
        binding = FragmentPreviousBinding.inflate(inflater)
        //return inflater.inflate(R.layout.fragment_previous, container, false)
        return binding.root
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        requireActivity().onBackPressedDispatcher.addCallback(this){ navController.navigateUp()}
        initLayout()
        cargarResultados()

    }

    private fun initLayout() {
        adapter = ResultsAdapter()
        binding.resultRecycler.adapter = adapter
    }

    private fun cargarResultados(){

        launch{
            try {
                //loadingIndicator.setVisible()
                //resultsContainer.setHidden()
                //binding.loadingIndicator.fadeZoomIn()
                binding.loadingIndicator.setVisible()
                binding.resultRecycler.setHidden()
                showLoading()

                val response = homeViewModel.getPreviousResults()

                when {
                    response==null -> {
                        showError("Ha ocurrido un error", "No se recibio respuesta del servidor",
                            R.raw.error_animation, false)
                        return@launch
                    }
                    response.statusCode()==200 -> {
                        val doc = homeViewModel.getPreviousResultsContent()
                        doc?.let {
                            binding.loadingIndicator.setHidden()
                            binding.resultRecycler.setVisible()
                            when (mArgs.sorteo) {
                                ScraperHelper.DIARIA -> adapter.submitList(ScraperHelper.scrapDiaria(it))
                                ScraperHelper.JUEGA3 -> adapter.submitList(ScraperHelper.scrapJuega3(it))
                                ScraperHelper.FECHAS -> adapter.submitList(ScraperHelper.scrapFechas(it))
                                ScraperHelper.SUPERCOMBO -> adapter.submitList(ScraperHelper.scrapSuperCombo(it))
                                ScraperHelper.TERMINACION2 -> adapter.submitList(ScraperHelper.scrapTerminacion2(it))
                            }
                        }
                    }
                    else -> {
                        val codeDescription = getCodeDescription(response.statusCode())
                        showError("Error al consultar los resultados",
                            "${response.statusCode()}: ${response.statusMessage()}\n\n $codeDescription",
                            R.raw.error_animation, false)
                    }
                }


            }catch (e: UnknownHostException){
                //resultsContainer.setHidden()
                showError("Error de conexión",
                    "No fue posible acceder a los resultados",
                    R.raw.women_no_internet, true)
            } catch (e:Exception){
                //Log.e("EDER", e.toString())
                //resultsContainer.setHidden()
                val errMessage = if(e.message!=null)
                    e.message!!
                else
                    "Error desconocido"
                showError("Ha ocurrido un error", errMessage, R.raw.error_animation, false)
            }

        }
    }

    private fun getCodeDescription(statusCode: Int): String {
        return when(statusCode){
            400-> "La solicitud no se puede cumplir debido a una sintaxis incorrecta"
            403-> "El servidor se niega a responder. El recurso no está disponible por alguna razón"
            404-> "No se pudo encontrar el recurso solicitado."
            500-> "El servidor encontró una condición inesperada que le impidió cumplir con la solicitud."
            502-> "El servidor estaba actuando como puerta de enlace o proxy y recibió una respuesta no válida del servidor ascendente."
            else-> "Error de servidor desconocido"
        }
    }

    private fun showError(title:String, message:String, animation:Int, loop:Boolean){
        binding.animationView.setAnimation(animation)
        binding.animationView.loop(loop)
        binding.animationView.playAnimation()
        binding.messageTitle.text = title
        binding.messageBody.text = message
    }

    @SuppressLint("SetTextI18n")
    private fun showLoading(){
        binding.animationView.setAnimation(R.raw.loading)
        binding.animationView.loop(true)
        binding.animationView.playAnimation()
        binding.messageTitle.text = "Examinando resultados anteriores"
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