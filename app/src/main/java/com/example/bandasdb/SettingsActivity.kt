package com.example.bandasdb

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import com.example.bandasdb.databinding.ActivitySettingsBinding
import com.example.bandasdb.utils.getLoginFromSharedPrefs
import com.example.bandasdb.utils.navUp
import com.example.bandasdb.utils.saveLoginToSharedPrefs
import com.example.bandasdb.viewmodel.MainViewModel

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setup()
    }

    private fun setup() {
        setupClickListeners()
        setinputyourname()
    }

    private fun setinputyourname() {
        binding.tvname.text = getLoginFromSharedPrefs()
    }

    private fun setupClickListeners() {
        binding.apply {
            btnSave.setOnClickListener {
                val login = binding.inputName.text.toString()
                saveLoginToSharedPrefs(login)
                setinputyourname()
            }
        }
    }


}