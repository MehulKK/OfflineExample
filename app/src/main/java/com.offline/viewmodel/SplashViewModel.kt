
package com.offline.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.offline.base.BaseViewModel
import com.offline.utils.result.Event
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * ViewModel for [com.offline.ui.activity.SplashActivity]
 */
class SplashViewModel @ViewModelInject constructor() : BaseViewModel() {

    companion object {
        private const val LAUNCH_DELAY = 3000L
    }

    private val _goToScreen = MutableLiveData<Event<Destination>>()
    val goToScreen: LiveData<Event<Destination>>
        get() = _goToScreen

    init {
        navigateTo()
    }

    /**
     * Decides where to navigate user.
     */
    private fun navigateTo() {
        viewModelScope.launch {
            delay(LAUNCH_DELAY)
            // TODO : Add logic to decide destination
            _goToScreen.value = Event(Destination.Sample)
        }
    }

    sealed class Destination {
        object Home : Destination()
        object Login : Destination()
        object Sample : Destination()
    }
}
