package com.rafaelleal.android.turmasdatabaseproject.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bandasdb.Fragments.adapters.MusiciansInBandAdapter
import com.example.bandasdb.Fragments.adapters.MusiciansInBandListener
import com.example.bandasdb.Fragments.adapters.QueryMusicianListener
import com.example.bandasdb.Fragments.adapters.QueryMusiciansAdapter
import com.example.bandasdb.databinding.FragmentEditMusiciansBandBinding
import com.example.bandasdb.models.Band
import com.example.bandasdb.models.BandMusician
import com.example.bandasdb.models.Musician
import com.example.bandasdb.viewmodel.MainViewModel
import kotlinx.coroutines.launch

class EditMusiciansBandFragment : Fragment() {

    val TAG = "band"

    val viewModel: MainViewModel by activityViewModels()

    var selectedBand = Band()

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ViewBinding     /////////////////////////////////////////////////////////////////////////////
    // https://developer.android.com/topic/libraries/view-binding?hl=pt-br#fragments  //////////////
    private var _binding: FragmentEditMusiciansBandBinding? = null

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
        _binding = FragmentEditMusiciansBandBinding.inflate(inflater, container, false)
        val view = binding.root

        setup()

        return view
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // RecyclerView ////////////////////////////////////////////////////////////////////////////////
    val adapterQuery = QueryMusiciansAdapter(
        object : QueryMusicianListener {
            override fun onAddClick(musician: Musician) {
                addMusicianInBand(musician)
            }
        }
    )

    val adapterInBand = MusiciansInBandAdapter(
        object : MusiciansInBandListener {
            override fun onDeleteClick(musician: Musician) {
                removeMusicianfromBand(musician)
            }
        }
    )

    fun setupQueryRecyclerView() {
        binding.rvQueryMusicians.adapter = adapterQuery
        binding.rvQueryMusicians.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    fun setupInBandRecyclerView() {
        binding.rvInsertMusicians.adapter = adapterInBand
        binding.rvInsertMusicians.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    fun queryMusicians(input: String){
        viewModel.collectMusiciansByName(input)
    }

    fun addMusicianInBand(musician: Musician){
        val newBandMusician = BandMusician(
            bandId = selectedBand.id,
            musicianId = musician.id
        )
        viewModel.insertBandMusician(newBandMusician)
    }

    fun removeMusicianfromBand(musician: Musician){
        val bandId = selectedBand.id
        val musicianId = musician.id
        viewModel.deleteBandMusician(bandId, musicianId)
    }

    private fun setupRecyclerView() {
        setupQueryRecyclerView()
        setupInBandRecyclerView()
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////





    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Use Collect para receber um StateFlow
                // import kotlinx.coroutines.flow.collect
                viewModel.musiciansInBand.collect { musicians ->
                    adapterInBand.submitList(musicians)
                    binding.rvInsertMusicians.adapter = adapterInBand
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Use Collect para receber um StateFlow
                // import kotlinx.coroutines.flow.collect
                viewModel.musiciansByName.collect { alunos ->
                    adapterQuery.submitList(alunos)
                    binding.rvQueryMusicians.adapter = adapterQuery
                }
            }
        }
    }


    private fun setup() {
        setupViews()
        setupObservers()
        setupRecyclerView()
        setupDoAfterTextChanged()
        queryMusicians("")
    }

    private fun setupViews() {
        getActivity()?.setTitle("Editar Alunos na Turma");
    }

    fun setupObservers() {
        viewModel.selectedBandId.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                selectedBand = viewModel.getBandById(it)
                binding.tvTitle.text = "Band: ${selectedBand.name}"
                viewModel.collectMusiciansInBand(
                    selectedBand.id
                )
            }
        }
    }

    fun setupDoAfterTextChanged(){
        binding.inputQueryMusicians.doAfterTextChanged {
            val input = it.toString()
            queryMusicians(input)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}