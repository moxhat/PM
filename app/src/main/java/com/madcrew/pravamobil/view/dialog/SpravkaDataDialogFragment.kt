package com.madcrew.pravamobil.view.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import com.madcrew.pravamobil.databinding.FragmentSpravkaDataDialogBinding
import com.madcrew.pravamobil.domain.BaseUrl
import com.madcrew.pravamobil.domain.BaseUrl.Companion.TOKEN
import com.madcrew.pravamobil.models.requestmodels.FullRegistrationRequest
import com.madcrew.pravamobil.models.submodels.MedInfoModel
import com.madcrew.pravamobil.models.submodels.ParentModel
import com.madcrew.pravamobil.models.submodels.PassportModel
import com.madcrew.pravamobil.utils.*
import com.madcrew.pravamobil.view.activity.education.EducationActivity
import com.madcrew.pravamobil.view.fragment.education.home.practice.beforespravka.NoSpravkaFragment
import com.madcrew.pravamobil.view.fragment.progress.checkdata.CheckDataFragment
import com.madcrew.pravamobil.view.fragment.progress.parentphone.ParentPhoneNumberFragment
import com.madcrew.pravamobil.view.fragment.progress.passportscan.PassportScanFragment


class SpravkaDataDialogFragment : DialogFragment() {

    private var _binding: FragmentSpravkaDataDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSpravkaDataDialogBinding.inflate(inflater, container, false)
        if (!(dialog == null || dialog!!.window == null)) {
            dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val seriesField = binding.spravkaDataSeries
        val seriesText = binding.spravkaDataSeriesText
        val numberField = binding.spravkaDataNumber
        val numberText = binding.spravkaDataNumberText
        val centerField = binding.spravkaDataCenter
        val centerText = binding.spravkaDataCenterText
        val licenseField = binding.spravkaDataLicense
        val licenseText = binding.spravkaDataLicenseText
        val dateField = binding.spravkaDataDate
        val dateText = binding.spravkaDataDateText

        val clientId = Preferences.getPrefsString("clientId", requireContext()).toString()
        val schoolId = Preferences.getPrefsString("schoolId", requireContext()).toString()

        val parent = this.context as EducationActivity

        seriesText.doOnTextChanged{_,_,_,_ ->
            if(seriesText.length() > 1) seriesField.setErrorOff()
            if (seriesText.length() == 6) numberText.requestFocus()
        }

        numberText.doOnTextChanged{_,_,_,_ ->
            if(numberText.length() > 1) numberField.setErrorOff()
            if (numberText.length() == 6) centerText.requestFocus()
        }

        dateText.doOnTextChanged{_,_,_,_ ->
            if(dateText.length() > 1) dateField.setErrorOff()
            if (dateText.length() == 10) dateText.hideKeyboard()
        }

        licenseText.doOnTextChanged{_,_,_,_ ->
            if(licenseText.length() > 1) licenseField.setErrorOff()
        }

        centerText.doOnTextChanged{_,_,_,_ ->
            if(centerText.length() > 1) centerField.setErrorOff()
        }



        binding.btSpravkaDataNext.setOnClickListener {
            if (seriesText.length() == 6 && numberText.length() == 6 && centerText.length() > 3 && dateText.length() == 10 && licenseText.length() > 3){
                val series = seriesText.text.toString()
                val number = numberText.text.toString()
                val center = centerText.text.toString()
                val license = licenseText.text.toString()
                val date = dateText.text.toString()
                parent.updateClientData(FullRegistrationRequest(TOKEN, clientId, schoolId, medInfo = MedInfoModel(series, number, center, license, date)))
                (parentFragment as NoSpravkaFragment).showSpravkaImageDialog()
                this.dialog?.dismiss()
            } else {
                if (seriesText.length() < 6) seriesField.setErrorOn()
                if (numberText.length() < 6) numberField.setErrorOn()
                if (centerText.length() < 3) centerField.setErrorOn()
                if (dateText.length() < 10) dateField.setErrorOn()
                if (licenseText.length() < 3) licenseField.setErrorOn()
            }
        }
    }
}