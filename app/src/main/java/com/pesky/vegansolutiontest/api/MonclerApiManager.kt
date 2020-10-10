package com.pesky.vegansolutiontest.api

import android.util.Log
import com.pesky.vegansolutiontest.model.MonclerDocument
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MonclerApiManager(val errorListener : IMonclerApiErrorListener) {
    private var service: IMonclergroupApi
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.monclergroup.com/wp-json/mobileApp/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(IMonclergroupApi::class.java)
    }

    suspend fun getDocuments() : List<MonclerDocument>?{
        return try {
            service.getDocumentsData().body()?.content
        } catch (exception: Exception) {
            if (exception is HttpException) {
                Log.e(javaClass.name, "Network error : ${exception.code()}")
            } else {
                exception.printStackTrace()
            }
            this.errorListener.notifyError()
            null
        }
    }


}

interface IMonclerApiErrorListener {
    fun notifyError()
}