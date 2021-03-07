package sidev.app.course.sbytour.frag

import android.content.Intent
import android.net.Uri
import android.view.View
import kotlinx.android.synthetic.main.page_about.view.*
import sidev.app.course.sbytour.R
import sidev.app.course.sbytour.util.Const


class AboutFrag: StdFrag() {
    override val layoutId: Int = R.layout.page_about
    override val actBarTitle: String = "About"

    override fun _initView(layoutView: View) {
        layoutView.apply {
            tv_name.text= "Muhammad Aliffiro Naufal"
            tv_email.text= "fathf48@gmail.com"
            comp_by.setOnClickListener {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(Const.API_DOC_WEB)
                startActivity(i)
            }
        }
    }
}