package com.madcrew.pravamobil.view.fragment.progress.filial

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentFilialBinding
import com.madcrew.pravamobil.domain.BaseUrl.Companion.TOKEN
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.models.requestmodels.FilialRequest
import com.madcrew.pravamobil.models.requestmodels.FullRegistrationRequest
import com.madcrew.pravamobil.models.requestmodels.ProgressRequest
import com.madcrew.pravamobil.utils.Preferences
import com.madcrew.pravamobil.utils.isOnline
import com.madcrew.pravamobil.utils.nextFragmentInProgress
import com.madcrew.pravamobil.utils.noInternet
import com.madcrew.pravamobil.view.activity.progress.ProgressActivity
import com.madcrew.pravamobil.view.fragment.progress.addpassword.AddPasswordViewModel
import com.madcrew.pravamobil.view.fragment.progress.addpassword.AddPasswordViewModelFactory
import com.madcrew.pravamobil.view.fragment.progress.theorygroup.TheoryGroupFragment
import com.shawnlin.numberpicker.NumberPicker


class FilialFragment : Fragment() {

    private var _binding: FragmentFilialBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilialBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainManager = parentFragmentManager
        val parent = this.context as ProgressActivity
        val clientId = Preferences.getPrefsString("clientId", requireContext()).toString()
        val schoolId = Preferences.getPrefsString("schoolId", requireContext()).toString()

        val picker = binding.filialPicker

        parent.mViewModel.updateProgress(ProgressRequest(TOKEN, schoolId, clientId, "SelectFilialAndGroup"))

        val repository = Repository()
        val viewModelFactory = FilialViewModelFactory(repository)
        val mViewModel = ViewModelProvider(this, viewModelFactory).get(FilialViewModel::class.java)

        val filialList = mutableListOf<String>()

        if(isOnline(requireContext())){
            mViewModel.getFilialList(FilialRequest(TOKEN, schoolId))
        } else {
            noInternet(requireContext())
        }

        mViewModel.filialResponse.observe(viewLifecycleOwner, {response ->
            if (response.isSuccessful){
                if (response.body()!!.status == "done"){
                    for (i in response.body()!!.centres!!){
                        if(i.full == "true"){
                            filialList.add("${i.title} - ${resources.getString(R.string.no_places)}")
                        } else {
                            filialList.add(i.title)
                        }
                    }
                    mViewModel.setUpPicker(picker, filialList.toTypedArray())
                }
            }
        })

        binding.btFilialNext.setOnClickListener {
            if (mViewModel.filialResponse.value!!.body()!!.centres!![picker.value].full == "true"){
                Toast.makeText(requireContext(), resources.getString(R.string.no_places), Toast.LENGTH_SHORT).show()
            } else {
                val selectedFilial = mViewModel.filialResponse.value!!.body()!!.centres!![picker.value].id
                Preferences.setPrefsString("filialId", selectedFilial, requireContext())
                parent.mViewModel.updateClientData(FullRegistrationRequest(TOKEN, clientId, schoolId, centre = selectedFilial))
                nextFragmentInProgress(mainManager, TheoryGroupFragment(selectedFilial))
            }
        }
    }
}