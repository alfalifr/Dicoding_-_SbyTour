package sidev.app.course.sbytour.repo

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.json.JSONObject
import sidev.app.course.sbytour.model.Destination
import sidev.app.course.sbytour.model.DestinationDownloadConfig
import sidev.app.course.sbytour.util.Const
import sidev.lib.check.asNotNull
import sidev.lib.jvm.tool.`fun`.contentLengthLong_
import sidev.lib.jvm.tool.`fun`.readStreamBufferByte
import java.net.HttpURLConnection
import java.net.URL

class DestinationRepo(private val frag: Fragment) {
    //private val placesList= MutableLiveData<List<Destination>>()
    //private val placesList= MutableLiveData<List<Destination>>()

    private val ctx: Context
        get()= frag.requireContext()

    /**
     * Returns pair of url contentLength and dataList.
     */
    fun downloadPlacesList(limit: Int = 50): DestinationDownloadConfig {
        val contentLength= MutableLiveData<Int>()
        val currentProgress= MutableLiveData<Pair<Long, Long>>()
        val dataList= MutableLiveData<ArrayList<Destination>>()

        //contentLength.observe()

        val url= URL(Const.apiPlacesLimitTo(limit))
        url.openConnection().asNotNull { it: HttpURLConnection ->
            val length= it.contentLengthLong_.toInt()
            contentLength.value = length

            val byteBuffer = ByteArray(20)
            val byteList= ArrayList<Byte>(
                if(length > 0) length else 200
            )

            it.readStreamBufferByte(byteBuffer) { readByteLen, current, len ->
                byteList += byteBuffer.asList()
                if(len > 0)
                    currentProgress.value= current to len
            }
            val responseStr= byteList.toByteArray().decodeToString()
            responseStr.toDestinationList(dataList)
        }
        return DestinationDownloadConfig(
            frag, contentLength, currentProgress, dataList
        )
    }

    private fun String.toDestinationList(liveData: MutableLiveData<out List<Destination>>?= null): List<Destination> {
        val json= JSONObject(this)
        val array= json.getJSONArray("features")
        val len= array.length()
        val res= ArrayList<Destination>(len)
        liveData?.value= res

        for(i in 0 until len){
            val obj= array.getJSONObject(i).getJSONObject("properties")
            val id= obj.getString("xid")
            val name= obj.getString("name")
            val rating= obj.getInt("rate")
            res += Destination(id, name, rating, null)
            liveData?.value= res
        }

        return res
    }
}