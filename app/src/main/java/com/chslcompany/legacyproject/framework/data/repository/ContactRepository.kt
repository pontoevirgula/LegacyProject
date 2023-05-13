package com.chslcompany.legacyproject.framework.data.repository

import com.chslcompany.legacyproject.framework.data.model.ContactResponse
import com.chslcompany.legacyproject.framework.data.remote.ContactApi
import javax.inject.Inject

interface ContactRepository {
    suspend fun getUsers(size: Int): Resource<List<ContactResponse>>
}

class ContactRepositoryImpl @Inject constructor(
    private val api: ContactApi
) : ContactRepository {

    override suspend fun getUsers(size: Int): Resource<List<ContactResponse>> {
        return try {
            val response = api.fetchUsers(size)
            val result = response.body()
            if (response.isSuccessful && !result.isNullOrEmpty()) {
                Resource.Success(result)
            } else
                Resource.Failure(response.message())
        } catch (e: Exception) {
            Resource.Failure(e.message ?: "Ocorreu um erro inesperado.")
        }
    }
}


sealed class Resource<T>(val data : T?, val message : String?){
    class Success<T>(data : T) : Resource<T>(data, null)
    class Failure<T>(message: String) : Resource<T>(null, message)
}



