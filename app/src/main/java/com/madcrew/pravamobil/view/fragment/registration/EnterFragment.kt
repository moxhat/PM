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
            replaceFragment(SignInFragment(), R.anim.slide_left_in, R.anim.slide_left_out)
        }

        binding.btEnterSignUp.setOnClickListener {
            replaceFragment(SignUpFragment(), R.anim.slide_left_in, R.anim.slide_left_out)
        }
    }

    private fun replaceFragment(fragment: Fragment, animationIn: Int, animationOut: Int){
        val mainManager = parentFragmentManager
        val transaction: FragmentTransaction = mainManager.beginTransaction()
        transaction.setCustomAnimations(animationIn, animationOut)
        transaction.remove(this)
        transaction.replace(R.id.enter_activity_fragment_container, fragment)
        transaction.commit()
    }
}