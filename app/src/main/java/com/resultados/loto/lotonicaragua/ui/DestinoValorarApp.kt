package com.resultados.loto.lotonicaragua.ui

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator

@Suppress("DEPRECATION")
@Navigator.Name("valorar_app")
class DestinoValorarApp (var myContext: Context): ActivityNavigator(myContext) {
    override fun navigate(destination: Destination, args: Bundle?, navOptions: NavOptions?, navigatorExtras: Navigator.Extras?): NavDestination? {
        //super.navigate(destination, args, navOptions, navigatorExtras)

        val uri = Uri.parse("market://details?id=" + myContext.packageName)
        val goToMarket = Intent(Intent.ACTION_VIEW, uri)
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or
                Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET or
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
        try {
            myContext.startActivity(goToMarket)
        } catch (e: ActivityNotFoundException) {
            myContext.startActivity(Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + myContext.packageName)))
        }
        return null
    }
}