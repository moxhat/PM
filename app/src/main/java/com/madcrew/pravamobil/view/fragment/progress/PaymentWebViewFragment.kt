package com.madcrew.pravamobil.view.fragment.progress

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.FragmentTransaction
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentPaymentSummBinding
import com.madcrew.pravamobil.databinding.FragmentPaymentWebViewBinding
import com.madcrew.pravamobil.domain.BaseUrl
import com.madcrew.pravamobil.domain.BaseUrl.Companion.TOKEN
import com.madcrew.pravamobil.models.requestmodels.ChekPaymentStatusRequest
import com.madcrew.pravamobil.utils.Preferences
import com.madcrew.pravamobil.utils.isOnline
import com.madcrew.pravamobil.utils.noInternet
import com.madcrew.pravamobil.utils.previousFragmentInProgress
import com.madcrew.pravamobil.view.activity.paymentsoptions.PaymentsOptionsActivity
import com.madcrew.pravamobil.view.activity.progress.ProgressActivity


class PaymentWebViewFragment(var first: Boolean = true, var paymentUrl: String) : Fragment() {

    private var _binding: FragmentPaymentWebViewBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentWebViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val webView = binding.paymentWebView

        val webSettings = webView.settings
        webSettings.allowContentAccess = true
        webSettings.allowFileAccess = true
        webSettings.useWideViewPort = true
        webSettings.loadWithOverviewMode = true
        webSettings.javaScriptEnabled = true
        webSettings.cacheMode
        webView.loadUrl(paymentUrl)
        webView.setBackgroundColor(0x00000000)

        webView.webViewClient = object : WebViewClient() {
            override fun onLoadResource(view: WebView, url: String) {
                if (url == paymentUrl) return
                if (url.endsWith("return.html")) {
                    binding.btPaymentWebViewBack.performClick()
                }
            }
        }

        binding.btPaymentWebViewBack.setOnClickListener {
            if (first){
                val schoolId = Preferences.getPrefsString("schoolId", requireContext()).toString()
                val clientId = Preferences.getPrefsString("clientId", requireContext()).toString()
                val currentParent = this.context as ProgressActivity
                if (isOnline(requireContext())){
                        currentParent.mViewModel.chekPaymentStatus(ChekPaymentStatusRequest(TOKEN, schoolId.toInt(), clientId.toInt(), false, true))
                } else {
                    noInternet(requireContext())
                }
                val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
                transaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_right_out)
                transaction.remove(this)
                transaction.commit()
            } else {
                val parent = this.context as PaymentsOptionsActivity
                parent.finish()
            }
            }

        }
    }
