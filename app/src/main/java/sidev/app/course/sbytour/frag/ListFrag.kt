package sidev.app.course.sbytour.frag

import android.view.View
import kotlinx.android.synthetic.main.page_list.view.*
import sidev.app.course.sbytour.R
import sidev.app.course.sbytour.adp.DestinationAdp

class ListFrag: StdFrag() {
    override val actBarTitle: String = "Daftar Destinasi"
    override val layoutId: Int = R.layout.page_list

    private lateinit var adp: DestinationAdp

    override fun _initView(layoutView: View) {
        adp= DestinationAdp(context!!)
        adp.rv= layoutView.rv
    }
}