
package com.offline.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.offline.base.BaseViewModel
import com.offline.data.repository.UserRepository
import com.offline.model.data.User
import com.offline.utils.result.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.net.UnknownHostException

/**
 * ViewModel for [com.offline.ui.activity.SampleActivity]
 */
class SampleViewModel @ViewModelInject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val _onInternetError = MutableLiveData<Boolean>()
    val onInternetError: LiveData<Boolean>
        get() = _onInternetError

    private val _onNewUserList = MutableLiveData<Event<List<User>>>()
    val onNewUserList: LiveData<Event<List<User>>>
        get() = _onNewUserList

    private val _isLoadingPage = MutableLiveData<Boolean>()
    val isLoadingPage: LiveData<Boolean>
        get() = _isLoadingPage

    private val _showShimmer = MutableLiveData<Boolean>()
    val showShimmer: LiveData<Boolean>
        get() = _showShimmer

    private var currentPage = 0

    init {
        loadMoreUsers()
    }

    fun loadMoreUsers() {
        _isLoadingPage.value = true
        currentPage += 1

        if (currentPage == 1) {
            _showShimmer.value = true
        }

        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                userRepository.loadUsers(currentPage)
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    _isLoadingPage.value = false
                    _onNewUserList.value = Event(it)
                    if (currentPage == 1) {
                        _showShimmer.value = false
                    }
                }
            }.onFailure {
                Timber.e(it)
                withContext(Dispatchers.Main) {
                    _isLoadingPage.value = false
                    if (currentPage == 1) {
                        _showShimmer.value = false
                    }
                    if (it is UnknownHostException) {
                        _onInternetError.value = true
                    }
                    currentPage -= 1 // Revert increased count
                }
            }
        }
    }
}
