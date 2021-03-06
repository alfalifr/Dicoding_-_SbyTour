package sidev.app.course.sbytour.model

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import java.io.Serializable

data class DestinationDownloadConfig(
    val lifecycleOwner: LifecycleOwner,
    val contentLength: LiveData<Int>,
    val currentProgress: LiveData<Pair<Long, Long>>,
    val dataList: LiveData<out List<Destination>>,
) {
    fun observeLength(func: (Int?) -> Unit) = contentLength.observe(lifecycleOwner, func)
    fun observeProgress(func: (current: Long?, total: Long?) -> Unit) = currentProgress.observe(lifecycleOwner) {
        func(it.first, it.second)
    }
    fun observeData(func: (List<Destination>) -> Unit) = dataList.observe(lifecycleOwner, func)
}