package com.pesky.vegansolutiontest.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pesky.vegansolutiontest.api.IMonclerApiErrorListener
import com.pesky.vegansolutiontest.api.MonclerApiManager
import com.pesky.vegansolutiontest.model.MonclerDocument
import kotlinx.coroutines.launch

class SharedViewModel(application: Application) : AndroidViewModel(application),
    IMonclerApiErrorListener {
    val documents : MutableLiveData<List<MonclerDocument>> = MutableLiveData(listOf())
    val selectedDocument : MutableLiveData<MonclerDocument?> = MutableLiveData(null)
    val monclerApiManager = MonclerApiManager(this)
    var error = MutableLiveData<Boolean>(false)

    fun fetchDocuments() {
        viewModelScope.launch {
            monclerApiManager.getDocuments()?.let { documents.postValue(it) }
        }
    }

    fun deleteDocumentAt(position : Int)
    {
        val tmpDocuments = documents.value?.toMutableList()
        tmpDocuments?.removeAt(position)
        documents.value = tmpDocuments
    }

    fun selectDocumentAtPosition(position: Int)
    {
        selectedDocument.value = documents.value?.get(position)
    }

    fun clearError()
    {
        error.value = false
    }
    override fun notifyError() {
        error.value = true
    }
}