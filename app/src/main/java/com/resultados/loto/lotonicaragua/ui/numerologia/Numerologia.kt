package com.resultados.loto.lotonicaragua.ui.numerologia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.resultados.loto.lotonicaragua.R

class Numerologia : Fragment() {

    private lateinit var sendViewModel: SendViewModel
    private lateinit var compose: ComposeView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sendViewModel =
                ViewModelProviders.of(this).get(SendViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_numbers, container, false)
        compose = root.findViewById(R.id.lucky_numbers)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        compose.setContent {
            LuckyNumbers()
        }
    }
}