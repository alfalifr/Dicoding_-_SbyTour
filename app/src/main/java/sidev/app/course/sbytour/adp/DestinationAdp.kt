package sidev.app.course.sbytour.adp

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.comp_item_rv.view.*
import org.jetbrains.anko.padding
import sidev.app.course.sbytour.R
import sidev.app.course.sbytour.frag.DetailFrag
import sidev.app.course.sbytour.model.Destination
import sidev.app.course.sbytour.repo.DestinationRepo
import sidev.app.course.sbytour.util.Const
import sidev.lib.android.siframe.adapter.RvAdp
import sidev.lib.android.siframe.tool.util.`fun`.startSingleFragAct_config
import sidev.lib.android.std.tool.util._ViewUtil
import sidev.lib.android.std.tool.util.`fun`.addOnGlobalLayoutListener
import sidev.lib.android.std.tool.util.`fun`.imgRes
import sidev.lib.android.std.tool.util.`fun`.loge

class DestinationAdp(c: Context, private val repo: DestinationRepo): RvAdp<Destination, GridLayoutManager>(c) {
    override val itemLayoutId: Int = R.layout.comp_item_rv

    override fun bindVH(vh: SimpleViewHolder, pos: Int, data: Destination) {
        vh.itemView.apply {
            padding = _ViewUtil.dpToPx(5.5f).toInt()
            layoutParams.height= rv!!.measuredHeight / 4 + 30
            tv.text= data.name
            iv.imgRes = R.drawable.ic_img
            if(data.imgUrl != null) {
                Glide.with(context)
                    .load(data.imgUrl)
                    .apply(RequestOptions().error(R.drawable.ic_warning))
                    .into(iv)
            } else {
                repo.getPlaceImg(data.id).observe(repo.frag) {
                    loge("DestinationAdp repo.getPlaceImg(data.id).observe(repo.frag) it= $it")
                    if(it != null){
                        Glide.with(context)
                            .load(it)
                            .apply(RequestOptions().error(R.drawable.ic_warning))
                            .into(iv)
                    }
                }
            }
            cv.setOnClickListener {
                context.startSingleFragAct_config<DetailFrag>(Const.DATA to data)
                loge("vh.itemView.setOnClickListener() pos= $pos data= $data")
            }
        }
    }

    override fun setupLayoutManager(context: Context): GridLayoutManager =
        GridLayoutManager(context, 2)
}