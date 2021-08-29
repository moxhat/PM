package com.madcrew.pravamobil.view.fragment.progress.email

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentAddPasswordBinding
import com.madcrew.pravamobil.databinding.FragmentEmailBinding
import com.madcrew.pravamobil.view.fragment.progress.category.CategoryFragment
import com.madcrew.pravamobil.view.fragment.registration.EnterFragment


class EmailFragment : Fragment() {

    private var _binding: FragmentEmailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmailBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btEmailNext.setOnClickListener {
            nextFragment()
        }
    }

    private fun nextFragment() {
        val mainManager = parentFragmentManager
        val transaction: FragmentTransaction = mainManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out)
        transaction.remove(this)
        transaction.replace(R.id.progress_activity_fragment_container, CategoryFragment())
        transaction.commit()
    }

}