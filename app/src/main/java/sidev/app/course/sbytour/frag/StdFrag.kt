package sidev.app.course.sbytour.frag

import android.view.View
import androidx.annotation.CallSuper
import kotlinx.android.synthetic.main.comp_act_bar.view.*
import org.jetbrains.anko.support.v4.act
import sidev.app.course.sbytour.R
import sidev.lib.android.siframe.lifecycle.fragment.ActBarFrag
import sidev.lib.android.std.tool.util.`fun`.childrenTree
import sidev.lib.android.std.tool.util.`fun`.loge

abstract class StdFrag: ActBarFrag() {
    override val actBarId: Int = R.layout.comp_act_bar
    abstract val actBarTitle: String
    open val isOptionAvailable: Boolean = false
    open val isBackAvailable: Boolean = true
    open val isLogoAvailable: Boolean = false
    override val styleId: Int = R.style.AppTheme

    @CallSuper
    override fun _initActBar(actBarView: View) {
        loge("StdFrag._initActBar() actBarView= $actBarView")
        loge("actBarView.findViewById<View>(R.id.iv_option)= ${actBarView.findViewById<View>(R.id.iv_option)}")
        for(v in actBarView.childrenTree)
            loge("v= $v")
        loge("StdFrag._initActBar() view iterate selesai ======")
        actBarView.apply {
            loge("StdFrag._initActBar() view iterate actBarTitle= $actBarTitle")
            loge("StdFrag._initActBar() view iterate iv_option= $iv_option findViewById<View>(R.id.iv_option)= ${findViewById<View>(R.id.iv_option)}")

            iv_option.visibility= if(isOptionAvailable) View.VISIBLE else View.INVISIBLE
            loge("StdFrag._initActBar() view iterate actBarTitle= $actBarTitle 2 =======")
            iv_logo.visibility= if(isLogoAvailable) View.VISIBLE else View.INVISIBLE
            iv_back.apply {
                visibility= if(isBackAvailable) View.VISIBLE else View.INVISIBLE
                setOnClickListener { act.onBackPressed() }
            }
            loge("StdFrag._initActBar() view iterate actBarTitle= $actBarTitle AKHIR =========")
            tv_title.text= actBarTitle
        }
    }
}