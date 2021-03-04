package sidev.app.course.sbytour.model

data class Destination(
    val id: String,
    val name: String,
    val rating: Int,
    var imgUrl: String?,
)