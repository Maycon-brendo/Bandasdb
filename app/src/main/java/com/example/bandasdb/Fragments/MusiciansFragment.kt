package com.example.bandasdb.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bandasdb.Fragments.adapters.BandListener
import com.example.bandasdb.Fragments.adapters.BandsAdapter
import com.example.bandasdb.Fragments.adapters.MusicianListener
import com.example.bandasdb.Fragments.adapters.MusiciansAdapter
import com.example.bandasdb.R
import com.example.bandasdb.databinding.FragmentBandsBinding
import com.example.bandasdb.databinding.FragmentMusiciansBinding
import com.example.bandasdb.utils.nav
import com.example.bandasdb.viewmodel.MainViewModel
import kotlinx.coroutines.launch

class MusiciansFragment : Fragment() {

    val viewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentMusiciansBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMusiciansBinding.inflate(inflater, container, false)
        val view = binding.root

        setup()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                // Use Collect para receber um StateFlow
                // import kotlinx.coroutines.flow.collect
                viewModel.musicians.collect{
                        musicians ->
                    adapter.submitList(musicians)
                    binding.rvMusicians.adapter = adapter
                }

            }
        }



    }

    val adapter = MusiciansAdapter(
        object : MusicianListener {
            override fun onClick(posicao: Int) {
                // pra fazer (evento de clique)
            }

        }
    )

    private fun setup() {
        setupViews()
        setupRecyclerView()
        setupClickListeners()
        setupObservers()
    }

    private fun setupObservers() {
//        viewModel.listaTurmas.observe(viewLifecycleOwner){
//            adapter.submitList(it)
//            binding.rvMusicians.adapter = adapter
//        }
    }

    private fun setupClickListeners() {
        binding.fabAdd2.setOnClickListener {
            nav(R.id.action_musiciansFragment_to_newMusicianFragment)
        }
    }

    private fun setupRecyclerView() {
        binding.rvMusicians.adapter = adapter
        binding.rvMusicians.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    private fun setupViews() {
        getActivity()?.setTitle("Musicians");
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}