package com.resultados.loto.lotonicaragua.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import com.resultados.loto.lotonicaragua.R


@Navigator.Name("compartir_app")
class DestinoCompartirApp (var myContext: Context): ActivityNavigator(myContext) {
    override fun navigate(destination: Destination, args: Bundle?, navOptions: NavOptions?, navigatorExtras: Navigator.Extras?): NavDestination? {
        //super.navigate(destination, args, navOptions, navigatorExtras)

        val i = Intent(Intent.ACTION_SEND)
        i.type = "text/plain"
        i.putExtra(Intent.EXTRA_SUBJECT, myContext.resources.getString(R.string.app_name))
        val sAux = "https://play.google.com/store/apps/details?id=" + myContext.packageName + " \n\n"
        i.putExtra(Intent.EXTRA_TEXT, sAux)
        myContext.startActivity(Intent.createChooser(i, myContext.resources.getString(R.string.share_app_msg)))
        return null
    }
}