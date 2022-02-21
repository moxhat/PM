package com.madcrew.pravamobil.view.fragment.progress.passportscan

import android.Manifest
import androidx.fragment.app.Fragment
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.madcrew.pravamobil.BuildConfig
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentPassportScanBinding
import com.madcrew.pravamobil.domain.BaseUrl.Companion.TOKEN
import com.madcrew.pravamobil.models.requestmodels.ClientInfoRequest
import com.madcrew.pravamobil.models.requestmodels.FullRegistrationRequest
import com.madcrew.pravamobil.models.submodels.DocumentsPhotosModel
import com.madcrew.pravamobil.utils.*
import com.madcrew.pravamobil.view.activity.progress.ProgressActivity
import com.madcrew.pravamobil.view.fragment.progress.checkdata.CheckDataFragment
import com.madcrew.pravamobil.view.fragment.progress.notadult.ClientIsNotAdultFragment
import com.madcrew.pravamobil.view.fragment.progress.snils.SnilsFragment
import com.tbruyelle.rxpermissions3.RxPermissions
import java.io.File
import java.io.InputStream


class PassportScanFragment(
    var titleText: Int = R.string.passport_scan_title,
    var typeOfPage: String = "passport"
) : Fragment() {

    private var _binding: FragmentPassportScanBinding? = null
    private val binding get() = _binding!!
    private var clientIsAdult = false
    lateinit var takenImageBase64: String
    private var imageIsTaken = false

    private val takeImageResult =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                latestTmpUri?.let { uri ->
                    binding.imageView4.setGone()
                    binding.textView3.setGone()
                    val imageStream: InputStream? =
                        requireActivity().contentResolver.openInputStream(uri)
                    val selectedImage = BitmapFactory.decodeStream(imageStream)
                    val encodedImage = encodeImage(selectedImage)
                    Glide.with(requireContext()).asBitmap().centerCrop().load(uri)
                        .into(previewImage)
                    takenImageBase64 = encodedImage.toString()
                    imageIsTaken = true
                }
            }
        }

    private val selectImageFromGalleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {

                binding.imageView4.setGone()
                binding.textView3.setGone()
                val imageStream: InputStream? =
                    requireActivity().contentResolver.openInputStream(uri)
                val selectedImage = BitmapFactory.decodeStream(imageStream)
                val encodedImage = encodeImage(selectedImage)
                Glide.with(requireContext()).asBitmap().centerCrop().load(uri)
                    .into(previewImage)
                takenImageBase64 = encodedImage.toString()
                imageIsTaken = true
            }
        }

    private var latestTmpUri: Uri? = null

    private val previewImage by lazy { binding.passportImage }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPassportScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainManager = parentFragmentManager
        val parent = this.context as ProgressActivity

        val clientId = Preferences.getPrefsString("clientId", requireContext()).toString()
        val schoolId = Preferences.getPrefsString("schoolId", requireContext()).toString()

        val rxPermissions = RxPermissions(this)

        binding.avatarPhotoAnnotation.setGone()

        binding.passportScanTitle.setText(titleText)

        binding.btPassportScanBehaviorClose.setGone()

        when (typeOfPage) {
            "passport" -> parent.updateProgress("RegistrationImagePassportPage")
            "registrationAddress" -> parent.updateProgress("RegisterAddressImagePage")
            "avatar" -> {
                binding.avatarPhotoAnnotation.setVisible()
                parent.updateProgress("RegisterIPhotoPage")
                parent.getClientInfo(
                    ClientInfoRequest(
                        TOKEN,
                        schoolId,
                        clientId,
                        listOf("dateBirthday", "passport", "snils", "kpp", "format", "place")
                    )
                )
                parent.mViewModel.clientInfo.observe(viewLifecycleOwner) { response ->
                    if (response.isSuccessful) {
                        if (response.body()!!.status == "done") {
                            clientIsAdult = response.body()!!.client?.adult == "true"
                        }
                    }
                }
            }
        }

        binding.btPassportScanNext.setOnClickListener {
            if (imageIsTaken) {
                if (isOnline(requireContext())) {
                    when (typeOfPage) {
                        "passport" -> {
                            parent.updateClientData(
                                FullRegistrationRequest(
                                    TOKEN,
                                    clientId,
                                    schoolId,
                                    images = DocumentsPhotosModel(firstPage = takenImageBase64)
                                )
                            )
                            nextFragmentInProgress(mainManager, SnilsFragment())
                        }
                        "registrationAddress" -> {
                            parent.updateClientData(
                                FullRegistrationRequest(
                                    TOKEN,
                                    clientId,
                                    schoolId,
                                    images = DocumentsPhotosModel(secondPage = takenImageBase64)
                                )
                            )
                            nextFragmentInProgress(
                                mainManager,
                                PassportScanFragment(R.string.your_photo, "avatar")
                            )
                        }
                        "avatar" -> {
                            parent.updateClientData(
                                FullRegistrationRequest(
                                    TOKEN,
                                    clientId,
                                    schoolId,
                                    images = DocumentsPhotosModel(avatar = takenImageBase64)
                                )
                            )
                            if (clientIsAdult) {
                                Preferences.setPrefsString("adult", "student", requireContext())
                                nextFragmentInProgress(mainManager, CheckDataFragment("student"))
                            } else {
                                Preferences.setPrefsString("adult", "parent", requireContext())
                                nextFragmentInProgress(mainManager, ClientIsNotAdultFragment())
                            }
                        }
                    }
                } else {
                    noInternet(requireContext())
                }
            } else {
                Toast.makeText(requireContext(), "Загрузите фото", Toast.LENGTH_SHORT).show()
            }
        }

        binding.passportImage.setOnClickListener {
            BottomSheetBehavior.from(binding.passportScanChooseFrom).state =
                BottomSheetBehavior.STATE_EXPANDED
            binding.btPassportScanBehaviorClose.alphaUp(300)
            binding.btPassportScanBehaviorClose.setVisible()
        }

        binding.btPassportScanBehaviorClose.setOnClickListener {
            BottomSheetBehavior.from(binding.passportScanChooseFrom).state =
                BottomSheetBehavior.STATE_COLLAPSED
            binding.btPassportScanBehaviorClose.alphaDown(300)
            binding.btPassportScanBehaviorClose.setGone()
        }

        BottomSheetBehavior.from(binding.passportScanChooseFrom).addBottomSheetCallback(object :
            BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED && binding.btPassportScanBehaviorClose.visibility != View.GONE) {
                    binding.btPassportScanBehaviorClose.alphaDown(300)
                    binding.btPassportScanBehaviorClose.setGone()
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })

        binding.btPassportScanGallery.setOnClickListener {
            rxPermissions
                .request(
                    Manifest.permission.CAMERA,
                )
                .subscribe { granted: Boolean ->
                    if (granted) {
                        selectImageFromGallery()
                    } else {
                        Toast.makeText(requireContext(), "Нет доступа", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            BottomSheetBehavior.from(binding.passportScanChooseFrom).state =
                BottomSheetBehavior.STATE_COLLAPSED
        }

        binding.btPassportScanPhoto.setOnClickListener {
            rxPermissions
                .request(
                    Manifest.permission.CAMERA,
                )
                .subscribe { granted: Boolean ->
                    if (granted) {
                        takeImage()
                    } else {
                        Toast.makeText(requireContext(), "Нет доступа", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            BottomSheetBehavior.from(binding.passportScanChooseFrom).state =
                BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    private fun takeImage() {
        lifecycleScope.launchWhenStarted {
            getTmpFileUri().let { uri ->
                latestTmpUri = uri
                takeImageResult.launch(uri)
            }
        }
    }

    private fun selectImageFromGallery() = selectImageFromGalleryResult.launch("image/*")

    private fun getTmpFileUri(): Uri {
        val tmpFile = File.createTempFile("tmp_image_file", ".png").apply {
            createNewFile()
            deleteOnExit()
        }
        return FileProvider.getUriForFile(
            requireContext(),
            "${BuildConfig.APPLICATION_ID}.provider",
            tmpFile
        )
    }
}