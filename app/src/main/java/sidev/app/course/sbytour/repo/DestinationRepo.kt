package sidev.app.course.sbytour.repo

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.toast
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import sidev.app.course.sbytour.model.DestDetailDownConfig
import sidev.app.course.sbytour.model.Destination
import sidev.app.course.sbytour.model.DestinationDetail
import sidev.app.course.sbytour.model.DestinationDownloadConfig
import sidev.app.course.sbytour.util.Const
import sidev.lib.android.std.tool.util.`fun`.loge
import sidev.lib.check.asNotNull
import sidev.lib.check.isNull
import sidev.lib.jvm.tool.`fun`.contentLengthLong_
import sidev.lib.jvm.tool.`fun`.readStreamBufferByte
import java.net.HttpURLConnection
import java.net.URL

class DestinationRepo(val frag: Fragment) {
    //private val placesList= MutableLiveData<List<Destination>>()
    //private val placesList= MutableLiveData<List<Destination>>()

    private val ctx: Context
        get()= frag.requireContext()

    /**
     * Returns pair of url contentLength and dataList.
     */
    fun getPlacesList(limit: Int = 26): DestinationDownloadConfig {
        val contentLength= MutableLiveData<Int>()
        val currentProgress= MutableLiveData<Pair<Long, Long>>()
        val dataList= MutableLiveData<ArrayList<Destination>>()

        //contentLength.observe()
        doAsync {
            val url= URL(Const.apiPlacesLimitTo(limit))
            try {
                url.openConnection().asNotNull { it: HttpURLConnection ->
                    val length= it.contentLengthLong_.toInt()
                    ctx.runOnUiThread { contentLength.value = length }

                    for(header in it.headerFields){
                        loge("getPlacesList() header= $header")
                    }

                    val byteBuffer = ByteArray(20)
                    val byteList= ArrayList<Byte>(
                        if(length > 0) length else 200
                    )

                    it.readStreamBufferByte(byteBuffer) { readByteLen, current, len ->
                        byteList += byteBuffer.asList()
                        if(len > 0){
                            ctx.runOnUiThread { currentProgress.value= current to len }
                        }
                    }
                    val byteArrComplete= byteList.toByteArray()
                    val responseStr= String(byteArrComplete, Charsets.UTF_8) //.decodeToString()
                    loge("getPlacesList() byteArrComplete.size= ${byteArrComplete.size} byteList.size= ${byteList.size} responseStr= $responseStr")
                    //loge("responseStr[8197]= ${responseStr[8197]}")
                    //loge("responseStr.substring(8190, 8198)= ${responseStr.substring(8190, 8198)}")
                    //loge("responseStr.substring(8194)= ${responseStr.substring(8194)}")
                    responseStr.toDestinationList(dataList)
                }
            } catch(e: java.net.ConnectException) {
                ctx.toast("Connection error has occured,\nkind= ${e::class}\nmsg= ${e.message}")
                loge("Connection error has occured", e)
            }
        }
        return DestinationDownloadConfig(
            frag, contentLength, currentProgress, dataList
        )
    }

    private fun String.toDestinationList(liveData: MutableLiveData<out List<Destination>>?= null): List<Destination> {
        //val json= JSONObject(this)
        val array= JSONArray(this) //json.getJSONArray("features")
        val len= array.length()
        val res= ArrayList<Destination>(len)
        liveData?.also {
            ctx.runOnUiThread { it.value= res }
        }

        for(i in 0 until len){
            val obj= array.getJSONObject(i)//.getJSONObject("properties")
            val id= obj.getString("xid")
            val name= obj.getString("name")
            val rating= obj.getInt("rate")
            res += Destination(id, name, rating, null)
            liveData?.also {
                ctx.runOnUiThread { it.value= res }
            }
        }

        return res
    }

    fun getDestinationDetail(id: String): DestDetailDownConfig {
        loge("getDestinationDetail() id= $id")
        val contentLength= MutableLiveData<Int>()
        val data= MutableLiveData<DestinationDetail>()

        doAsync {
            val urlStr= Const.API_PLACE_DETAIL.replace("{id}", id)
            val url= URL(urlStr)
            try {
                url.openConnection().asNotNull { it: HttpURLConnection ->
                    val length= it.contentLengthLong_.toInt()
                    ctx.runOnUiThread { contentLength.value = length }

                    val byteBuffer = ByteArray(20)
                    val byteList= ArrayList<Byte>(
                        if(length > 0) length else 200
                    )

                    it.readStreamBufferByte(byteBuffer) { _, _, _ ->
                        byteList += byteBuffer.asList()
                    }
                    val responseStr= byteList.toByteArray().decodeToString()
                    //loge("getDestinationDetail() urlStr= $urlStr")
                    loge("getDestinationDetail() responseStr= $responseStr")
                    ctx.runOnUiThread {
                        try {
                            data.value= responseStr.toDestinationDetail()
                        } catch (e: JSONException){
                            if(e.message?.startsWith("End of input at character", true) != true)
                                throw e
                            else {
                                loge("Error timeout", e)
                            }
                        }
                    }
                }
            } catch (e: java.net.ConnectException) {
                ctx.toast("Connection error has occured,\nkind= ${e::class}\nmsg= ${e.message}")
                loge("Connection error has occured", e)
            }
        }

        return DestDetailDownConfig(contentLength, data)
    }

    private fun String.toDestinationDetail(): DestinationDetail {
        val json= JSONObject(this)
        val id= json.getString("xid")
        val name= json.getString("name")
        val kind= json.getString("kinds")
            //.removeSuffix(",interesting_places")
            .split(",")
            .joinToString().also {
                loge("toDestinationDetail() it= $it")
            }

        val addObj= json.getJSONObject("address")
        loge("toDestinationDetail() addObj= $addObj")

        val road=
            try { addObj.getString("road") }
            catch (e: JSONException) { null }

        val district=
            try { addObj.getString("city_district") }
            catch (e: JSONException) { null }
        val address= if(road != null) "Jl. $road${ district?.let { ", $it" } ?: "" }" else district

        val village=
            try { addObj.getString("village") }
            catch (e: JSONException) { null }

        //val city= json.getString("city")
        //val province= json.getString("state")
        val postcode= addObj.getString("postcode")

        val img= json.getJSONObject("preview").getString("source")
        loge("DestDet img= $img")
        val desc= json.getJSONObject("wikipedia_extracts")
            .getString("html")
        val web= json.getString("wikipedia")
        val rating= json.getInt("rate")

        return DestinationDetail(id, name, kind, address, village, postcode, img, desc, web, rating)
    }

    fun getPlaceImg(id: String) : LiveData<String> {
        val res= MutableLiveData<String>()
        getDestinationDetail(id).data.observe(frag) {
            loge("getPlaceImg() HASIL id= $id it= $it")
            if(it != null) ctx.runOnUiThread {
                res.value= it.imgUrl
            }
        }
        return res
    }
}