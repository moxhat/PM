package com.madcrew.pravamobil.view.fragment.progress.theory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentSelectTheoryBinding
import com.madcrew.pravamobil.view.fragment.progress.category.CategoryFragment
import com.madcrew.pravamobil.view.fragment.progress.filial.FilialFragment

class SelectTheoryFragment : Fragment() {

    private var _binding: FragmentSelectTheoryBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectTheoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainManager = parentFragmentManager

        binding.btSelectTheoryOffline.setOnClickListener {
            nextFragment(FilialFragment())
        }
    }

    private fun nextFragment(fragment: Fragment) {
        val mainManager = parentFragmentManager
        val transaction: FragmentTransaction = mainManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out)
        transaction.remove(this)
        transaction.replace(R.id.progress_activity_fragment_container, fragment)
        transaction.commit()
    }

}