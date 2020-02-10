package com.kotlin.goods.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.base.ext.startLoading
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.goods.R
import com.kotlin.goods.data.protocol.Category
import com.kotlin.goods.injection.component.DaggerCategoryComponent
import com.kotlin.goods.injection.module.CategoryModule
import com.kotlin.goods.presenter.CategoryPresenter
import com.kotlin.goods.presenter.view.CategoryView
import com.kotlin.goods.ui.adapter.SecondCategoryAdapter
import com.kotlin.goods.ui.adapter.TopCategoryAdapter
import kotlinx.android.synthetic.main.fragment_category.*

class CategoryFragment : BaseMvpFragment<CategoryPresenter>(), CategoryView {


    lateinit var mTopCategoryAdapter: TopCategoryAdapter
    lateinit var mSecondCategoryAdapter: SecondCategoryAdapter


    override fun initComponent() {
        DaggerCategoryComponent.builder().activityComponent(mActivityComponent).categoryModule(
            CategoryModule()
        ).build().inject(this)
        mPresenter.mView = this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_category, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        loadData()
    }

    /*
    加载数据
 */
    private fun loadData(parentId: Int = 0) {
        if (parentId != 0) {
            mMultiStateView.startLoading()
        }

        mPresenter.getCategory(parentId)

    }

    private fun initView() {
        mTopCategoryRv.layoutManager = LinearLayoutManager(context)
        mTopCategoryAdapter = TopCategoryAdapter(context as Context)
        mTopCategoryRv.adapter = mTopCategoryAdapter
        mTopCategoryAdapter.setOnItemClickListener(object :
            BaseRecyclerViewAdapter.OnItemClickListener<Category> {
            override fun onItemClick(item: Category, position: Int) {
                for (category in mTopCategoryAdapter.dataList) {
                    category.isSelected = item.id == category.id
                }
                mTopCategoryAdapter.notifyDataSetChanged()
                loadData(item.id)
            }

        })
        mSecondCategoryRv.layoutManager = GridLayoutManager(context, 3)
        mSecondCategoryAdapter = SecondCategoryAdapter(context as Context)
        mSecondCategoryRv.adapter = mSecondCategoryAdapter
        mSecondCategoryAdapter.setOnItemClickListener(object :
            BaseRecyclerViewAdapter.OnItemClickListener<Category> {
            override fun onItemClick(item: Category, position: Int) {
//                val intent = Intent(context,)
//                intent.
//                startActivity()
            }

        })
    }


    override fun onGetCategoryResult(result: MutableList<Category>?) {
        result?.let {
            if (it[0].parentId == 0) {
                mTopCategoryAdapter.setData(result)
                mPresenter.getCategory(it[0].id)
            } else {
                mSecondCategoryAdapter.setData(result)
            }
        }
    }


}