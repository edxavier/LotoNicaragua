package com.resultados.loto.lotonicaragua.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.resultados.loto.lotonicaragua.*
import com.resultados.loto.lotonicaragua.data.RequestResult
import com.resultados.loto.lotonicaragua.data.repo.RepoResults
import com.resultados.loto.lotonicaragua.databinding.FragmentPreviousBinding
import com.resultados.loto.lotonicaragua.ui.home.ResultsViewModel
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.net.UnknownHostException


class PreviousResultsFragment : ScopeFragment() {

    private lateinit var homeViewModel: ResultsViewModel
    private lateinit var navController: NavController
    private lateinit var binding: FragmentPreviousBinding

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
        navController = findNavController()
        requireActivity().onBackPressedDispatcher.addCallback(this){ navController.navigateUp()}
        repo = RepoResults(requireContext())
        cargarResultados()
    }

    private fun cargarResultados(){
        launch{
            try {
                binding.loadingIndicator.setVisible()
                binding.previousCompose.setHidden()
                showLoading()
                when (mArgs.sorteo) {
                    ScraperHelper.DIARIA -> {
                        when(val data = repo.fetchDiaria()){
                            is RequestResult.Diaria -> {
                                binding.previousCompose.setContent {
                                    PreviousResults(ScraperHelper.apiDiaria(data.results))
                                }
                            }
                            is RequestResult.Failure -> {
                                showError("Error al consultar los resultados",
                                    "${data.status}: ${data.text}\n\n",
                                    R.raw.error_animation, false)
                            }
                            else -> {}
                        }
                    }
                    ScraperHelper.JUEGA3 -> {
                        when(val data = repo.fetchJuega3()){
                            is RequestResult.Base -> {
                                binding.previousCompose.setContent {
                                    PreviousResults(ScraperHelper.apiBase(data.results))
                                }
                            }
                            is RequestResult.Failure -> {
                                showError("Error al consultar los resultados",
                                    "${data.status}: ${data.text}\n\n",
                                    R.raw.error_animation, false)
                            }
                            else -> {
                                //Log.e("EDER", "JEUGA3 ELSE")
                            }
                        }
                    }
                    ScraperHelper.FECHAS -> {
                        when(val data = repo.fetchFechas()){
                            is RequestResult.Fechas -> {
                                binding.previousCompose.setContent {
                                    PreviousResults(ScraperHelper.apiFechas(data.results))
                                }
                            }
                            is RequestResult.Failure -> {
                                showError(
                                    "Error al consultar los resultados",
                                    "${data.status}: ${data.text}\n\n",
                                    R.raw.error_animation, false
                                )
                            }
                            else -> {}
                        }
                    }
                    ScraperHelper.SUPERCOMBO -> {
                        when(val data = repo.fetchCombo()){
                            is RequestResult.Combo -> {
                                binding.previousCompose.setContent {
                                    PreviousResults(ScraperHelper.apiCombo(data.results))
                                }
                            }
                            is RequestResult.Failure -> {
                                showError("Error al consultar los resultados",
                                    "${data.status}: ${data.text}\n\n",
                                    R.raw.error_animation, false)
                            }else -> {}
                        }
                    }
                    ScraperHelper.TERMINACION2 -> {
                        when(val data = repo.fetchTerminacion2()){
                            is RequestResult.Base -> {
                                binding.previousCompose.setContent {
                                    PreviousResults(ScraperHelper.apiBase(data.results))
                                }
                            }
                            is RequestResult.Failure -> {
                                showError("Error al consultar los resultados",
                                    "${data.status}: ${data.text}\n\n",
                                    R.raw.error_animation, false)
                            }else -> {}
                        }
                    }
                    ScraperHelper.LAGRANDE -> {
                        when(val data = repo.fetchGrande()){
                            is RequestResult.Grande ->{
                                binding.previousCompose.setContent {
                                    PreviousResults(ScraperHelper.apiGrande(data.results))
                                }
                            }
                            is RequestResult.Failure -> {
                                showError("Error al consultar los resultados",
                                    "${data.status}: ${data.text}\n\n",
                                    R.raw.error_animation, false)
                            }else -> {}
                        }
                    }
                }
                binding.loadingIndicator.setHidden()
                binding.previousCompose.setVisible()

            }catch (e: UnknownHostException){
                //resultsContainer.setHidden()
                showError("Error de conexión",
                    "No fue posible acceder a los resultados",
                    R.raw.women_no_internet, true)
            }
            catch (e: SocketTimeoutException){
                showError("Ha ocurrido un error",
                    "No fue posible conectarse al servidor, tiempo agotado \n Intenta nuevamente por favor",
                    R.raw.error_animation, false)
            }catch (e:Exception){
                Log.e("EDER", e.toString())
                e.printStackTrace()
                //resultsContainer.setHidden()
                val errMessage = if(e.message!=null)
                    e.message!!
                else
                    "Error desconocido"
                showError("Ha ocurrido un error", errMessage, R.raw.error_animation, false)
            }

        }
    }

    /*
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
     */

    private fun showError(title:String, message:String, animation:Int, loop:Boolean){
        binding.animationView.setAnimation(animation)
        binding.animationView.loop(loop)
        binding.animationView.playAnimation()
        binding.messageTitle.text = title
        binding.messageBody.text = message
    }

    @SuppressLint("SetTextI18n")
    private fun showLoading(){
        binding.animationView.setAnimation(R.raw.loading_google_style)
        binding.animationView.loop(true)
        binding.animationView.playAnimation()
        binding.messageTitle.text = "Examinando resultados anteriores"
        binding.messageBody.text = "Por favor espera..."
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
    }
    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_refresh->{
                cargarResultados()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}