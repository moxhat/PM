package com.madcrew.pravamobil.view.fragment.registration.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentSignInBinding
import com.madcrew.pravamobil.view.fragment.registration.EnterFragment


class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainManager = parentFragmentManager

        binding.btSigninBack.setOnClickListener {
            val transaction: FragmentTransaction = mainManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_right_out)
            transaction.replace(R.id.enter_activity_fragment_container, EnterFragment())
            transaction.commit()
        }
    }

}