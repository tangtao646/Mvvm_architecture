package com.tang.baseframe.base.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import com.tang.baseframe.base.ext.dp2px
import com.tang.baseframe.base.ui.listener.TitleBar

/**
 *@Author tangtao
 *@Description:
 *@Date:2023/12/9 11:40
 */
abstract class BaseTitleBarActivity<VB : ViewDataBinding> : BaseActivity<VB>(), TitleBar {

    override fun title() = "标题"


    override fun leftAction() {
        onBackPressed()
    }

    override fun rightAction() {

    }

    override fun setRealContent() {
        fullWindowContent()
    }

    override fun titleBar(): View =
        LayoutInflater.from(this).inflate(com.tang.commonres.R.layout.layout_titlebar, null, false).apply {
            layoutParams = wrapLayoutParams(dp2px(44))
            val back = findViewById<ImageView>(com.tang.commonres.R.id.iv_back)
            val tvTitle = findViewById<TextView>(com.tang.commonres.R.id.tv_title)
            tvTitle.text = title()
            back.setOnClickListener {
                leftAction()
            }
        }

    private fun fullWindowContent() {
        val windowContent = findViewById<FrameLayout>(android.R.id.content)
        val childRoot = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = matchLayoutParams()
        }
        val contentView = binding.root.apply {
            layoutParams = matchLayoutParams()
        }
        childRoot.addView(titleBar())
        childRoot.addView(contentView)
        windowContent.removeAllViews()
        windowContent.addView(childRoot)

    }


    private fun wrapLayoutParams(height: Int): ViewGroup.LayoutParams = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT, height
    )


    private fun matchLayoutParams(): ViewGroup.LayoutParams = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
    )
}