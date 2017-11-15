package com.dp.logcatapp.fragments.base

import android.os.Handler
import android.os.Looper
import android.support.v4.app.DialogFragment
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils

open class BaseDialogFragment : DialogFragment() {
    private val handler = Handler()

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        var animation: Animation? = super.onCreateAnimation(transit, enter, nextAnim)

        if (animation == null && nextAnim != 0) {
            animation = AnimationUtils.loadAnimation(activity, nextAnim)
        }

        if (animation != null) {
            val view = view
            if (view != null) {
                val layerType = view.layerType
                view.setLayerType(View.LAYER_TYPE_HARDWARE, null)

                animation.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation) {}

                    override fun onAnimationEnd(animation: Animation) {
                        view.setLayerType(layerType, null)
                    }

                    override fun onAnimationRepeat(animation: Animation) {}
                })
            }
        }

        return animation
    }

    override fun onDestroyView() {
        if (dialog != null && retainInstance)
            dialog.setDismissMessage(null)
        super.onDestroyView()
    }

    protected fun runOnUIThread(runnable: () -> Unit) {
        if (Thread.currentThread() == Looper.getMainLooper().thread) {
            runnable()
        } else {
            handler.post(runnable)
        }
    }
}