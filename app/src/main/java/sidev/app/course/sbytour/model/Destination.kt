package sidev.app.course.sbytour.model

import java.io.Serializable

data class Destination(
    val id: String,
    val name: String,
    val rating: Int,
    var imgUrl: String?,
): Serializable