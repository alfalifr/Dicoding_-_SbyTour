package sidev.app.course.sbytour.frag

import android.view.View
import kotlinx.android.synthetic.main.comp_act_bar.view.*
import kotlinx.android.synthetic.main.page_list.view.*
import sidev.app.course.sbytour.R
import sidev.app.course.sbytour.adp.DestinationAdp
import sidev.app.course.sbytour.repo.DestinationRepo
import sidev.app.course.sbytour.util.Util.asArrayList
import sidev.lib.android.siframe.tool.util.`fun`.startSingleFragAct_config
import sidev.lib.android.std.tool.util.`fun`.imgRes
import sidev.lib.android.std.tool.util.`fun`.loge

class ListFrag: StdFrag() {
    override val actBarTitle: String = "Destination List"
    override val layoutId: Int = R.layout.page_list
    override val isLogoAvailable: Boolean = true
    override val isBackAvailable: Boolean = false
    override val isOptionAvailable: Boolean = true

    private lateinit var adp: DestinationAdp
    private val repo= DestinationRepo(this)


    override fun _initActBar(actBarView: View) {
        super._initActBar(actBarView)
        actBarView.apply {
            iv_option.apply {
                imgRes = R.drawable.ic_profile
                setOnClickListener { startSingleFragAct_config<AboutFrag>() }
            }
        }
    }

    override fun _initView(layoutView: View) {
        adp= DestinationAdp(context!!, repo)
        adp.rv= layoutView.rv
        repo.getPlacesList().apply {
            var isProgressVisible= true
            observeLength {
                loge("ListFrag._initView() observeLength() it= $it")
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
                if(it.size > 0)
                    layoutView.pb_circle.visibility= View.GONE
            }
        }
    }
}