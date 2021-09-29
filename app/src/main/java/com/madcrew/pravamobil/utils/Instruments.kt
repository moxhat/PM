package com.madcrew.pravamobil.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.textfield.TextInputLayout
import com.madcrew.pravamobil.R
import java.text.SimpleDateFormat

fun dateConverter(date: String, context: Context): String {
    var (day, month, year) = date.split(".")
    if (day[0].toString() == "0"){
        day = day[day.length - 1].toString()
    }

    return when (month) {
        "01" -> return "$day ${context.getString(R.string.january)} $year"
        "02" -> return "$day ${context.getString(R.string.february)} $year"
        "03" -> return "$day ${context.getString(R.string.march)} $year"
        "04" -> return "$day ${context.getString(R.string.april)} $year"
        "05" -> return "$day ${context.getString(R.string.may)} $year"
        "06" -> return "$day ${context.getString(R.string.june)} $year"
        "07" -> return "$day ${context.getString(R.string.july)} $year"
        "08" -> return "$day ${context.getString(R.string.august)} $year"
        "09" -> return "$day ${context.getString(R.string.september)} $year"
        "10" -> return "$day ${context.getString(R.string.october)} $year"
        "11" -> return "$day ${context.getString(R.string.november)} $year"
        "12" -> return "$day ${context.getString(R.string.december)} $year"
        else -> "wrong"
    }
}

fun dateConverterForTitle(date: String, context: Context): String {
    var (day, month, year) = date.split(".")
    if (day[0].toString() == "0"){
        day = day[day.length - 1].toString()
    }

    return when (month) {
        "01" -> return "$day ${context.getString(R.string.january)}"
        "02" -> return "$day ${context.getString(R.string.february)}"
        "03" -> return "$day ${context.getString(R.string.march)}"
        "04" -> return "$day ${context.getString(R.string.april)}"
        "05" -> return "$day ${context.getString(R.string.may)}"
        "06" -> return "$day ${context.getString(R.string.june)}"
        "07" -> return "$day ${context.getString(R.string.july)}"
        "08" -> return "$day ${context.getString(R.string.august)}"
        "09" -> return "$day ${context.getString(R.string.september)}"
        "10" -> return "$day ${context.getString(R.string.october)}"
        "11" -> return "$day ${context.getString(R.string.november)}"
        "12" -> return "$day ${context.getString(R.string.december)}"
        else -> "wrong"
    }
}

fun dateConverterSecond(date: String, context: Context): String {
    var (year, month, day) = date.split("-")
    if (day[0].toString() == "0"){
        day = day[day.length - 1].toString()
    }
    return when (month) {
        "01" -> return "$day ${context.getString(R.string.january)} $year"
        "02" -> return "$day ${context.getString(R.string.february)} $year"
        "03" -> return "$day ${context.getString(R.string.march)} $year"
        "04" -> return "$day ${context.getString(R.string.april)} $year"
        "05" -> return "$day ${context.getString(R.string.may)} $year"
        "06" -> return "$day ${context.getString(R.string.june)} $year"
        "07" -> return "$day ${context.getString(R.string.july)} $year"
        "08" -> return "$day ${context.getString(R.string.august)} $year"
        "09" -> return "$day ${context.getString(R.string.september)} $year"
        "10" -> return "$day ${context.getString(R.string.october)} $year"
        "11" -> return "$day ${context.getString(R.string.november)} $year"
        "12" -> return "$day ${context.getString(R.string.december)} $year"
        else -> "wrong"
    }
}

fun dateConverterThird(date: String, context: Context): String {
    var (day, month, year) = date.split("-")
    if (day[0].toString() == "0"){
        day = day[day.length - 1].toString()
    }
    return when (month) {
        "01" -> return "$day ${context.getString(R.string.january)} $year"
        "02" -> return "$day ${context.getString(R.string.february)} $year"
        "03" -> return "$day ${context.getString(R.string.march)} $year"
        "04" -> return "$day ${context.getString(R.string.april)} $year"
        "05" -> return "$day ${context.getString(R.string.may)} $year"
        "06" -> return "$day ${context.getString(R.string.june)} $year"
        "07" -> return "$day ${context.getString(R.string.july)} $year"
        "08" -> return "$day ${context.getString(R.string.august)} $year"
        "09" -> return "$day ${context.getString(R.string.september)} $year"
        "10" -> return "$day ${context.getString(R.string.october)} $year"
        "11" -> return "$day ${context.getString(R.string.november)} $year"
        "12" -> return "$day ${context.getString(R.string.december)} $year"
        else -> "wrong"
    }
}

fun dateConverterSpravka(date: String, context: Context): String {
    var (day, month, year) = date.split(".")
    if (day[0].toString() == "0"){
        day = day[day.length - 1].toString()
    }
    return when (month) {
        "1" -> {
            return if ((day.toInt() + 20) > 31){
                day = (20 - (31 - day.toInt())).toString()
                "$day ${context.getString(R.string.february)} $year"
            } else {
                "$day ${context.getString(R.string.january)} $year"
            }
        }
        "2" -> {
            return if ((day.toInt() + 20) > 28){
                day = (20 - (30 - day.toInt())).toString()
                "$day ${context.getString(R.string.march)} $year"

            } else {
                "$day ${context.getString(R.string.february)} $year"
            }
        }
        "3" -> {
            return if ((day.toInt() + 20) > 31){
                day = (20 - (31 - day.toInt())).toString()
                "$day ${context.getString(R.string.april)} $year"
            } else {
                "$day ${context.getString(R.string.march)} $year"
            }
        }
        "4" -> {
            return if ((day.toInt() + 20) > 30){
                day = (20 - (30 - day.toInt())).toString()
                "$day ${context.getString(R.string.april)} $year"

            } else {
                "$day ${context.getString(R.string.march)} $year"
            }
        }
        "5" -> {
            return if ((day.toInt() + 20) > 31){
                day = (20 - (31 - day.toInt())).toString()
                "$day ${context.getString(R.string.june)} $year"
            } else {
                "$day ${context.getString(R.string.may)} $year"
            }
        }
        "6" -> {
            return if ((day.toInt() + 20) > 30){
                day = (20 - (30 - day.toInt())).toString()
                "$day ${context.getString(R.string.july)} $year"

            } else {
                "$day ${context.getString(R.string.june)} $year"
            }
        }
        "7" -> {
            return if ((day.toInt() + 20) > 31){
                day = (20 - (31 - day.toInt())).toString()
                "$day ${context.getString(R.string.august)} $year"
            } else {
                "$day ${context.getString(R.string.july)} $year"
            }
        }
        "8" -> {
            return if ((day.toInt() + 20) > 31){
                day = (20 - (31 - day.toInt())).toString()
                "$day ${context.getString(R.string.september)} $year"
            } else {
                "$day ${context.getString(R.string.august)} $year"
            }
        }
        "9" -> {
            return if ((day.toInt() + 20) > 30){
                day = (20 - (30 - day.toInt())).toString()
                "$day ${context.getString(R.string.october)} $year"

            } else {
                "$day ${context.getString(R.string.september)} $year"
            }
        }
        "10" -> {
            return if ((day.toInt() + 20) > 31){
                day = (20 - (31 - day.toInt())).toString()
                "$day ${context.getString(R.string.november)} $year"
            } else {
                "$day ${context.getString(R.string.october)} $year"
            }
        }
        "11" -> {
            return if ((day.toInt() + 20) > 30){
                day = (20 - (30 - day.toInt())).toString()
                "$day ${context.getString(R.string.december)} $year"

            } else {
                "$day ${context.getString(R.string.november)} $year"
            }
        }
        "12" -> {
            return if ((day.toInt() + 20) > 31){
                day = (20 - (31 - day.toInt())).toString()
                "$day ${context.getString(R.string.january)} $year"
            } else {
                "$day ${context.getString(R.string.december)} $year"
            }
        }
        else -> "wrong"
    }
}

fun dateConverterReverse(date: String, context: Context): String {
    var (day, month, year) = date.split(" ")
    if (day.length == 1){
        day = "0$day"
    }
    return when (month) {
        context.getString(R.string.january) -> return "$year-01-$day"
        context.getString(R.string.february) -> return "$year-02-$day"
        context.getString(R.string.march) -> return "$year-03-$day"
        context.getString(R.string.april) -> return "$year-04-$day"
        context.getString(R.string.may) -> return "$year-05-$day"
        context.getString(R.string.june) -> return "$year-06-$day"
        context.getString(R.string.july) -> return "$year-07-$day"
        context.getString(R.string.august) -> return "$year-08-$day"
        context.getString(R.string.september) -> return "$year-09-$day"
        context.getString(R.string.october) -> return "$year-10-$day"
        context.getString(R.string.november) -> return "$year-11-$day"
        context.getString(R.string.december) -> return "$year-12-$day"
        else -> "wrong"
    }
}

fun View.hideKeyboard() {
    val hkb = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    hkb.hideSoftInputFromWindow(windowToken, 0)
}

fun View.showKeyboard() {
    val skb = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    skb.showSoftInput(this,2)
}

fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities =
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    if (capabilities != null) {
        when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            }
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            }
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
    }
    return false
}

fun noInternet(context: Context){
    Toast.makeText(context, "Нет соединения с интернетом", Toast.LENGTH_SHORT).show()
}

fun View.setGone(){
    this.visibility = View.GONE
}

fun View.setInvisible(){
    this.visibility = View.INVISIBLE
}

fun View.setVisible(){
    this.visibility = View.VISIBLE
}

fun dateToMillis(date: String) : Long {
    val formatter = SimpleDateFormat("yyyy-MM-dd")
    val dateInMillis = formatter.parse(date).time
    return dateInMillis
}

fun nextFragmentInProgress(
    fragmentManager: FragmentManager,
    fragment: Fragment
) {
    val transaction: FragmentTransaction = fragmentManager.beginTransaction()
    transaction.apply {
        setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out)
        replace(R.id.progress_activity_fragment_container, fragment)
        commit()
    }
}

fun previousFragmentInProgress(
    fragmentManager: FragmentManager,
    fragment: Fragment
) {
    val transaction: FragmentTransaction = fragmentManager.beginTransaction()
    transaction.apply {
        setCustomAnimations(R.anim.slide_right_in, R.anim.slide_right_out)
        replace(R.id.progress_activity_fragment_container, fragment)
        commit()
    }
}

fun TextInputLayout.setErrorOn() {
    this.isErrorEnabled = true
    this.error = resources.getString(R.string.name_alert)
}

fun TextInputLayout.setErrorOff() {
    this.error = null
    this.isErrorEnabled = false
}

fun View.alphaUp(duration: Int = 500) {
    val animate = AlphaAnimation(0f, 1f)
    animate.duration = duration.toLong()
    this.startAnimation(animate)
}

fun View.alphaDown(duration: Int = 500) {
    val animate = AlphaAnimation(1f, 0f)
    animate.duration = duration.toLong()
    this.startAnimation(animate)
}


