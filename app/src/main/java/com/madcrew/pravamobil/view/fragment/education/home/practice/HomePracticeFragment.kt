package com.madcrew.pravamobil.view.fragment.education.home.practice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentHomePracticeBinding
import com.madcrew.pravamobil.databinding.FragmentHomeTheoryBinding


class HomePracticeFragment : Fragment() {

    private var _binding: FragmentHomePracticeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomePracticeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(requireContext()).load(R.drawable.ic_man).circleCrop().into(binding.homePracticeInstructorAvatar)
    }

}