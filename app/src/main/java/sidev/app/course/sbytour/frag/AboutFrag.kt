package sidev.app.course.sbytour.frag

import android.view.View
import kotlinx.android.synthetic.main.page_about.view.*
import sidev.app.course.sbytour.R


class AboutFrag: StdFrag() {
    override val layoutId: Int = R.layout.page_about
    override val actBarTitle: String = "About"

    override fun _initView(layoutView: View) {
        layoutView.apply {
            tv_name.text= "Muhammad Aliffiro Naufal"
            tv_email.text= "fathf48@gmail.com"
            civ
        }
    }
}