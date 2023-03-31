package com.resultados.loto.lotonicaragua.data

import java.util.*

data class LotoResult(
        var code:String = "",
        var date:String = "",
        var time:String = "",
        var result1:Int = -1,
        var result2:Int = -1,
        var result3:Int = -1,
        var result4:Int = -1,
        var result5:Int = -1,
        var result6:Int = -1,
        var month:String = "",
        var multix:String = "",
        var game: Int = 0
        )