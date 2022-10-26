package com.example.bandasdb.utils

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.bandasdb.R

fun Fragment.nav(value: Int){
    findNavController().navigate(value)
}

fun Fragment.navUp(){
    findNavController().navigateUp()
}

fun Fragment.toast(value: String){
    Toast.makeText(requireContext(), value, Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.getShatedPrefs(): SharedPreferences {
    return getSharedPreferences(
        getString(R.string.my_preferences_name),
        Context.MODE_PRIVATE
    )
}

fun AppCompatActivity.saveLoginToSharedPrefs(value: String){
    val sharedPrefs = getShatedPrefs()
    val editor = sharedPrefs.edit()
    editor.putString(getString(R.string.login_key),
    value)

    editor.apply()
}

fun AppCompatActivity.getLoginFromSharedPrefs(): String{

    val sharedPrefs = getShatedPrefs()
    return  sharedPrefs.getString(
        getString(R.string.login_key),
        ""
    )?:""
}