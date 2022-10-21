package com.example.bandasdb.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.bandasdb.databinding.FragmentNewBandBinding
import com.example.bandasdb.models.Band
import com.example.bandasdb.utils.navUp
import com.example.bandasdb.viewmodel.MainViewModel

class NewBandFragment : Fragment() {

    val viewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentNewBandBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewBandBinding.inflate(inflater, container, false)
        val view = binding.root

        setup()

        return view
    }

    private fun setup() {
        setupViews()
        setupClickListeners()
    }

    private fun setupViews() {
        getActivity()?.setTitle("New Musician");
    }

    private fun setupClickListeners() {
        binding.apply {
            fabAddBand.setOnClickListener {
                addNewBand()
                navUp()
            }
        }
    }

    private fun addNewBand() {
        if (validateInput()) {
            val newBand = getNewBandFromInputs()
            viewModel.insertBand(newBand)
        }
    }

    private fun getNewBandFromInputs(): Band {
        return Band(
            name = binding.inputNameBand.text.toString(),
            formation = binding.inputFormationYear.text.toString(),
            genre = binding.inputGenre.text.toString()
        )
    }

    fun validateInput(): Boolean {

        // ainda fazer validação

        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}