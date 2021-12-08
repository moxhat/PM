package com.madcrew.pravamobil.view.fragment.registration.school

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentSchoolBinding
import com.madcrew.pravamobil.domain.BaseUrl.Companion.TOKEN
import com.madcrew.pravamobil.models.requestmodels.FullRegistrationRequest
import com.madcrew.pravamobil.models.responsemodels.School
import com.madcrew.pravamobil.utils.Preferences
import com.madcrew.pravamobil.utils.isOnline
import com.madcrew.pravamobil.utils.nextFragmentInProgress
import com.madcrew.pravamobil.utils.noInternet
import com.madcrew.pravamobil.view.activity.progress.ProgressActivity
import com.madcrew.pravamobil.view.fragment.progress.category.CategoryFragment
import com.madcrew.pravamobil.view.fragment.registration.signup.SignUpFragment
import com.shawnlin.numberpicker.NumberPicker


class SchoolFragment(var schoolList: MutableList<School>) : Fragment() {

    private var _binding: FragmentSchoolBinding? = null
    private val binding get() = _binding!!

    var listOfSchools: Array<String> = arrayOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSchoolBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val picker = binding.schoolPicker

        val list = mutableListOf<String>()
        for (i in schoolList){
            list.add(i.title)
        }
        listOfSchools = list.toTypedArray()

        setUpPicker(picker,listOfSchools)

        binding.schoolMainConstraint.setOnClickListener {}

        binding.btSchoolNext.setOnClickListener {
            val selectedSchool = schoolList[picker.value].id
            val clientId = Preferences.getPrefsString("clientId", requireContext()).toString()
            if(Preferences.getPrefsString("progressStatus", requireContext()) == "AddPassword") {
                Preferences.setPrefsString("schoolId", selectedSchool, requireContext())
                replaceFragment(SignUpFragment(), R.anim.slide_left_in, R.anim.slide_left_out)
            } else {
                val schoolId = Preferences.getPrefsString("schoolId",  requireContext())
                if (schoolId == selectedSchool) {
                    parentFragmentManager.beginTransaction().remove(this).commit()
                } else {
                    val parent = this.context as ProgressActivity
                    if (isOnline(requireContext())) {
                        parent.mViewModel.updateClientData(
                            FullRegistrationRequest(
                                TOKEN,
                                clientId,
                                schoolId.toString(),
                                new_school_id = selectedSchool
                            )
                        )
                    } else {
                        noInternet(requireContext())
                    }
                    Preferences.setPrefsString("schoolId", selectedSchool, requireContext())
                    parentFragmentManager.beginTransaction().remove(this).commit()
                    nextFragmentInProgress(parentFragmentManager, CategoryFragment())
                }

            }

        }
    }

    private fun setUpPicker(
        picker: NumberPicker,
        data: Array<String>
    ) {
        picker.apply {
            minValue = 0
            maxValue = data.size - 1
            displayedValues = data
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                typeface = resources.getFont(R.font.ubuntu_m)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                setSelectedTypeface(resources.getFont(R.font.ubuntu_m))
            }
            wrapSelectorWheel = true
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

