package com.resultados.loto.lotonicaragua.ui.home

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.edit
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.libraries.ads.mobile.sdk.common.AdLoadCallback
import com.google.android.libraries.ads.mobile.sdk.common.LoadAdError
import com.google.android.libraries.ads.mobile.sdk.common.AdRequest
import com.google.android.libraries.ads.mobile.sdk.interstitial.InterstitialAd
import com.google.android.libraries.ads.mobile.sdk.interstitial.InterstitialAdEventCallback
import com.resultados.loto.lotonicaragua.*
import com.resultados.loto.lotonicaragua.R
import com.resultados.loto.lotonicaragua.data.RequestResult
import com.resultados.loto.lotonicaragua.data.repo.RepoResults
import com.resultados.loto.lotonicaragua.databinding.FragmentHomeBinding
import com.resultados.loto.lotonicaragua.ui.ads.NativeAdCard
import com.resultados.loto.lotonicaragua.ui.ads.AdaptiveBannerAd
import com.resultados.loto.lotonicaragua.ui.home.composes.*
import com.resultados.loto.lotonicaragua.ui.theme.LotoTheme
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

    private lateinit var repoResults: RepoResults

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        binding = FragmentHomeBinding.inflate(inflater)
        homeViewModel = ViewModelProvider(requireActivity()).get(ResultsViewModel::class.java)
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
            LotoTheme(viewModel = homeViewModel) {
                CruzPiramideOptions(onClick = {
                    val action = ResultsFragmentDirections.actionNavHomeToLuckyNumbers()
                    navController.navigate(action)
                })
            }
        }
        cargarResultados()
        /*launch {
            delay(2000)
            showInterstitial()
        }*/
        binding.nativeAdComposeView.setContent {
            LotoTheme(viewModel = homeViewModel) {
                NativeAdCard()
            }
        }
        binding.bannerScrollComposeView.setContent {
            LotoTheme(viewModel = homeViewModel) {
                AdaptiveBannerAd(adUnitId = getString(R.string.ads_banner))
            }
        }
        binding.nativeAdBottomComposeView.setContent {
            LotoTheme(viewModel = homeViewModel) {
                NativeAdCard()
            }
        }

        homeViewModel.showInterstitialEvent.observe(viewLifecycleOwner) {
            showInterstitial()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun cargarResultados() {
        launch {
            try {
                binding.loadingIndicator.setVisible()
                binding.resultsContainer.setHidden()
                showLoading()
                getRecentResults()
            } catch (e: Exception) {
                Log.e("EDER", e.toString())
                binding.resultsContainer.setHidden()
                val errMessage = if (e.message != null)
                    e.message!!
                else
                    "Error desconocido \n Intenta nuevamente por favor"
                showError(
                    "Ha ocurrido un error",
                    "Error desconocido \n $errMessage \n Intenta nuevamente por favor",
                    R.raw.error_animation, false
                )
            }
        }
    }

    private fun showFailUI(title: String, message: String, composeView: ComposeView) {
        composeView.setContent {
            LotoTheme {
                CardNoData(title, message)
            }
        }
    }

    private suspend fun getRecentResults() {
        try {
            val recentResults = repoResults.fetchRecentResults()
            if (recentResults is RequestResult.LotoRecentResults) {
                val results = recentResults.recentResults

                if (results.diaria.isNotEmpty()) {
                    binding.diariaComposeView.setContent {
                        LotoTheme(viewModel = homeViewModel) {
                            CardDiaria(results.diaria, navController)
                        }
                    }
                    delay(80)
                }
                if (results.fechas.isNotEmpty()) {
                    binding.fechasComposeView.setContent {
                        LotoTheme(viewModel = homeViewModel) {
                            CardFechas(results = results.fechas, navController = navController)
                        }
                    }
                    delay(80)
                }
                if (results.juega3.isNotEmpty()) {
                    binding.juga3ComposeView.setContent {
                        LotoTheme(viewModel = homeViewModel) {
                            CardJuega(results = results.juega3, navController = navController)
                        }
                    }
                    delay(80)
                }
                if (results.juega4.isNotEmpty()) {
                    binding.juega4ComposeView.setContent {
                        LotoTheme(viewModel = homeViewModel) {
                            CardJuega4(results = results.juega4, navController = navController)
                        }
                    }
                    delay(80)
                }
                if (results.premia2.isNotEmpty()) {
                    binding.comboComposeView.setContent {
                        LotoTheme(viewModel = homeViewModel) {
                            CardCombo(results = results.premia2, navController = navController)
                        }
                    }
                    delay(80)
                }
                if (results.terminacion.isNotEmpty()) {
                    binding.terminacionComposeView.setContent {
                        LotoTheme(viewModel = homeViewModel) {
                            CardTerminacion(results = results.terminacion, navController = navController)
                        }
                    }
                }
            } else if (recentResults is RequestResult.Failure) {
                val message =
                    "No fue posible conectarse al servidor:${recentResults.status} - ${recentResults.text}"
                showFailUI(
                    "Resultados",
                    message,
                    binding.diariaComposeView
                )
                enviarEmail(message)
            }
        } catch (e: ConnectException) {
            showFailUI("Resultados", "No fue posible conectarse al servidor", binding.diariaComposeView)
        } catch (e: SocketTimeoutException) {
            showFailUI("Resultados", "No fue posible conectarse al servidor", binding.diariaComposeView)
        }
        binding.loadingIndicator.setHidden()
        binding.resultsContainer.setVisible()
    }

    private fun enviarEmail(content: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822"
            putExtra(Intent.EXTRA_EMAIL, arrayOf("edxavier05@gmail.com"))
            putExtra(Intent.EXTRA_SUBJECT, "Fallo al cargar resultados loto")
            putExtra(Intent.EXTRA_TEXT, "$content")
        }

        try {
            startActivity(Intent.createChooser(intent, "Enviar email con..."))
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(requireContext(), "No hay apps de correo instaladas.", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun showError(title: String, message: String, animation: Int, loop: Boolean) {
        binding.loadingIndicator.setVisible()
        binding.animationView.setAnimation(animation)
        binding.animationView.loop(loop)
        binding.animationView.playAnimation()
        binding.messageTitle.text = title
        binding.messageBody.text = message
    }

    @SuppressLint("SetTextI18n")
    private fun showLoading() {
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
                homeViewModel.triggerInterstitial()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun requestInterstitialAds() {
        val adUnitId = resources.getString(R.string.ads_intersticial)
        val request = AdRequest.Builder(adUnitId).build()
        InterstitialAd.load(request, object : AdLoadCallback<InterstitialAd> {
            override fun onAdLoaded(ad: InterstitialAd) {
                mInterstitialAd = ad
            }

            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                mInterstitialAd = null
            }
        })
    }

    private fun showInterstitial() {
        val pref = requireContext().getSharedPreferences("LOTO_PREFS", Context.MODE_PRIVATE)
        val ne = pref.getInt("exec_count", 0)
        pref.edit { putInt("exec_count", ne + 1) }
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

}
