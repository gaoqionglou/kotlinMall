package com.kotlin.base.widget

import android.content.Context
import android.widget.ImageView
import com.kotlin.base.utils.GlideUtils
import com.youth.banner.loader.ImageLoader

class BannerImageLoader : ImageLoader() {
    override fun displayImage(context: Context, path: Any?, imageView: ImageView) {
        GlideUtils.loadUrlImage(context, path.toString(), imageView)
    }
}