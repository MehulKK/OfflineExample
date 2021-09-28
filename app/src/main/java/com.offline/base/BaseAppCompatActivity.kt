
package com.offline.base

import android.content.SharedPreferences
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.offline.BR

/**
 * Base activity for all activities.
 */
abstract class BaseAppCompatActivity<Binding : ViewDataBinding, ViewModel : BaseViewModel> : AppCompatActivity() {

    companion object {
        const val PREF_NAME = "OFFLINE_EXAMPLE"
        const val PREF_USER = "USER_PREF"
    }

    protected lateinit var binding: Binding
    protected abstract val viewModel: ViewModel
    protected val prefs: Lazy<SharedPreferences> = lazy { getSharedPreferences(PREF_NAME, MODE_PRIVATE) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindViewModel()
        initializeObservers(viewModel)
    }

    /**
     * Initialize observers for [ViewModel].
     *
     * @param viewModel
     */
    open fun initializeObservers(viewModel: ViewModel) {}

    /**
    * This function will be executed when onCreate() is called.
    */
    open fun initialize() {}

    /**
     * Get the layout resource ID for the screen.
     */
    @LayoutRes
    abstract fun getLayoutResId(): Int

    private fun bindViewModel() {
        binding = DataBindingUtil.setContentView(this, getLayoutResId())
        binding.apply {
            lifecycleOwner = this@BaseAppCompatActivity
            setVariable(BR.viewModel, viewModel)
        }
        binding.executePendingBindings()
        initialize()
    }
}
