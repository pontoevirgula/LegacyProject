package com.chslcompany.legacyproject.data.remote

import com.chslcompany.legacyproject.data.model.Contact
import retrofit2.Call
import retrofit2.http.GET

interface ContactService {

    @GET("users")
    fun getUsers(): Call<List<Contact>>
}