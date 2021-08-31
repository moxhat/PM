package com.madcrew.pravamobil.view.fragment.progress.theorygroup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.madcrew.pravamobil.adapter.GroupListRecyclerAdapter
import com.madcrew.pravamobil.databinding.FragmentTheoryGroupBinding
import com.madcrew.pravamobil.models.GroupTimes

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
            GroupTimes("12 июля", "Пн, Ср 18:30-20:00"),
            GroupTimes("12 июля", "Пн, Ср 18:30-20:00"),
            GroupTimes("12 июля", "Пн, Ср 18:30-20:00"),
            GroupTimes("12 июля", "Пн, Ср 18:30-20:00"),
            GroupTimes("12 июля", "Пн, Ср 18:30-20:00"),
            GroupTimes("12 июля", "Пн, Ср 18:30-20:00"),
            GroupTimes("12 июля", "Пн, Ср 18:30-20:00"),
            GroupTimes("12 июля", "Пн, Ср 18:30-20:00"),
            GroupTimes("12 июля", "Пн, Ср 18:30-20:00")
        )

        mAdapter = GroupListRecyclerAdapter(mGroupTimeList, this)

        binding.recyclerTheoryChoose.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mAdapter
        }
    }

    override fun onGroupClick(itemView: View?, position: Int) {
        Toast.makeText(requireContext(), "Works!", Toast.LENGTH_SHORT).show()
    }
}