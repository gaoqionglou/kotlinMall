package com.kotlin.mall.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.kotlin.mall.R
import com.kotlin.mall.ui.fragment.HomeFragment
import com.kotlin.mall.ui.fragment.MeFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val mStack = Stack<Fragment>()
    private val mHomeFragment by lazy { HomeFragment() }
    private val mCategoryFragment by lazy { HomeFragment() }
    private val mCartFragment by lazy { HomeFragment() }
    private val mMsgFragment by lazy { HomeFragment() }
    private val mMeFragment by lazy { MeFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mBottomNavBar.checkMsgBadge(false)
        mBottomNavBar.checkCartBadge(20)
//        initView()
        initFragment()
        initBottomNavBar()
        changeFragment(0)

    }

    private fun initView() {
        val manager = supportFragmentManager.beginTransaction()
        manager.replace(mContainer.id, HomeFragment())
        manager.commit()
    }

    private fun initFragment() {
        val manager = supportFragmentManager.beginTransaction()
        manager.add(mContainer.id, mHomeFragment)
        manager.add(mContainer.id, mCategoryFragment)
        manager.add(mContainer.id, mCartFragment)
        manager.add(mContainer.id, mMsgFragment)
        manager.add(mContainer.id, mMeFragment)
        manager.commit()


        mStack.add(mHomeFragment)
        mStack.add(mCategoryFragment)
        mStack.add(mCartFragment)
        mStack.add(mMsgFragment)
        mStack.add(mMeFragment)

    }

    private fun initBottomNavBar() {
        mBottomNavBar.setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener {
            override fun onTabReselected(position: Int) {

            }

            override fun onTabUnselected(position: Int) {

            }

            override fun onTabSelected(position: Int) {
                changeFragment(position)
            }

        })
    }

    private fun changeFragment(position: Int) {
        val manager = supportFragmentManager.beginTransaction()
        for (fragment in mStack) {
            manager.hide(fragment)
        }
        manager.show(mStack[position])
        manager.commit()
    }

}
