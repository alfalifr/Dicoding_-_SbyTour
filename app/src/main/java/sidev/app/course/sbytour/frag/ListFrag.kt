package sidev.app.course.sbytour.frag

import android.view.View
import kotlinx.android.synthetic.main.page_list.view.*
import sidev.app.course.sbytour.R
import sidev.app.course.sbytour.adp.DestinationAdp
import sidev.app.course.sbytour.repo.DestinationRepo
import sidev.app.course.sbytour.util.Const
import sidev.app.course.sbytour.util.Util.asArrayList
import sidev.lib.check.asNotNull
import sidev.lib.jvm.tool.`fun`.contentLengthLong_
import sidev.lib.jvm.tool.`fun`.readStreamBufferByte
import sidev.lib.jvm.tool.util.FileUtil
import java.net.HttpURLConnection
import java.net.URL

class ListFrag: StdFrag() {
    override val actBarTitle: String = "Daftar Destinasi"
    override val layoutId: Int = R.layout.page_list

    private lateinit var adp: DestinationAdp
    private val repo= DestinationRepo(this)

    override fun _initView(layoutView: View) {
        adp= DestinationAdp(context!!)
        adp.rv= layoutView.rv

        repo.downloadPlacesList().apply {
            var isProgressVisible= true
            observeLength {
                if(it != null){
                    if(it > 0){
                        layoutView.pb_line.max= it
                    } else {
                        layoutView.ll_pb_line.visibility= View.GONE
                        isProgressVisible= false
                    }
                }
            }
            observeProgress { current, total ->
                if(isProgressVisible && current != null)
                    layoutView.pb_line.progress= current.toInt()
            }
            observeData {
                adp.dataList= it.asArrayList()
            }
        }
    }
}