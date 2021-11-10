package com.madcrew.pravamobil.view.fragment.progress.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentCategoryBinding
import com.madcrew.pravamobil.domain.BaseUrl.Companion.TOKEN
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.models.requestmodels.CategoryRequest
import com.madcrew.pravamobil.models.requestmodels.FullRegistrationRequest
import com.madcrew.pravamobil.models.requestmodels.ProgressRequest
import com.madcrew.pravamobil.utils.Preferences
import com.madcrew.pravamobil.utils.isOnline
import com.madcrew.pravamobil.utils.nextFragmentInProgress
import com.madcrew.pravamobil.utils.noInternet
import com.madcrew.pravamobil.view.activity.progress.ProgressActivity
import com.madcrew.pravamobil.view.fragment.progress.theory.SelectTheoryFragment
import com.madcrew.pravamobil.view.fragment.progress.theorygroup.TheoryGroupFragment
import com.madcrew.pravamobil.view.fragment.progress.transmission.TransmissionFragment


class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoryList = mutableListOf<String>()

        val parent = this.context as ProgressActivity
        val repository = Repository()
        val viewModelFactory = CategoryViewModelFactory(repository)
        val mViewModel =
            ViewModelProvider(this, viewModelFactory).get(CategoryViewModel::class.java)

        val mainManager = parentFragmentManager
        val picker = binding.categoryPicker
        val schoolId = Preferences.getPrefsString("schoolId", requireContext()).toString()
        val clientId = Preferences.getPrefsString("clientId", requireContext()).toString()

        parent.mViewModel.updateProgress(ProgressRequest(TOKEN, schoolId, clientId, "SelectCategotyPage"))

        if (isOnline(requireContext())) {
            mViewModel.getCategoryList(CategoryRequest(TOKEN, schoolId))
        } else {
            noInternet(requireContext())
        }

        mViewModel.categoryResponse.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                for (i in response.body()!!.categories) {
                    categoryList.add(resources.getString(R.string.category) + " " + i.title)
                }
                mViewModel.setUpPicker(picker, categoryList.toTypedArray())
            }
        })

        binding.btCategoryNext.setOnClickListener {
            val selectedCategory =
                mViewModel.categoryResponse.value?.body()!!.categories[picker.value].id
            parent.updateClientData(
                FullRegistrationRequest(
                    TOKEN,
                    clientId,
                    schoolId,
                    category = selectedCategory
                )
            )
            nextFragmentInProgress(mainManager, SelectTheoryFragment())
        }
    }


}