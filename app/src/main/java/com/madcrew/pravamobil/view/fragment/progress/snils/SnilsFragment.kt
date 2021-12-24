package com.madcrew.pravamobil.view.fragment.progress.snils

import android.Manifest
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.madcrew.pravamobil.BuildConfig
import com.madcrew.pravamobil.databinding.FragmentSnilsBinding
import com.madcrew.pravamobil.domain.BaseUrl.Companion.TOKEN
import com.madcrew.pravamobil.models.requestmodels.ClientInfoRequest
import com.madcrew.pravamobil.models.requestmodels.FullRegistrationRequest
import com.madcrew.pravamobil.models.submodels.DocumentsPhotosModel
import com.madcrew.pravamobil.utils.*
import com.madcrew.pravamobil.view.activity.progress.ProgressActivity
import com.madcrew.pravamobil.view.fragment.progress.address.AddressFragment
import com.madcrew.pravamobil.view.fragment.progress.checkdata.CheckDataFragment
import com.tbruyelle.rxpermissions2.RxPermissions
import java.io.File
import java.io.InputStream


class SnilsFragment : Fragment() {

    private var _binding: FragmentSnilsBinding? = null
    private val binding get() = _binding!!

    private lateinit var takenImageBase64: String
    private var imageIsTaken = false

    private val takeImageResult =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                latestTmpUri?.let { uri ->
                    binding.imageView3.setGone()
                    binding.textView2.setGone()
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

                binding.imageView3.setGone()
                binding.textView2.setGone()
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

    //    private val selectImageFromGalleryResult = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
//        uri?.let { previewImage.setImageURI(uri) }
//    }

    private var latestTmpUri: Uri? = null

    private val previewImage by lazy { binding.snilsScanImage }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSnilsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val parent = this.context as ProgressActivity
        val rxPermissions = RxPermissions(this)

        val clientId = Preferences.getPrefsString("clientId", requireContext()).toString()
        val schoolId = Preferences.getPrefsString("schoolId", requireContext()).toString()
        val checkData = Preferences.getPrefsString("checkData", requireContext()) == "true"

        val snilsText = binding.snilsNumberText
        val snilsField = binding.snilsNumber

        if (checkData) {
            parent.getClientInfo(
                ClientInfoRequest(
                    TOKEN,
                    schoolId,
                    clientId,
                    listOf("dateBirthday", "passport", "snils", "kpp", "format", "place")
                )
            )
            binding.snilsScanImage.setDisable()
            binding.snilsCard.setDisable()
            parent.mViewModel.clientInfo.observe(viewLifecycleOwner, { response ->
                if (response.isSuccessful) {
                    if (response.body()!!.status == "done" && response.body()!!.client?.snils != null) {
                        val snils = response.body()!!.client?.snils.toString().replace("-", "")
                        snilsText.setText(snils)
                    }
                }
            })
        } else {
            parent.updateProgress("RegistrationSnilsPage")
            binding.snilsScanImage.setEnable()
            binding.snilsCard.setEnable()
        }


        snilsText.doOnTextChanged { _, _, _, _ ->
            if (snilsText.length() > 1) snilsField.setErrorOff()
            if (snilsText.length() == 14) snilsField.hideKeyboard()
        }

        binding.btSnilsNext.setOnClickListener {
            if (snilsText.length() < 14) {
                snilsField.setErrorOn()
            } else {
                if (checkData) {
                    val snils = snilsText.text.toString()
                    parent.updateClientData(
                        FullRegistrationRequest(
                            TOKEN,
                            clientId,
                            schoolId,
                            snils = snils
                        )
                    )
                    nextFragmentInProgress(
                        parentFragmentManager,
                        CheckDataFragment("student")
                    )
                } else {
                    if (imageIsTaken) {
                        val snils = snilsText.text.toString()
                        if (isOnline(requireContext())) {
                            parent.updateClientData(
                                FullRegistrationRequest(
                                    TOKEN,
                                    clientId,
                                    schoolId,
                                    snils = snils,
                                    images = DocumentsPhotosModel(snils = takenImageBase64)
                                )
                            )
                            nextFragmentInProgress(parentFragmentManager, AddressFragment())
                        } else {
                            noInternet(requireContext())
                        }
                    } else {
                        Toast.makeText(requireContext(), "Загрузите фото СНИЛС", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }

        binding.snilsScanImage.setOnClickListener {
            BottomSheetBehavior.from(binding.snilsChooseFrom).state =
                BottomSheetBehavior.STATE_EXPANDED
            binding.btSnilsBehaviorClose.alphaUp(300)
            binding.btSnilsBehaviorClose.setVisible()
        }

        binding.btSnilsBehaviorClose.setOnClickListener {
            BottomSheetBehavior.from(binding.snilsChooseFrom).state =
                BottomSheetBehavior.STATE_COLLAPSED
            binding.btSnilsBehaviorClose.alphaDown(300)
            binding.btSnilsBehaviorClose.setGone()
        }

        BottomSheetBehavior.from(binding.snilsChooseFrom).addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED && binding.btSnilsBehaviorClose.visibility != View.GONE) {
                    binding.btSnilsBehaviorClose.alphaDown(300)
                    binding.btSnilsBehaviorClose.setGone()
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })

        binding.btSnilsGallery.setOnClickListener {
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
            BottomSheetBehavior.from(binding.snilsChooseFrom).state =
                BottomSheetBehavior.STATE_COLLAPSED
        }

        binding.btSnilsPhoto.setOnClickListener {
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
            BottomSheetBehavior.from(binding.snilsChooseFrom).state =
                BottomSheetBehavior.STATE_COLLAPSED
        }

}

private fun selectImageFromGallery() = selectImageFromGalleryResult.launch("image/*")


private fun takeImage() {
    lifecycleScope.launchWhenStarted {
        getTmpFileUri().let { uri ->
            latestTmpUri = uri
            takeImageResult.launch(uri)
        }
    }
}

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