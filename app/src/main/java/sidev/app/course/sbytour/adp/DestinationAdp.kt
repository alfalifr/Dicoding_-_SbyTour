package sidev.app.course.sbytour.adp

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.comp_item_rv.view.*
import sidev.app.course.sbytour.R
import sidev.app.course.sbytour.frag.DetailFrag
import sidev.app.course.sbytour.model.Destination
import sidev.app.course.sbytour.util.Const
import sidev.lib.android.siframe.adapter.RvAdp
import sidev.lib.android.siframe.tool.util.`fun`.startSingleFragAct_config
import sidev.lib.android.std.tool.util.`fun`.addOnGlobalLayoutListener

class DestinationAdp(c: Context): RvAdp<Destination, GridLayoutManager>(c) {
    override val itemLayoutId: Int = R.layout.comp_item_rv

    override fun bindVH(vh: SimpleViewHolder, pos: Int, data: Destination) {
        vh.itemView.apply {
            layoutParams.height= rv!!.measuredHeight / 4
            tv.text= data.name
            Glide.with(context)
                .load(data.imgUrl)
                .into(iv)
            setOnClickListener {
                context.startSingleFragAct_config<DetailFrag>(Const.DATA to data)
            }
        }
    }

    override fun setupLayoutManager(context: Context): GridLayoutManager =
        GridLayoutManager(context, 2)
}