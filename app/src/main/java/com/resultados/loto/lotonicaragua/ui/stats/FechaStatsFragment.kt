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
import com.resultados.loto.lotonicaragua.data.api.models.FechaFrequency
import com.resultados.loto.lotonicaragua.data.repo.RepoResults
import com.resultados.loto.lotonicaragua.databinding.FragmentFechaStatsBinding
import com.resultados.loto.lotonicaragua.databinding.TopFechasItemBinding
import com.resultados.loto.lotonicaragua.setHidden
import com.resultados.loto.lotonicaragua.setupBarChartStyle
import com.resultados.loto.lotonicaragua.ui.ads.NativeAdCard
import com.resultados.loto.lotonicaragua.ui.theme.LotoTheme
import kotlinx.coroutines.launch

class FechaStatsFragment : ScopeFragment() {

    private lateinit var navController: NavController
    private lateinit var binding: FragmentFechaStatsBinding
    private lateinit var repo: RepoResults

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFechaStatsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        requireActivity().onBackPressedDispatcher.addCallback(this){ navController.navigateUp()}
        repo = RepoResults(requireContext())
        binding.nativeAdComposeView.setContent {
            LotoTheme {
                NativeAdCard()
            }
        }
        launch {
            try {
                val res = repo.fetchStatsFechas()
                if (res is RequestResult.StatsFecha) {
                    binding.progressBar2.setHidden()
                    binding.total.text = "Estad\u00edsticas de los \u00faltimos ${res.stats.total} sorteos"
                    val labels = listOf("1-5", "6-10", "11-15", "16-20", "21-25", "26-31")
                    val monthLabels = listOf(
                        "Ene",
                        "Feb",
                        "Mar",
                        "Abr",
                        "May",
                        "Jun",
                        "Jul",
                        "Ago",
                        "Sep",
                        "Oct",
                        "Nov",
                        "Dic"
                    )
                    val labels2: MutableList<String> = ArrayList()

                    res.stats.numbersFrequency.forEach {
                        labels2.add(
                            it.number.toString()
                        )
                    }
                    binding.histogram.setupBarChartStyle(labels)
                    binding.histogram2.setupBarChartStyle(labels2)
                    binding.histogram3.setupBarChartStyle(monthLabels)

                    binding.histogram.data = buildHistogramDataSet(res.stats.histogram)
                    binding.histogram2.data =
                        buildHistogramDataSet2(res.stats.numbersFrequency)
                    binding.histogram3.data = buildHistogramDataSet3(res.stats.monthHistogram)

                    res.stats.topMixin.forEach {
                        val adBinding = TopFechasItemBinding.inflate(layoutInflater)
                        adBinding.month.text = it.month
                        adBinding.day.text = it.day
                        adBinding.count.text = it.count
                        binding.mainContainer.addView(adBinding.root)
                    }
                }
            }catch (e:Exception){
                Toast.makeText(requireContext(), "Error al cargar los datos", Toast.LENGTH_LONG).show()
                binding.progressBar2.setHidden()
            }
        }
    }


    private fun buildHistogramDataSet(hist:List<Float>): BarData {
        val entries: MutableList<BarEntry> = ArrayList()
        hist.forEachIndexed{ i, e  ->
            entries.add(BarEntry(i.toFloat(), e))
        }
        val pDataSet = BarDataSet(entries, "Numero de coincidencias")
        pDataSet.color = ContextCompat.getColor(requireContext(), R.color.primaryDarkColor)
        pDataSet.valueTypeface = ResourcesCompat.getFont(requireContext(), R.font.source_sans_pro_semibold)
        pDataSet.valueTextSize = 10f

        val dataSets: MutableList<IBarDataSet> = ArrayList()
        dataSets.add(pDataSet)

        return BarData(dataSets)

    }

    private fun buildHistogramDataSet3(hist:List<Float>): BarData {
        val entries: MutableList<BarEntry> = ArrayList()
        hist.forEachIndexed{ i, e  ->
            entries.add(BarEntry(i.toFloat(), e))
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

    private fun buildHistogramDataSet2(hist:List<FechaFrequency>): BarData {
        val entries: MutableList<BarEntry> = ArrayList()
        hist.forEachIndexed{ i, e  ->
            entries.add(BarEntry(i.toFloat(), e.freq.toFloat()))
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
