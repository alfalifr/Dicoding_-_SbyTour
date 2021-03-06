package sidev.app.course.sbytour.act

import android.view.View
import sidev.app.course.sbytour.R
import sidev.app.course.sbytour.frag.ListFrag
import sidev.lib.android.siframe.lifecycle.activity.Act
import sidev.lib.android.siframe.tool.util.`fun`.startSingleFragAct_config
import sidev.lib.android.std.tool.util._ThreadUtil
import sidev.lib.jvm.tool.util.ThreadUtil

class SplashAct: Act() {
    override val layoutId: Int = R.layout.page_splash

    override fun _initView(layoutView: View) {
        _ThreadUtil.delayRun(2000) {
            startSingleFragAct_config<ListFrag>()
            finish()
        }
    }
}