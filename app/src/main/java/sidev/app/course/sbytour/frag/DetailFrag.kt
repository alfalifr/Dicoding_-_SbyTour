package sidev.app.course.sbytour.frag

import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.text.HtmlCompat
import androidx.core.view.children
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.page_detail.*
import kotlinx.android.synthetic.main.page_detail.view.*
import org.jetbrains.anko.textColorResource
import sidev.app.course.sbytour.R
import sidev.app.course.sbytour.model.Destination
import sidev.app.course.sbytour.repo.DestinationRepo
import sidev.app.course.sbytour.util.Const
import sidev.lib.android.std.tool.util.`fun`.*
import sidev.lib.android.viewrap.wrapChildWithBuffer
import sidev.lib.check.isNull
import sidev.lib.exception.IllegalArgExc

class DetailFrag: StdFrag() {
    override var actBarTitle: String = "Detail Destinasi"
        private set
    override val layoutId: Int = R.layout.page_detail

    private lateinit var destination: Destination
    private val repo= DestinationRepo(this)
    //private lateinit var destinationDetail: DestinationDetail

    override fun _initDataFromIntent(intent: Intent) {
        super._initDataFromIntent(intent)
        destination= intent[Const.DATA]!!
        //actBarTitle= destination.name
        loge("DetailFrag._initDataFromIntent() actBarTitle= $actBarTitle")
    }

    override fun _initView(layoutView: View) {
        layoutView.apply {
            tv_name.text= destination.name
            pb.visibility= View.VISIBLE
        }

        val wrapperList= layoutView.wrapChildWithBuffer {
            it !is ViewGroup && it.id != R.id.tv_name
        }
        for(wrapper in wrapperList){
            wrapper.showBuffer(keepView = false)
        }

        repo.getDestinationDetail(destination.id).apply {
            data.observe(this@DetailFrag) { data ->
                loge("repo.getDestinationDetail() data.observe() it= $data")
                if(data != null){
                    for(wrapper in wrapperList){
                        wrapper.showBuffer(false)
                    }
/*
                    for(v in layoutView.childrenTree){
                        loge("repo.getDestinationDetail() data.observe() v= $v")
                    }
// */
                    layoutView.apply {
                        tv_kind.apply {
                            text= data.kind.also {
                                loge("DetailFrag data.kind= $it")
                            }
                            layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
                            paint.measureText(data.kind)
                            minLines = 0
                            requestLayout()
                            invalidate()
                        }

                        //tv_address.text= it.address
                        tv_postcode.text= data.postcode
                        tv_web.apply {
                            textColorResource= R.color.biru
                            text= HtmlCompat.fromHtml(
                                "<u>${data.website}</u>",
                                HtmlCompat.FROM_HTML_MODE_COMPACT
                            )
                            setOnClickListener {
                                val i = Intent(Intent.ACTION_VIEW)
                                i.data = Uri.parse(data.website)
                                startActivity(i)
                            }
                            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                            paint.measureText(data.website)
                            minLines = 0
                            requestLayout()
                            invalidate()
                        }

                        data.address?.also {
                            tv_address.text= it
                        }.isNull {
                            tv_address.visibility= View.GONE
                        }

                        data.village?.also {
                            tv_village.text= it
                        }.isNull {
                            tv_village.visibility= View.GONE
                        }

                        loge("StdFrag() it.description= ${data.description}")
                        tv_desc.apply {
                            text= HtmlCompat.fromHtml(
                                data.description, //+"\u00A0",
                                HtmlCompat.FROM_HTML_MODE_LEGACY
                            )
                            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                            paint.measureText(data.description)
                            minLines = 0
                            requestLayout()
                            invalidate()
                        }
                        Glide.with(context!!).load(data.imgUrl).into(iv_display)
                        setStar(data.rating)

                        rl_content.apply {
                            requestLayout()
                            invalidate()
                        }
                        rl_1.apply {
                            requestLayout()
                            invalidate()
                        }
                        sv.apply {
                            requestLayout()
                            invalidate()
                        }
                    }
                    pb.visibility= View.GONE
                }
            }
        }
    }

    private fun setStar(n: Int){
        if(n !in 0 .. 5) throw IllegalArgExc(
            paramExcepted = arrayOf("n"),
            detailMsg = "`n` ($n) harus di antara 0 .. 5"
        )


        val starViewItr= layoutView.ll_star.children.iterator()
        //for(v in layoutView.ll_star.childrenTree)
            //loge("layoutView.ll_star.children v= $v")

        for(i in 0 until n){
            val v= starViewItr.next().findViewByType<ImageView>()
            loge("layoutView.ll_star.children.iterator() for() i= $i v= $v")
            (v as ImageView).apply {
                colorTintRes = R.color.kuning
            }
        }
        for(i in n until 5){
            starViewItr.next().findViewByType<ImageView>()!!.apply {
                colorTintRes = R.color.abuLebihTua
            }
        }
    }
}