package com.resultados.loto.lotonicaragua.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class ResultsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private var response:Connection.Response? = null
    private var response2:Connection.Response? = null

    suspend fun getConnection(): Connection.Response? = withContext(Dispatchers.IO){
        val remoteConfig = Firebase.remoteConfig
        val url = remoteConfig.getString("loto_url")
        response = Jsoup.connect(url).ignoreHttpErrors(true).execute()
        return@withContext response
    }
    suspend fun getResultsContent(): Document? = withContext(Dispatchers.IO){
        return@withContext response?.parse()
    }


    suspend fun getPreviousResults(): Connection.Response? = withContext(Dispatchers.IO){
        val url = "http://64.225.47.75:7000/previous"
        response2 = Jsoup.connect(url).ignoreHttpErrors(true).execute()
        return@withContext response2
    }
    suspend fun getPreviousResultsContent(): Document? = withContext(Dispatchers.IO){
        return@withContext response2?.parse()
    }
}