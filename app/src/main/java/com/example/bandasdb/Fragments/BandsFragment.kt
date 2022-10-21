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
import com.example.bandasdb.R
import com.example.bandasdb.databinding.FragmentBandsBinding
import com.example.bandasdb.utils.nav
import com.example.bandasdb.viewmodel.MainViewModel
import kotlinx.coroutines.launch

class BandsFragment : Fragment() {

    val viewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentBandsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBandsBinding.inflate(inflater, container, false)
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
                viewModel.bands.collect{
                        bands ->
                    adapter.submitList(bands)
                    binding.rvBands.adapter = adapter
                }

            }
        }



    }

    val adapter = BandsAdapter(
        object : BandListener {
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

    private fun setupRecyclerView() {
        binding.rvBands.adapter = adapter
        binding.rvBands.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    private fun setupClickListeners() {
        binding.fabAdd.setOnClickListener {
            nav(R.id.action_bandsFragment_to_newBandFragment)
        }
    }

    private fun setupViews() {
        getActivity()?.setTitle("Bands");
    }

    private fun setupObservers() {
//        viewModel.listaTurmas.observe(viewLifecycleOwner){
//            adapter.submitList(it)
//            binding.rvTurmas.adapter = adapter
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}