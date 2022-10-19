package com.example.bandasdb.utils

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

fun Fragment.nav(value: Int){
    findNavController().navigate(value)
}

fun Fragment.navUp(){
    findNavController().navigateUp()
}

fun Fragment.toast(value: String){
    Toast.makeText(requireContext(), value, Toast.LENGTH_SHORT).show()
}