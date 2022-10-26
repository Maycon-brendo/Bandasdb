package com.example.bandasdb.Fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.bandasdb.databinding.FragmentNewMusicianBinding
import com.example.bandasdb.models.Musician
import com.example.bandasdb.utils.navUp
import com.example.bandasdb.viewmodel.MainViewModel

class NewMusicianFragment : Fragment() {

    val viewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentNewMusicianBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewMusicianBinding.inflate(inflater, container, false)
        val view = binding.root

        setup()

        return view
    }

    private fun setup() {
        setupViews()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.apply {
            fabAddMusician.setOnClickListener {
                addNewMusician()
                navUp()
            }
        }
    }

    private fun addNewMusician() {
        if (validateInput()) {
            val newMusician = getNewMusicianFromInputs()
            Log.i(TAG, "Musician: ${newMusician.name}")
            Log.i(TAG, "genre: ${newMusician.gender}")
            Log.i(TAG, "periodo: ${newMusician.age}")
            viewModel.insertMusician(newMusician)
        }
    }

    private fun getNewMusicianFromInputs(): Musician {
        return Musician(
            name = binding.inputNameMusician.text.toString(),
            age = binding.inputAge.text.toString(),
            gender = binding.inputGender.text.toString()
        )

    }

    private fun validateInput(): Boolean {
        // para fazer ainda
        return true

    }

    private fun setupViews() {
        getActivity()?.setTitle("New Musician");
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}