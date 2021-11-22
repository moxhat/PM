package com.madcrew.pravamobil.view.fragment.education.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.adapter.HomePagerAdapter
import com.madcrew.pravamobil.databinding.FragmentHomeBinding
import com.madcrew.pravamobil.domain.BaseUrl.Companion.TOKEN
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.models.requestmodels.SpravkaStatusRequest
import com.madcrew.pravamobil.utils.Preferences
import com.madcrew.pravamobil.view.activity.education.EducationActivity
import com.madcrew.pravamobil.view.activity.education.EducationViewModel
import com.madcrew.pravamobil.view.activity.education.EducationViewModelFactory
import com.madcrew.pravamobil.view.dialog.SpravkaConfirmedDialogFragment
import com.madcrew.pravamobil.view.fragment.education.home.exam.HomeExamFragment
import com.madcrew.pravamobil.view.fragment.education.home.practice.HomePracticeFragment
import com.madcrew.pravamobil.view.fragment.education.home.practice.beforespravka.ConfirmationSpravkaFragment
import com.madcrew.pravamobil.view.fragment.education.home.practice.beforespravka.NoSpravkaFragment
import com.madcrew.pravamobil.view.fragment.education.home.theory.HomeTheoryFragment

class HomeFragment : Fragment() {

    var _binding: FragmentHomeBinding? = null
    val binding get() = _binding!!

    private lateinit var homeViewPager: ViewPager2
    private lateinit var homePagerAdapter: HomePagerAdapter
    private lateinit var hViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = Repository()
        val viewModelFactory = HomeViewModelFactory(repository)
        hViewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        val clientId = Preferences.getPrefsString("clientId", requireContext()).toString()
        val schoolId = Preferences.getPrefsString("schoolId", requireContext()).toString()

        if (Preferences.getPrefsString("spravka", requireContext()) != "confirm"){
            hViewModel.getSpravkaStatus(SpravkaStatusRequest(TOKEN, schoolId, clientId))
        }

        hViewModel.spravkaStatus.observe(viewLifecycleOwner, {response ->
            if (response.isSuccessful){
                if (response.body()!!.status == "done"){
                    Preferences.setPrefsString("spravkaStatus",  response.body()!!.medical, requireContext())
                    when (Preferences.getPrefsString("spravkaStatus", requireContext())) {
                        "empty" -> setSpravkaAdd()
                        "confirm" -> {
                            Preferences.setPrefsString("spravka", "confirm", requireContext())
                            setSpravkaConfirmed()
                        }
                        "noconfirm" -> setSpravkaConfirmation()
                        "deactivate" -> {
                            Preferences.setPrefsString("spravka", "deactivate", requireContext())
                            Handler(Looper.getMainLooper()).postDelayed({
                                val confirmedDialog = SpravkaConfirmedDialogFragment("bad")
                                confirmedDialog.show(childFragmentManager, "SpravkaConfirmedDialogFragment")
                            }, 3000)
                            setSpravkaAdd()
                            Preferences.setPrefsString("spravkaStatus", "empty", requireContext())
                            hViewModel.deleteSpravka(SpravkaStatusRequest(TOKEN, schoolId, clientId))
                        }
                    }
                }
            }
        })

        when (Preferences.getPrefsString("spravkaStatus", requireContext())) {
            "empty" -> setSpravkaAdd()
            "confirm" -> setSpravkaConfirmed()
            "noconfirm" -> setSpravkaConfirmation()
            "deactivate" -> {
                Handler(Looper.getMainLooper()).postDelayed({
                    val confirmedDialog = SpravkaConfirmedDialogFragment("bad")
                    confirmedDialog.show(childFragmentManager, "SpravkaConfirmedDialogFragment")
                }, 5000)
                setSpravkaAdd()
                Preferences.setPrefsString("spravkaStatus", "empty", requireContext())
            }
        }

        homeViewPager = binding.homeViewPager

        homeViewPager.adapter = homePagerAdapter
        homeViewPager.currentItem = 0

        val tabLayout = binding.homeTabs
        TabLayoutMediator(tabLayout, homeViewPager) { tab, position ->
            when (position) {
                0 -> tab.text = resources.getString(R.string.theory)
                1 -> tab.text = resources.getString(R.string.practice)
                2 -> tab.text = resources.getString(R.string.exam)
            }
        }.attach()

    }

    override fun onPause() {
        super.onPause()
        val clientId = Preferences.getPrefsString("clientId", requireContext()).toString()
        val schoolId = Preferences.getPrefsString("schoolId", requireContext()).toString()
        if (Preferences.getPrefsString("loadingSpravka", requireContext()) != "true"){
            if (Preferences.getPrefsString("spravka", requireContext()) != "confirm"){
                hViewModel.getSpravkaStatus(SpravkaStatusRequest(TOKEN, schoolId, clientId))
            }
        }
    }

    private fun setSpravkaConfirmed() {
        val fragmentsWithSpravkaList =
            mutableListOf(HomeTheoryFragment(), HomePracticeFragment(), HomeExamFragment())
        homePagerAdapter = HomePagerAdapter(this, fragmentsWithSpravkaList)
        val item = binding.homeViewPager.currentItem
        binding.homeViewPager.adapter = homePagerAdapter
        binding.homeViewPager.currentItem = item
        homePagerAdapter.notifyDataSetChanged()
    }

    fun setSpravkaConfirmation() {
        val fragmentsConfirmationSpravkaList =
            mutableListOf(HomeTheoryFragment(), ConfirmationSpravkaFragment(), HomeExamFragment())
        homePagerAdapter = HomePagerAdapter(this, fragmentsConfirmationSpravkaList)
        val item = binding.homeViewPager.currentItem
        binding.homeViewPager.adapter = homePagerAdapter
        binding.homeViewPager.currentItem = item
        homePagerAdapter.notifyDataSetChanged()
    }

    private fun setSpravkaAdd() {
        val fragmentsNoSpravkaList =
            mutableListOf(HomeTheoryFragment(), NoSpravkaFragment(), HomeExamFragment())
        homePagerAdapter = HomePagerAdapter(this, fragmentsNoSpravkaList)
        val item = binding.homeViewPager.currentItem
        binding.homeViewPager.adapter = homePagerAdapter
        binding.homeViewPager.currentItem = item
        homePagerAdapter.notifyDataSetChanged()
    }

    fun setTitle(title: String, text: String){
        binding.educationInfoFieldTitle.text = title
        binding.educationInfoFieldText.text = text
    }

}