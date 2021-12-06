package dev.jakal.codechallenge.ui.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import dev.jakal.codechallenge.ui.common.viewBinding.FragmentViewBindingDelegate

inline fun <T : ViewBinding> AppCompatActivity.viewBinding(crossinline bindingInflater: (LayoutInflater) -> T) =
    lazy(LazyThreadSafetyMode.NONE) {
        bindingInflater.invoke(layoutInflater)
    }

fun <T : ViewBinding> Fragment.viewBinding(viewBindingFactory: (View) -> T) =
    FragmentViewBindingDelegate(this, viewBindingFactory)

inline fun <T : ViewBinding> ViewGroup.viewBinding(
    crossinline factory: (LayoutInflater, ViewGroup, Boolean) -> T,
    attachToRoot: Boolean = false
) = factory(LayoutInflater.from(context), this, attachToRoot)
