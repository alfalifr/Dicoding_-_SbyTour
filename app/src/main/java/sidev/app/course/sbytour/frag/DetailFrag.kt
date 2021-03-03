package sidev.app.course.sbytour.frag

import android.content.Intent
import android.view.View
import sidev.app.course.sbytour.R
import sidev.app.course.sbytour.model.Destination
import sidev.app.course.sbytour.util.Const
import sidev.lib.android.std.tool.util.`fun`.get

class DetailFrag: StdFrag() {
    override var actBarTitle: String = "<Detail>"
        private set
    override val layoutId: Int = R.layout.page_detail

    private lateinit var destination: Destination

    override fun _initDataFromIntent(intent: Intent) {
        super._initDataFromIntent(intent)
        destination= intent[Const.DATA]!!
        actBarTitle= destination.name
    }

    override fun _initView(layoutView: View) {
        TODO("Not yet implemented")
    }
}