package com.madcrew.pravamobil.view.fragment.progress.theorygroup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.adapter.GroupListRecyclerAdapter
import com.madcrew.pravamobil.databinding.FragmentTheoryGroupBinding
import com.madcrew.pravamobil.models.GroupTimes
import com.madcrew.pravamobil.view.fragment.progress.tariff.TariffFragment

private var mGroupTimeList: MutableList<GroupTimes> = mutableListOf()

class TheoryGroupFragment : Fragment(),
    GroupListRecyclerAdapter.OnGroupClickListener{

    private var _binding: FragmentTheoryGroupBinding? = null
    private val binding get() = _binding!!

    private lateinit var mAdapter: GroupListRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTheoryGroupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mGroupTimeList = mutableListOf(
            GroupTimes("12 июля", "Пн, Ср 18:30-20:00"),
            GroupTimes("15 Августа", "Вт, Ср 11:30-21:00"),
            GroupTimes("2 сентября", "Чт, Сб 12:30-15:00"),
            GroupTimes("21 сентября", "Пн, Ср 18:30-20:00")
        )

        mAdapter = GroupListRecyclerAdapter(mGroupTimeList, this)

        binding.recyclerTheoryChoose.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mAdapter
        }

        binding.btTheoryGroupNext.setOnClickListener {
            nextFragment(TariffFragment())
        }
    }

    override fun onGroupClick(itemView: View?, position: Int) {
        Toast.makeText(requireContext(), "Works!", Toast.LENGTH_SHORT).show()
    }

    private fun nextFragment(fragment: Fragment) {
        val mainManager = parentFragmentManager
        val transaction: FragmentTransaction = mainManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out)
        transaction.remove(this)
        transaction.replace(R.id.progress_activity_fragment_container, fragment)
        transaction.commit()
    }
}