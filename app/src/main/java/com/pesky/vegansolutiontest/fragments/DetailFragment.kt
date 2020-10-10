package com.pesky.vegansolutiontest.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.pesky.vegansolutiontest.R
import com.pesky.vegansolutiontest.model.MonclerDocument
import com.pesky.vegansolutiontest.viewmodels.SharedViewModel
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.selectedDocument.observe(viewLifecycleOwner, Observer {
            it?.let {document ->
                Log.d(javaClass.name, "$document")
                fragment_detail_title.setText(document.mediaTitleCustom)
                fragment_detail_date.setText(document.mediaDate.dateString)
                fragment_detail_link.setText(getSpannableString(document.mediaUrl))
                fragment_detail_link.movementMethod = LinkMovementMethod.getInstance()
                setupDocumentPreviewdocument(document)
            }
        })
    }

    private fun getSpannableString(url : String): SpannableString {
        val tmpSpannable = SpannableString("Click HERE to open the document")
        val linkSpan  = object : ClickableSpan()
        {
            override fun onClick(p0: View) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
            }
        }
        tmpSpannable.setSpan(linkSpan,6,10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return tmpSpannable
    }

    private fun setupDocumentPreviewdocument(document: MonclerDocument)
    {
        fragment_detail_webview.webViewClient = WebViewClient()
        fragment_detail_webview.settings.setSupportZoom(true)
        fragment_detail_webview.settings.javaScriptEnabled = true
        fragment_detail_webview.loadUrl("https://docs.google.com/gview?embedded=true&url=${document.mediaUrl}")
    }

}