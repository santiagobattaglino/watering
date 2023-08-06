package com.santiago.battaglino.esp32.presentation.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowInsetsControllerCompat
import androidx.viewbinding.ViewBinding
import com.santiago.battaglino.esp32.R

abstract class BaseHomeFragment<VB : ViewBinding>(private val isAppearanceLightStatusBars: Boolean = false) :
    BaseFragment() {

    protected var viewBinding: VB? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        viewBinding = inflateViewBinding(inflater, container)
        activity?.window?.let { window ->
            viewBinding?.root?.let { view ->
                window.statusBarColor = if (isAppearanceLightStatusBars) {
                    resources.getColor(R.color.header, null)
                } else {
                    resources.getColor(R.color.primary, null)
                }
                WindowInsetsControllerCompat(window, view).isAppearanceLightStatusBars =
                    isAppearanceLightStatusBars
            }
        }
        return viewBinding?.root
    }

    override fun onDestroyView() {
        viewBinding = null
        super.onDestroyView()
    }

    override fun setUpViews() {

    }

    abstract fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB
}