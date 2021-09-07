package com.madcrew.pravamobil.view.fragment.progress.documenttype

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentDocumentTypeBinding
import com.madcrew.pravamobil.utils.nextFragmentInProgress
import com.madcrew.pravamobil.utils.setGone
import com.madcrew.pravamobil.utils.setVisible
import com.madcrew.pravamobil.view.fragment.progress.passport.PassportFragment
import com.madcrew.pravamobil.view.fragment.progress.studentname.StudentNameFragment
import com.skydoves.powerspinner.IconSpinnerAdapter
import com.skydoves.powerspinner.IconSpinnerItem


class DocumentTypeFragment(var title2: Int = R.string.of_student, var type: String = "student") : Fragment() {

    private var _binding: FragmentDocumentTypeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDocumentTypeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinnerDocument = binding.documentTypeSpinner

        val mainManager = parentFragmentManager

        binding.documentTypeTitle2.setText(title2)

        spinnerDocument.apply {
            setSpinnerAdapter(IconSpinnerAdapter(this))
            setItems(
                arrayListOf(
                    IconSpinnerItem(text = "Паспорт РФ", iconRes = null),
                    IconSpinnerItem(text = "Иностранный паспорт", iconRes = null),
                    IconSpinnerItem(text = "РВП", iconRes = null),
                    IconSpinnerItem(text = "Вид на жительсво", iconRes = null)
                )
            )
            getSpinnerRecyclerView().layoutManager = GridLayoutManager(context, 1)
//            selectItemByIndex(0) // select an item initially.
            lifecycleOwner = this@DocumentTypeFragment
        }

        spinnerDocument.setOnSpinnerDismissListener {
            spinnerDocument.setBackgroundResource(R.drawable.ic_rectangle_light_gray)
        }

        spinnerDocument.setOnClickListener {
            spinnerDocument.setBackgroundResource(R.drawable.ic_rectangle_light_gray_half_radius)
            spinnerDocument.showOrDismiss()
        }


        spinnerDocument.setOnSpinnerItemSelectedListener<Any> { oldIndex, oldItem, newIndex, newText ->
//            if (newIndex == 0) {
//                paymentSeekCard.setGone()
//                paymentDatesCard.setGone()
//            } else {
//                paymentSeekCard.setVisible()
//                paymentDatesCard.setVisible()
//            }
        }

        binding.btDocumentTypeNext.setOnClickListener {
            when(type){
                "student" -> nextFragmentInProgress(mainManager, StudentNameFragment())
                "parent" -> nextFragmentInProgress(mainManager, PassportFragment("parent"))
            }

        }
    }
}