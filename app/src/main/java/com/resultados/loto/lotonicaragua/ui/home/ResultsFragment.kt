package com.resultados.loto.lotonicaragua.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.crecimiento.tablas.percentiles.oms.ui.ScopeFragment
import com.resultados.loto.lotonicaragua.R
import kotlinx.android.synthetic.main.card_diaria.*
import kotlinx.android.synthetic.main.card_juega3.*
import kotlinx.android.synthetic.main.card_lagrande.*
import kotlinx.android.synthetic.main.card_supercombo.*
import kotlinx.android.synthetic.main.card_terminacion2.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch


class ResultsFragment : ScopeFragment() {

    private lateinit var homeViewModel: ResultsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        homeViewModel = ViewModelProviders.of(this).get(ResultsViewModel::class.java)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
     cargarResultados()
    }

    private fun cargarResultados(){

        launch {
            try {
                loading_indicator.visibility = View.VISIBLE
                val doc = homeViewModel.getLastResults()
                doc?.let {
                    loading_indicator.visibility = View.GONE
                    val diaria = doc.getElementsByClass("ball_y1-new")
                    val juega3 = doc.getElementsByClass("ball_j3")
                    val supercombo1 = doc.getElementsByClass("ball_w2")
                    val supercombo2 = doc.getElementsByClass("ball_y2")
                    val terminacion2 = doc.getElementsByClass("ball_y1")
                    val lagrande = doc.getElementsByClass("ball_w3")
                    val lagrandeOro = doc.getElementsByClass("ball_g2")


                    val fecha_d = doc.getElementsByClass("sorteo_date_diaria_hijo")
                    val fecha_t2 = doc.getElementsByClass("sorteo_date")

                    try{
                        fecha_diaria_10.text = fecha_d[0].html().split("<br>")[1]
                        fecha_diaria_2.text =  fecha_d[1].html().split("<br>")[1]
                        fecha_diaria_9.text =  fecha_d[2].html().split("<br>")[1]

                        fecha_juega3_10.text = fecha_d[0].html().split("<br>")[1]
                        fecha_juega3_2.text = fecha_d[1].html().split("<br>")[1]
                        fecha_juega3_9.text = fecha_d[2].html().split("<br>")[1]

                        fecha_supercombo_10.text = fecha_d[0].html().split("<br>")[1]
                        fecha_supercombo_2.text = fecha_d[1].html().split("<br>")[1]
                        fecha_supercombo_9.text = fecha_d[2].html().split("<br>")[1]

                        fecha_terminacion2.text = fecha_t2[3].text()
                        fecha_lagrande.text = fecha_t2[4].text()

                    }catch (e:Exception){ }
                    if(diaria.isNotEmpty()) {
                        ganador_diaria_10.text = diaria[0].text()
                        ganador_diaria_2.text = diaria[1].text()
                        ganador_diaria_9.text = diaria[2].text()
                    }
                    if(juega3.isNotEmpty()) {
                        ganador_juega3_10.text = juega3[0].text()
                        ganador_juega3_2.text = juega3[1].text()
                        ganador_juega3_9.text = juega3[2].text()
                    }
                    if(supercombo1.isNotEmpty()) {
                        ganador_scombo_p1_10.text = supercombo1[0].text()
                        ganador_scombo_p1_2.text = supercombo1[1].text()
                        ganador_scombo_p1_9.text = supercombo1[2].text()
                    }
                    if(supercombo2.isNotEmpty()) {
                        ganador_scombo_p2_10.text = supercombo2[0].text()
                        ganador_scombo_p2_2.text = supercombo2[1].text()
                        ganador_scombo_p2_9.text = supercombo2[2].text()
                    }
                    if(terminacion2.isNotEmpty()) {
                        ganador_terminacion2.text = terminacion2[0].text()
                    }
                    if(lagrande.isNotEmpty()) {
                        ganador_lg1.text = lagrande[0].text()
                        ganador_lg2.text = lagrande[1].text()
                        ganador_lg3.text = lagrande[2].text()
                        ganador_lg4.text = lagrande[3].text()
                        ganador_lg5.text = lagrande[4].text()
                    }
                    if(lagrandeOro.isNotEmpty()) {
                        ganador_lg_oro.text = lagrandeOro[0].text()
                    }
                }
            }catch (e:Exception){
                loading_indicator.visibility = View.GONE
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            }

        }
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