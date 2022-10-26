package com.example.bandasdb.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bandasdb.R
import com.example.bandasdb.databinding.FragmentHomeBinding
import com.example.bandasdb.utils.nav


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        setup()

        return view
    }

    private fun setup() {
        setupViews()
        setupClickListeners()
    }

    private fun setupViews() {
        getActivity()?.setTitle("Home");
    }

    private fun setupClickListeners() {

        binding.apply {
            btnBand.setOnClickListener{
                nav(R.id.action_homeFragment_to_bandsFragment)
            }
            btnMusicians.setOnClickListener {
                nav(R.id.action_homeFragment_to_musiciansFragment)
            }
            fabSettings.setOnClickListener {
                nav(R.id.action_homeFragment_to_settingsActivity)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
