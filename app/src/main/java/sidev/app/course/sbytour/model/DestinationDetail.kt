package sidev.app.course.sbytour.model

data class DestinationDetail(
    val id: String,
    val name: String,
    val address: String,
    val county: String,
    val city: String,
    val province: String,
    val imgUrl: String
)