package sidev.app.course.sbytour.app

import sidev.app.course.sbytour.act.SingleFragAct
import sidev.lib.android.siframe.lifecycle.app.App
import sidev.lib.android.siframe.tool.util.`fun`.setSingleFragAct

class PApp: App() {
    init {
        setSingleFragAct(SingleFragAct::class.java, false)
    }
}