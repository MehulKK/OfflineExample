
package com.offline.ui.activity

import androidx.activity.viewModels
import com.offline.R
import com.offline.base.BaseAppCompatActivity
import com.offline.databinding.ActivitySplashBinding
import com.offline.utils.extension.launchActivity
import com.offline.utils.extension.observeEvent
import com.offline.viewmodel.SplashViewModel
import com.offline.viewmodel.SplashViewModel.Destination
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseAppCompatActivity<ActivitySplashBinding, SplashViewModel>() {

    override val viewModel: SplashViewModel by viewModels()

    override fun getLayoutResId(): Int = R.layout.activity_splash

    override fun initializeObservers(viewModel: SplashViewModel) {
        super.initializeObservers(viewModel)

        viewModel.goToScreen.observeEvent(this) { destination ->
            when (destination) {
                Destination.Home -> openHomeScreen()
                Destination.Login -> openLoginScreen()
                Destination.Sample -> openSampleScreen()
            }
        }
    }

    private fun openLoginScreen() {
        // TODO : Open Login screen
    }

    private fun openHomeScreen() {
        // TODO : Open Home screen
    }

    private fun openSampleScreen() {
        launchActivity<SampleActivity>()
    }
}
