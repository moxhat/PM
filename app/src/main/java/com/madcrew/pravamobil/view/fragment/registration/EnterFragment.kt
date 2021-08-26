package com.madcrew.pravamobil.view.fragment.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentEnterBinding
import com.madcrew.pravamobil.view.fragment.registration.signin.SignInFragment
import com.madcrew.pravamobil.view.fragment.registration.signup.SignUpFragment


class EnterFragment : Fragment() {

    private var _binding: FragmentEnterBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEnterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainManager = parentFragmentManager

        binding.btEnterSignIn.setOnClickListener {
            val transaction: FragmentTransaction = mainManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out)
            transaction.replace(R.id.enter_activity_fragment_container, SignInFragment())
            transaction.commit()
        }

        binding.btEnterSignUp.setOnClickListener {
            val transaction: FragmentTransaction = mainManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out)
            transaction.replace(R.id.enter_activity_fragment_container, SignUpFragment())
            transaction.commit()
        }
    }
}