package com.pesky.vegansolutiontest.api

import com.pesky.vegansolutiontest.model.MonclerResponse
import retrofit2.Response
import retrofit2.http.GET

interface IMonclergroupApi {

    @GET("getPressReleasesDocs")
    suspend fun getDocumentsData(): Response<MonclerResponse>
}