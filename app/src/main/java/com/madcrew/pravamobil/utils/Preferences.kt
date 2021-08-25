package com.madcrew.pravamobil.utils

import android.content.Context
import android.net.Uri

class Preferences {
    companion object {
        private const val SP_NAME = "appSettingsSingleOptions"

        fun setPrefsString(key: String?, value: String?, context: Context) {
            val sharedPreferences = context.getSharedPreferences(
                SP_NAME,
                Context.MODE_PRIVATE
            )
            val editor = sharedPreferences.edit()
            editor.putString(key, value)
            editor.apply()
        }
        fun getPrefsString(key: String?, context: Context): String? {
            val sharedPreferences = context.getSharedPreferences(
                SP_NAME,
                Context.MODE_PRIVATE
            )
            return sharedPreferences.getString(key, "")
        }

        fun setPrefsInt(key: String?, value: Int?, context: Context) {
            val sharedPreferences = context.getSharedPreferences(
                SP_NAME,
                Context.MODE_PRIVATE
            )
            val editor = sharedPreferences.edit()
            if (value != null) {
                editor.putInt(key, value)
            }
            editor.apply()
        }
        fun getPrefsInt(key: String?, context: Context): Int {
            val sharedPreferences = context.getSharedPreferences(
                SP_NAME,
                Context.MODE_PRIVATE
            )
            return sharedPreferences.getInt(key, 0)
        }
    }
}