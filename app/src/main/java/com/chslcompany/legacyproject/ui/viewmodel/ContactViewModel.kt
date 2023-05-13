package com.chslcompany.legacyproject.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chslcompany.legacyproject.framework.data.model.ContactResponse
import com.chslcompany.legacyproject.framework.data.repository.ContactRepository
import com.chslcompany.legacyproject.framework.data.repository.Resource
import com.chslcompany.legacyproject.ui.util.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    private val repository: ContactRepository,
    private val dispatchers : DispatcherProvider
): ViewModel() {

    private var sizeContacts: Int = 0
    private val _users = MutableStateFlow<StateUi>(StateUi.EmptyUi)
    val users : StateFlow<StateUi> get() = _users

    init {
        sizeContacts = 10
        getUsers(sizeContacts)
    }

    private fun getUsers(size : Int) {
        viewModelScope.launch(dispatchers.io) {
            _users.value = StateUi.Loading
            when(val response = repository.getUsers(size)) {
                is Resource.Failure -> {
                    _users.value = StateUi.Error(
                        response.message ?: "Falha na conexão.\nTente novamente mais tarde."
                    )
                }
                is Resource.Success -> {
                    if (response.data.isNullOrEmpty()) {
                        _users.value = StateUi.EmptyUi
                    } else
                        _users.value = StateUi.Success(response.data)
                }
            }
        }
    }


    sealed class StateUi {
        class Success(val data : List<ContactResponse>) : StateUi()
        class Error(val errorText : String) : StateUi()
        object EmptyUi : StateUi()
        object Loading : StateUi()
    }

}