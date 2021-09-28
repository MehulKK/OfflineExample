
package com.offline.ui.activity

import androidx.activity.viewModels
import com.offline.R
import com.offline.base.BaseAppCompatActivity
import com.offline.databinding.ActivitySampleBinding
import com.offline.model.data.User
import com.offline.ui.adapter.UserAdapter
import com.offline.utils.RecyclerPaginationListener
import com.offline.utils.extension.*
import com.offline.utils.pref.StringPreference
import com.offline.viewmodel.SampleViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SampleActivity : BaseAppCompatActivity<ActivitySampleBinding, SampleViewModel>() {
    var userPref by StringPreference(prefs, PREF_USER, null)
    override val viewModel: SampleViewModel by viewModels()

    override fun getLayoutResId(): Int = R.layout.activity_sample

    private val userAdapter = UserAdapter()
    private val paginationListener = RecyclerPaginationListener {
        viewModel.loadMoreUsers()
    }

    override fun initialize() {
        super.initialize()
        binding.rvUsers.adapter = userAdapter
        binding.rvUsers.addOnScrollListener(paginationListener)
    }

    override fun initializeObservers(viewModel: SampleViewModel) {
        super.initializeObservers(viewModel)

        binding.shimmer.startShimmer()

        viewModel.showShimmer.observe(this) {
            if (it) {
                binding.shimmer.startShimmer()
            } else {
                binding.shimmer.stopShimmer()
            }
        }

        viewModel.isLoadingPage.observe(this) {
            if (it) {
                userAdapter.addLoader()
            } else {
                userAdapter.removeLoader()
            }
        }

        viewModel.onNewUserList.observeEvent(this) {
            userAdapter.addAllItem(ArrayList(it))
            userPref = toList<List<User>>(userAdapter.getListItems())
        }

        viewModel.onInternetError.observe(this) {
            userPref?.let {
                val yourList: List<User> = fromJson<List<User>>(it)
                userAdapter.addAllItem(ArrayList(yourList))
                binding.rvUsers.clearOnScrollListeners()
            }
        }
    }
}
