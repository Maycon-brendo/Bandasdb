package com.example.bandasdb.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.bandasdb.R
import com.example.bandasdb.databinding.FragmentEditBandBinding
import com.example.bandasdb.models.Band
import com.example.bandasdb.utils.nav
import com.example.bandasdb.utils.navUp
import com.example.bandasdb.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditBandFragment : Fragment() {

    val TAG = "band"

    val viewModel: MainViewModel by activityViewModels()


    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ViewBinding     /////////////////////////////////////////////////////////////////////////////
    // https://developer.android.com/topic/libraries/view-binding?hl=pt-br#fragments  //////////////
    private var _binding: FragmentEditBandBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditBandBinding.inflate(inflater, container, false)
        val view = binding.root

        setup()

        return view
    }

    private fun setup() {
        setupViews()
        setupClickListeners()
        setupObservers()
    }

    private fun setupViews() {
        getActivity()?.setTitle("Edit Band")
    }

    fun setupObservers() {
        viewModel.selectedBandId.observe(viewLifecycleOwner) {
            lifecycleScope.launch(Dispatchers.IO) {
                val band = viewModel.getBandById(it)
                Log.i(TAG, "band selected: ${band.name} - id: ${band.id}")
                setupBandView(band)
            }

        }
    }

    suspend fun setupBandView(band: Band) {
        withContext(Dispatchers.Main) {
            binding.apply {
                inputFormationYear.setText(band.formation)
                inputNameBand.setText(band.name)
                inputBandGenre.setText(band.genre)
            }
        }
    }

    private fun setupClickListeners() {
        binding.apply {
            fabUpdateBand.setOnClickListener {
                updateBand()
                navUp()
            }

            btnEditMusiciansBand.setOnClickListener {
                nav(R.id.action_editBandFragment_to_editMusiciansBandFragment)
            }
        }
    }

    fun updateBand() {
        if (validateInput()) {
            val band = getUpdateBandFromInputs()
            viewModel.updateBand(band)
        }
    }

    fun getUpdateBandFromInputs(): Band {
        return Band(
            id = viewModel.selectedBandId.value!!,
            name = binding.inputNameBand.text.toString(),
            genre = binding.inputBandGenre.text.toString(),
            formation = binding.inputFormationYear.text.toString()
        )
    }

    fun clearInputs() {
        binding.apply {
            inputFormationYear.text?.clear()
            inputNameBand.text?.clear()
            inputBandGenre.text?.clear()
        }
    }

    fun validateInput(): Boolean {

        // TODO

        return true
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}