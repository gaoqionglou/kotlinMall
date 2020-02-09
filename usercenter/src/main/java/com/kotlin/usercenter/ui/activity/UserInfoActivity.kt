package com.kotlin.usercenter.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.jph.takephoto.app.TakePhoto
import com.jph.takephoto.app.TakePhotoImpl
import com.jph.takephoto.compress.CompressConfig
import com.jph.takephoto.model.TResult
import com.kotlin.base.common.BaseConstant
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.base.utils.AppPrefsUtils
import com.kotlin.base.utils.DateUtils
import com.kotlin.base.utils.GlideUtils
import com.kotlin.provider.common.ProviderConstant
import com.kotlin.user.utils.UserPrefsUtils
import com.kotlin.usercenter.R
import com.kotlin.usercenter.data.protocol.UserInfo
import com.kotlin.usercenter.injection.component.DaggerUserComponent
import com.kotlin.usercenter.injection.module.UserModule
import com.kotlin.usercenter.presenter.UserInfoPresenter
import com.kotlin.usercenter.presenter.view.UserInfoView
import com.qiniu.android.http.ResponseInfo
import com.qiniu.android.storage.UpCompletionHandler
import com.qiniu.android.storage.UploadManager

import kotlinx.android.synthetic.main.activity_user_info.*
import org.jetbrains.anko.toast
import org.json.JSONObject
import java.io.File

class UserInfoActivity : BaseMvpActivity<UserInfoPresenter>(), UserInfoView,
    TakePhoto.TakeResultListener {


    lateinit var mTakePhoto: TakePhoto
    lateinit var mTempFile: File
    private val mUploadManager: UploadManager by lazy { UploadManager() }

    private var mLocalFileUrl: String? = null

    private var mRemoteFileUrl: String? = null

    private var mUserIcon: String? = null
    private var mUserName: String? = null
    private var mUserGender: String? = null
    private var mUserSign: String? = null
    private var mUserMobile: String? = null

    override fun initComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent).userModule(UserModule())
            .build().inject(this)
        mPresenter.mView = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)
        mTakePhoto = TakePhotoImpl(this, this)
        mTakePhoto.onCreate(savedInstanceState)
        initVIew()
        initData()

    }


    private fun initVIew() {
        mUserIconView.onClick {
            showAlertView()
        }
        mHeaderBar.getRightTextView()?.onClick {
            mPresenter.editUser(
                mRemoteFileUrl!!,
                mUserNameEt.text.toString(),
                if (mGenderMaleRb.isChecked) "0" else "1",
                mUserSignEt.text.toString()
            )
        }
    }

    private fun initData() {
        mUserIcon = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_ICON)
        mUserName = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_NAME)
        mUserSign = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_SIGN)
        mUserGender = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_GENDER)

        mRemoteFileUrl = mUserIcon
        if (mUserIcon != "") {
            GlideUtils.loadUrlImage(this, mUserIcon!!, mUserIconIv)
        }

        mUserNameEt.setText(mUserName)
        if (mUserGender == "0") {
            mGenderMaleRb.isChecked = true
        } else {
            mGenderFemaleRb.isChecked = true
        }

        mUserSignEt.setText(mUserSign)
        mUserMobileTv.text = mUserMobile

    }

    private fun showAlertView() {
        AlertView(
            "选择图片",
            null,
            "取消",
            null,
            arrayOf("拍照", "相册"),
            this,
            AlertView.Style.ActionSheet,
            object : OnItemClickListener {
                override fun onItemClick(o: Any?, position: Int) {
                    //启动图片压缩
                    mTakePhoto.onEnableCompress(CompressConfig.ofDefaultConfig(), false)
                    when (position) {
                        0 -> {
                            createTempFIle()
                            mTakePhoto.onPickFromCapture(Uri.fromFile(mTempFile))
                        }
                        1 -> {
                            mTakePhoto.onPickFromGallery()
                        }
                    }
                }

            }).show()
    }

    /**
     * App专属文件
    这类文件应该是随着app删除而一起删除的，它们可以被存储在两个地方：internal storage 和 external storage 。 internal storage就是手机自带的一块存储区域，通常很小；external storage就是通常所说的SD卡，通常很大，有16GB,32GB等。

    internal storage很小，所以你就应该很正确的使用它，因为SD卡有可能会被用户卸下，换成新的，所以SD卡不是任何时间都可用的，因此我们必须将一些重要的数据库文件以及一些用户配置文件存放在internal storage中。将一些大的图片或文件等缓存放到external storage中。

    存储在internal storage
    这是你app私有的目录，你的shared preference文件，数据库文件，都存储在这里。目录为data/data/< package name >/files/
    访问方法为：

    File filesDir = getFilesDir();
    Log.i(TAG,"file_dir="+filesDir);


    存储在external storage
    这类文件不应该存在SD卡的根目录下，而应该存在mnt/sdcard/Android/data/< package name >/files/…这个目录下。这类文件应该随着App的删除而一起删除。例如一种格式的电子书，只有该app才可以打开，如果用户删除了该app，那么留下来的电子书就成为了一种无法打开的垃圾文件，所以应该随着该app一起删除掉。

    获得这个路径的方法：

    File externalFilesDir = getExternalFilesDir(null); //需要文件读写权限
    Log.i(TAG, "externalFileDir = "+externalFilesDir);

     */
    fun createTempFIle() {
        val tempFileName = "${DateUtils.curTime}.png"
        if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            //SD卡已装入
            mTempFile = File(getExternalFilesDir(null), tempFileName)
            return
        }
        //没有sd卡
        mTempFile = File(filesDir, tempFileName)

    }

    override fun takeSuccess(result: TResult?) {
        Log.i("TakePhoto", "takeSuccess 原始路径originalPath :  ${result?.image?.originalPath}")
        Log.i("TakePhoto", "takeSuccess 压缩后路径compressPath :  ${result?.image?.compressPath}")
        mLocalFileUrl = result?.image?.compressPath
        //获取七牛云凭证
        mPresenter.getUploadToken()
    }

    override fun takeCancel() {

    }

    override fun takeFail(result: TResult?, msg: String?) {
        Log.i("TakePhoto", "takeFail  :  $msg")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mTakePhoto.onActivityResult(requestCode, resultCode, data)
    }

    override fun onGetUploadToken(result: String) {
        Log.i("TakePhoto", "onGetUploadToken   :  $result")
        mUploadManager.put(mLocalFileUrl, null, result, object : UpCompletionHandler {
            override fun complete(key: String?, info: ResponseInfo?, response: JSONObject?) {
                //获取完整的图片地址
                mRemoteFileUrl = BaseConstant.IMAGE_SERVER_ADDRESS + response?.getString("hash")

                GlideUtils.loadUrlImage(this@UserInfoActivity, mRemoteFileUrl!!, mUserIconIv)
            }

        }, null)
    }

    override fun onEditUserResult(result: UserInfo) {
        toast("修改成功")
        UserPrefsUtils.putUserInfo(result)
    }
}
