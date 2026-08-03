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
import com.resultados.loto.lotonicaragua.R
import com.resultados.loto.lotonicaragua.ScopeFragment
import com.resultados.loto.lotonicaragua.data.RequestResult
import com.resultados.loto.lotonicaragua.data.api.models.NumbersFrequency
import com.resultados.loto.lotonicaragua.data.repo.RepoResults
import com.resultados.loto.lotonicaragua.databinding.FragmentDiariaStatsBinding
import com.resultados.loto.lotonicaragua.setHidden
import com.resultados.loto.lotonicaragua.setupBarChartStyle
import com.resultados.loto.lotonicaragua.ui.ads.NativeAdCard
import com.resultados.loto.lotonicaragua.ui.theme.LotoTheme
import kotlinx.coroutines.launch

class DiariaStatsFragment : ScopeFragment() {

    private lateinit var galleryViewModel: GalleryViewModel
    private lateinit var navController: NavController
    private lateinit var binding: FragmentDiariaStatsBinding
    private lateinit var repo: RepoResults

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDiariaStatsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        requireActivity().onBackPressedDispatcher.addCallback(this){ navController.navigateUp()}
        repo = RepoResults(requireContext())
        /*
        binding.nativeAdComposeView.setContent {
            LotoTheme {
                NativeAdCard()
            }
        }*/

        launch {
            try {
                val res = repo.fetchStatsDiaria()
                if (res is RequestResult.StatsDiaria) {
                    binding.progressBar2.setHidden()
                    binding.total.text = "Estad\u00edsticas de los \u00faltimos ${res.stats.total} sorteos"
                    val labels = listOf(
                        "0-9", "10-19", "20-29", "30-39", "40-49",
                        "50-59", "60-69", "70-89", "80-89", "90-99"
                    )
                    val labels2: MutableList<String> = ArrayList()
                    res.stats.numbersFrequency.forEach {
                        labels2.add(
                            it.number.toString()
                        )
                    }
                    binding.histogram.setupBarChartStyle(labels)
                    binding.histogram2.setupBarChartStyle(labels2)

                    binding.histogram.data = buildHistogramDataSet(res.stats.histogram)
                    binding.histogram2.data =
                        buildHistogramDataSet2(res.stats.numbersFrequency)
                }
            }catch (e:Exception){
                binding.progressBar2.setHidden()
                Toast.makeText(requireContext(), "Error al cargar los datos", Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun buildHistogramDataSet(hist:List<Int>): BarData {
        val entries: MutableList<BarEntry> = ArrayList()
        hist.forEachIndexed{ i, e  ->
            entries.add(BarEntry(i.toFloat(), e.toFloat()))
        }
        val pDataSet = BarDataSet(entries, "Numero de coincidencias")
        pDataSet.color = ContextCompat.getColor(requireContext(), R.color.primaryDarkColor)
        pDataSet.valueTypeface = ResourcesCompat.getFont(requireContext(), R.font.source_sans_pro_semibold)
        pDataSet.valueTextSize = 10f

        val dataSets: MutableList<IBarDataSet> = ArrayList()
        dataSets.add(pDataSet)
        val data = BarData(dataSets)

        return  data

    }

    private fun buildHistogramDataSet2(hist:List<NumbersFrequency>): BarData {
        val entries: MutableList<BarEntry> = ArrayList()
        hist.forEachIndexed{ i, e  ->
            entries.add(BarEntry(i.toFloat(), e.frequency.toFloat()))
        }
        val pDataSet = BarDataSet(entries, "Numero de coincidencias")
        pDataSet.color = ContextCompat.getColor(requireContext(), R.color.primaryDarkColor)
        pDataSet.valueTypeface = ResourcesCompat.getFont(requireContext(), R.font.source_sans_pro_semibold)
        pDataSet.valueTextSize = 10f

        val dataSets: MutableList<IBarDataSet> = ArrayList()
        dataSets.add(pDataSet)
        val data = BarData(dataSets)

        return  data

    }

}
