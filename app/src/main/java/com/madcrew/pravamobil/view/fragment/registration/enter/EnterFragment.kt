package com.madcrew.pravamobil.view.fragment.registration.enter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentEnterBinding
import com.madcrew.pravamobil.domain.BaseUrl.Companion.TOKEN
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.models.requestmodels.TokenOnly
import com.madcrew.pravamobil.models.responsemodels.School
import com.madcrew.pravamobil.utils.Preferences
import com.madcrew.pravamobil.utils.isOnline
import com.madcrew.pravamobil.utils.noInternet
import com.madcrew.pravamobil.view.fragment.registration.school.SchoolFragment
import com.madcrew.pravamobil.view.fragment.registration.signin.SignInFragment


class EnterFragment : Fragment() {

    private var _binding: FragmentEnterBinding? = null
    private val binding get() = _binding!!

    private var schoolList = mutableListOf<School>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEnterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = Repository()
        val viewModelFactory = EnterViewModelFactory(repository)
        val mViewModel = ViewModelProvider(this, viewModelFactory).get(EnterViewModel::class.java)

        if (isOnline(requireContext())){
            mViewModel.getSchoolList(TokenOnly(TOKEN))
        } else {
            noInternet(requireContext())
        }

        mViewModel.schoolListResponse.observe(viewLifecycleOwner, {response ->
            if (response.isSuccessful){
                if (response.body()!!.status == "done"){
                    schoolList = response.body()!!.schoolList
                }
            }
        })

        binding.btEnterSignIn.setOnClickListener {
            replaceFragment(SignInFragment(), R.anim.slide_left_in, R.anim.slide_left_out)
        }

        binding.btEnterSignUp.setOnClickListener {
            Preferences.setPrefsString("progressStatus", "AddPassword", requireContext())
            replaceFragment(SchoolFragment(schoolList), R.anim.slide_left_in, R.anim.slide_left_out)
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