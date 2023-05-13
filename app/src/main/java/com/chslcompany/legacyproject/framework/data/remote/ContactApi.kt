package com.chslcompany.legacyproject.framework.data.remote

import com.chslcompany.legacyproject.framework.data.model.ContactResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ContactApi {

    @GET("users")
    suspend fun fetchUsers(
        @Query("size") size : Int
    ) : Response<List<ContactResponse>>
}