package com.resultados.loto.lotonicaragua.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.resultados.loto.lotonicaragua.*
import com.resultados.loto.lotonicaragua.adapters.LagrandeAdapter
import com.resultados.loto.lotonicaragua.adapters.ResultsAdapter
import com.resultados.loto.lotonicaragua.data.RequestResult
import com.resultados.loto.lotonicaragua.data.repo.RepoResults
import com.resultados.loto.lotonicaragua.databinding.FragmentPreviousBinding
import com.resultados.loto.lotonicaragua.ui.home.ResultsViewModel
import kotlinx.coroutines.launch
import java.net.UnknownHostException


class PreviousResultsFragment : ScopeFragment() {

    private lateinit var homeViewModel: ResultsViewModel
    private lateinit var navController: NavController
    private lateinit var binding: FragmentPreviousBinding

    private lateinit var adapter: ResultsAdapter
    private lateinit var adapter2: LagrandeAdapter
    val mArgs: PreviousResultsFragmentArgs by navArgs()

    private lateinit var repo: RepoResults

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
        repo = RepoResults(requireContext())

    }

    private fun initLayout() {
        adapter = ResultsAdapter()
        binding.resultRecycler.adapter = adapter
    }

    private fun cargarResultados(){
        launch{
            try {
                binding.loadingIndicator.setVisible()
                binding.resultRecycler.setHidden()
                showLoading()
                when (mArgs.sorteo) {
                    ScraperHelper.DIARIA -> {
                        when(val data = repo.fetchDiaria()){
                            is RequestResult.Diaria -> adapter.submitList(ScraperHelper.apiDiaria(data.results))
                            is RequestResult.Failure -> {
                                showError("Error al consultar los resultados",
                                    "${data.status}: ${data.text}\n\n",
                                    R.raw.error_animation, false)
                            }
                        }
                    }
                    ScraperHelper.JUEGA3 -> {
                        when(val data = repo.fetchJuega3()){
                            is RequestResult.Diaria -> adapter.submitList(ScraperHelper.apiDiaria(data.results))
                            is RequestResult.Failure -> {
                                showError("Error al consultar los resultados",
                                    "${data.status}: ${data.text}\n\n",
                                    R.raw.error_animation, false)
                            }
                        }
                    }
                    ScraperHelper.FECHAS -> {
                        when(val data = repo.fetchFechas()){
                            is RequestResult.Fechas -> adapter.submitList(ScraperHelper.apiFechas(data.results))
                            is RequestResult.Failure -> {
                                showError("Error al consultar los resultados",
                                    "${data.status}: ${data.text}\n\n",
                                    R.raw.error_animation, false)
                            }
                        }
                    }
                    ScraperHelper.SUPERCOMBO -> {
                        when(val data = repo.fetchCombo()){
                            is RequestResult.Combo -> adapter.submitList(ScraperHelper.apiCombo(data.results))
                            is RequestResult.Failure -> {
                                showError("Error al consultar los resultados",
                                    "${data.status}: ${data.text}\n\n",
                                    R.raw.error_animation, false)
                            }
                        }
                    }
                    ScraperHelper.TERMINACION2 -> {
                        when(val data = repo.fetchTerminacion2()){
                            is RequestResult.Diaria -> adapter.submitList(ScraperHelper.apiDiaria(data.results))
                            is RequestResult.Failure -> {
                                showError("Error al consultar los resultados",
                                    "${data.status}: ${data.text}\n\n",
                                    R.raw.error_animation, false)
                            }
                        }
                    }
                    ScraperHelper.LAGRANDE -> {
                        adapter2 = LagrandeAdapter()
                        binding.resultRecycler.adapter = adapter2
                        when(val data = repo.fetchGrande()){
                            is RequestResult.Grande -> adapter2.submitList(ScraperHelper.apiGrande(data.results))
                            is RequestResult.Failure -> {
                                showError("Error al consultar los resultados",
                                    "${data.status}: ${data.text}\n\n",
                                    R.raw.error_animation, false)
                            }
                        }
                    }
                }
                binding.loadingIndicator.setHidden()
                binding.resultRecycler.setVisible()

                /*
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

                 */

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