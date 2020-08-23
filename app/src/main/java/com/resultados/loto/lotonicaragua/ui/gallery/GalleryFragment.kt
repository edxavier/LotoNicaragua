package com.resultados.loto.lotonicaragua.ui.gallery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.resultados.loto.lotonicaragua.ScopeFragment
import com.resultados.loto.lotonicaragua.R
import kotlinx.coroutines.launch

class GalleryFragment : ScopeFragment() {

    private lateinit var galleryViewModel: GalleryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel::class.java)
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launch {
            val doc = galleryViewModel.getLastMonthResults()
            Log.e("EDER", "onViewCreated");
            doc?.let {
                Log.e("EDER", doc.toString())
            }
        }
    }
}