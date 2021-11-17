package com.madcrew.pravamobil.view.fragment.progress.theorygroup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.adapter.GroupListRecyclerAdapter
import com.madcrew.pravamobil.databinding.FragmentTheoryGroupBinding
import com.madcrew.pravamobil.domain.BaseUrl
import com.madcrew.pravamobil.domain.BaseUrl.Companion.TOKEN
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.models.GroupTimes
import com.madcrew.pravamobil.models.requestmodels.FullRegistrationRequest
import com.madcrew.pravamobil.models.requestmodels.GroupsRequest
import com.madcrew.pravamobil.models.requestmodels.ProgressRequest
import com.madcrew.pravamobil.models.responsemodels.FilialGroup
import com.madcrew.pravamobil.utils.Preferences
import com.madcrew.pravamobil.utils.isOnline
import com.madcrew.pravamobil.utils.nextFragmentInProgress
import com.madcrew.pravamobil.utils.noInternet
import com.madcrew.pravamobil.view.activity.progress.ProgressActivity
import com.madcrew.pravamobil.view.fragment.progress.filial.FilialViewModel
import com.madcrew.pravamobil.view.fragment.progress.filial.FilialViewModelFactory
import com.madcrew.pravamobil.view.fragment.progress.tariff.TariffFragment

class TheoryGroupFragment(var filialId: String) : Fragment(),
    GroupListRecyclerAdapter.OnGroupClickListener{

    private var _binding: FragmentTheoryGroupBinding? = null
    private val binding get() = _binding!!

    private var mGroupTimeList: MutableList<FilialGroup> = mutableListOf()

    private lateinit var mAdapter: GroupListRecyclerAdapter
    private var selectedGroup = "null"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTheoryGroupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainManager = parentFragmentManager
        val parent = this.context as ProgressActivity

        val clientId = Preferences.getPrefsString("clientId", requireContext()).toString()
        val schoolId = Preferences.getPrefsString("schoolId", requireContext()).toString()

        parent.mViewModel.updateProgress(ProgressRequest(TOKEN, schoolId, clientId, "SelectFilialAndGroup"))

        val repository = Repository()
        val viewModelFactory = TheoryGroupViewModelFactory(repository)
        val mViewModel = ViewModelProvider(this, viewModelFactory).get(TheoryGroupViewModel::class.java)

        if (isOnline(requireContext())){
            mViewModel.getGroupList(GroupsRequest(TOKEN, schoolId, filialId))
        } else {
            noInternet(requireContext())
        }

        mViewModel.groupsResponse.observe(viewLifecycleOwner, {response ->
            if (response.isSuccessful){
                if (response.body()!!.status == "done"){
                    for (i in response.body()!!.groups){
                        mGroupTimeList.add(i)
                    }
                    mAdapter = GroupListRecyclerAdapter(mGroupTimeList, this)
                    binding.recyclerTheoryChoose.apply {
                        layoutManager = LinearLayoutManager(requireContext())
                        adapter = mAdapter
                    }
                    mAdapter.notifyDataSetChanged()
                }
            }
        })


        binding.btTheoryGroupNext.setOnClickListener {
            if (selectedGroup == "null"){
                Toast.makeText(requireContext(), R.string.choose_theory_group, Toast.LENGTH_SHORT).show()
            } else {
                parent.updateClientData(FullRegistrationRequest(TOKEN, clientId, schoolId,group_id = selectedGroup))
                nextFragmentInProgress(mainManager, TariffFragment())
            }
        }
    }

    override fun onGroupClick(itemView: View?, position: Int) {
        selectedGroup = mGroupTimeList[position].id
    }

}