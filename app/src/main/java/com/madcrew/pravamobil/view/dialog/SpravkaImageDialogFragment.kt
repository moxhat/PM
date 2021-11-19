package com.madcrew.pravamobil.view.dialog

import android.Manifest
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.madcrew.pravamobil.BuildConfig
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentSpravkaImageDialogBinding
import com.madcrew.pravamobil.domain.BaseUrl
import com.madcrew.pravamobil.domain.BaseUrl.Companion.TOKEN
import com.madcrew.pravamobil.models.requestmodels.FullRegistrationRequest
import com.madcrew.pravamobil.models.submodels.DocumentsPhotosModel
import com.madcrew.pravamobil.utils.*
import com.madcrew.pravamobil.view.activity.education.EducationActivity
import com.madcrew.pravamobil.view.activity.progress.ProgressActivity
import com.madcrew.pravamobil.view.fragment.education.home.HomeFragment
import com.madcrew.pravamobil.view.fragment.education.home.practice.beforespravka.NoSpravkaFragment
import com.madcrew.pravamobil.view.fragment.progress.checkdata.CheckDataFragment
import com.madcrew.pravamobil.view.fragment.progress.notadult.ClientIsNotAdultFragment
import com.madcrew.pravamobil.view.fragment.progress.passportscan.PassportScanFragment
import com.madcrew.pravamobil.view.fragment.progress.snils.SnilsFragment
import com.tbruyelle.rxpermissions2.RxPermissions
import java.io.File
import java.io.InputStream


class SpravkaImageDialogFragment : DialogFragment() {

    private var _binding: FragmentSpravkaImageDialogBinding? = null
    private val binding get() = _binding!!

    lateinit var takenImageBase64: String
    private var imageIsTaken = false

    private val takeImageResult =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                latestTmpUri?.let { uri ->
                    binding.imageView45.setGone()
                    binding.textView35.setGone()
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
                binding.imageView45.setGone()
                binding.textView35.setGone()
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

    private val previewImage by lazy { binding.spravkaImageImage }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSpravkaImageDialogBinding.inflate(inflater, container, false)
        if (!(dialog == null || dialog!!.window == null)) {
            dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val parent = this.context as EducationActivity

        val clientId = Preferences.getPrefsString("clientId", requireContext()).toString()
        val schoolId = Preferences.getPrefsString("schoolId", requireContext()).toString()

        val rxPermissions = RxPermissions(this)

        binding.spravkaImageImage.setOnClickListener {
            rxPermissions
                .request(
                    Manifest.permission.CAMERA,
                )
                .subscribe { granted: Boolean ->
                    if (granted) {
                        takeImage()
                    } else {
                        Toast.makeText(requireContext(), "Нет доступа к камере", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }

        binding.btSpravkaImageScanNext.setOnClickListener {
            if (imageIsTaken) {
                Preferences.setPrefsString("loadingSpravka", "false", requireContext())
                parent.updateClientData(
                    FullRegistrationRequest(
                        TOKEN,
                        clientId,
                        schoolId,
                        images = DocumentsPhotosModel(medical = takenImageBase64)
                    )
                )
                (parentFragment as NoSpravkaFragment).setSpravkaConfirmation()
                this.dialog?.dismiss()
            } else {
                Toast.makeText(requireContext(), "Загрузите фото", Toast.LENGTH_SHORT).show()
            }
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