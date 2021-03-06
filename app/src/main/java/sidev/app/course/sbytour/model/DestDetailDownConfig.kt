package sidev.app.course.sbytour.model

import androidx.lifecycle.LiveData

data class DestDetailDownConfig(
    val contentLength: LiveData<Int>,
    val data: LiveData<DestinationDetail>,
)