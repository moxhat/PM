package com.madcrew.pravamobil.view.activity.progress

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.ActivityProgressBinding
import com.madcrew.pravamobil.view.fragment.progress.addpassword.AddPasswordFragment
import com.madcrew.pravamobil.view.fragment.progress.address.AddressFragment
import com.madcrew.pravamobil.view.fragment.progress.category.CategoryFragment
import com.madcrew.pravamobil.view.fragment.progress.checkdata.CheckDataFragment
import com.madcrew.pravamobil.view.fragment.progress.confirmcontract.ConfirmContractFragment
import com.madcrew.pravamobil.view.fragment.progress.documenttype.DocumentTypeFragment
import com.madcrew.pravamobil.view.fragment.progress.email.EmailFragment
import com.madcrew.pravamobil.view.fragment.progress.filial.FilialFragment
import com.madcrew.pravamobil.view.fragment.progress.notadult.ClientIsNotAdultFragment
import com.madcrew.pravamobil.view.fragment.progress.parentphone.ParentPhoneNumberFragment
import com.madcrew.pravamobil.view.fragment.progress.passport.PassportFragment
import com.madcrew.pravamobil.view.fragment.progress.passportscan.PassportScanFragment
import com.madcrew.pravamobil.view.fragment.progress.paymnetoptions.PaymentOptionsFragment
import com.madcrew.pravamobil.view.fragment.progress.snils.SnilsFragment
import com.madcrew.pravamobil.view.fragment.progress.studentname.StudentNameFragment
import com.madcrew.pravamobil.view.fragment.progress.tariff.TariffFragment
import com.madcrew.pravamobil.view.fragment.progress.theory.SelectTheoryFragment
import com.madcrew.pravamobil.view.fragment.progress.transmission.TransmissionFragment

class ProgressActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProgressBinding
    private var status = "AddPassword"
    private var isAdult = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProgressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val owner = if (isAdult) {
            "student"
        } else {
            "parent"
        }

        val window: Window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.main)
        val currentFragment: Fragment =
            when (status) {
                "RegisterEmailPage" -> EmailFragment()
                "SelectCategotyPage" -> CategoryFragment()
                "RegisterTransmissionPage" -> TransmissionFragment()
                "RegisterFormatEducationPage" -> SelectTheoryFragment()
                "SelectFilialAndGroup" -> FilialFragment()
                "SelectTariffPage" -> TariffFragment()
//            "SelectPayer" ->
                "SelectPayment" -> PaymentOptionsFragment()
                "SelectTypeDocument" -> DocumentTypeFragment(type = "student")
                "RegisterPersonalDataPage" -> StudentNameFragment(type = "student")
                "RegisterPassportPage" -> PassportFragment("student")
                "RegistrationImagePassportPage" -> PassportScanFragment(typeOfPage = "passport")
                "RegistrationSnilsPage" -> SnilsFragment()
                "RegisterAddressPage" -> AddressFragment()
//            "RegisterLivingPage" ->
                "RegisterAddressImagePage" -> PassportScanFragment(
                    titleText = R.string.registration_scan_title,
                    typeOfPage = "registrationAddress"
                )
                "RegisterIPhotoPage" -> PassportScanFragment(
                    typeOfPage = "avatar",
                    titleText = R.string.your_photo
                )
                "RegisterParentInfoPage" -> ClientIsNotAdultFragment()
                "RegisterParentDataPage" -> StudentNameFragment(
                    type = "parent",
                    title = R.string.student_representative
                )
                "SelectTypeParentDocument" -> DocumentTypeFragment(
                    title2 = R.string.representatives,
                    type = "parent"
                )
                "RegisterParentPassportPage" -> PassportFragment(type = "parent")
                "RegisterParentPhonePage" -> ParentPhoneNumberFragment()
                "CheckDataPage" -> CheckDataFragment(owner)
                "ConfirmContractPage" -> ConfirmContractFragment(owner)
//            "PaymentPage" ->
//            "ContractFail" ->
//            "ContractComplete" ->
//            "InstructionPage" ->
                else -> AddPasswordFragment()
            }

        setUpFirstFragment(currentFragment)
    }

    private fun setUpFirstFragment(fragment: Fragment) {
        val mainManager = supportFragmentManager
        val transaction: FragmentTransaction = mainManager.beginTransaction()
        transaction.replace(R.id.progress_activity_fragment_container, fragment)
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
        transaction.commit()
    }
}