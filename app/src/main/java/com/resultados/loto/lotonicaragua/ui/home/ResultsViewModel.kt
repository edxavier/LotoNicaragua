package com.resultados.loto.lotonicaragua.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class ResultsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    suspend fun getLastResults(): Document? = withContext(Dispatchers.IO){
        return@withContext Jsoup.connect("https://www.loto.com.ni/").get()
    }
}