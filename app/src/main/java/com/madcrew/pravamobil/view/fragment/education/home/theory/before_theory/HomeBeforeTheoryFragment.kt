package com.madcrew.pravamobil.view.fragment.education.home.theory.before_theory

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.madcrew.pravamobil.databinding.HomeBeforeTheoryFragmentBinding
import com.madcrew.pravamobil.domain.Repository


class HomeBeforeTheoryFragment : Fragment() {


    private var _binding: HomeBeforeTheoryFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HomeBeforeTheoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeBeforeTheoryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = Repository()
        val viewModelFactory = HomeBeforeTheoryViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeBeforeTheoryViewModel::class.java)
    }

}