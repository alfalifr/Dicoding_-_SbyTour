package sidev.app.course.sbytour.frag

import android.view.View
import kotlinx.android.synthetic.main.comp_act_bar.*
import sidev.app.course.sbytour.R
import sidev.lib.android.siframe.lifecycle.fragment.ActBarFrag

abstract class StdFrag: ActBarFrag() {
    override val actBarId: Int = R.layout.comp_act_bar
    abstract val actBarTitle: String
    open val isOptionAvailable: Boolean = false
    open val isBackAvailable: Boolean = true
    open val isLogoAvailable: Boolean = false
    override val styleId: Int = R.style.AppTheme

    override fun _initActBar(actBarView: View) {
        actBarView.apply {
            iv_option.visibility= if(isOptionAvailable) View.VISIBLE else View.INVISIBLE
            iv_back.visibility= if(isBackAvailable) View.VISIBLE else View.INVISIBLE
            iv_logo.visibility= if(isLogoAvailable) View.VISIBLE else View.INVISIBLE
            tv_title.text= actBarTitle
        }
    }
}