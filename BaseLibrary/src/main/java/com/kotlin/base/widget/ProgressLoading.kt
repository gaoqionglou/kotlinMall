package com.kotlin.base.widget

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.view.Gravity
import android.widget.ImageView
import androidx.annotation.StyleRes
import com.kotlin.base.R
import org.jetbrains.anko.find

class ProgressLoading private constructor(context: Context, @StyleRes themeResId: Int) :
    Dialog(context, themeResId) {

    companion object {
        private lateinit var mDialog: ProgressLoading
        private var animDrawable: AnimationDrawable? = null

        fun create(context: Context): ProgressLoading {
            mDialog = ProgressLoading(
                context,
                R.style.LightProgressDialog
            )

            mDialog.setContentView(R.layout.progress_dialog)
            mDialog.setCancelable(true)
            mDialog.setCanceledOnTouchOutside(false)
            mDialog.window?.attributes?.gravity = Gravity.CENTER
            val lp = mDialog.window?.attributes
            lp?.dimAmount = 0.2F
            mDialog.window?.attributes = lp

            val loadingView = mDialog.find<ImageView>(R.id.iv_loading)
            animDrawable = loadingView.background as AnimationDrawable
            return mDialog
        }
    }

    fun showLoading() {
        show()
        animDrawable?.start()
    }

    fun hideLoading() {
        cancel()
        animDrawable?.stop()
    }

}