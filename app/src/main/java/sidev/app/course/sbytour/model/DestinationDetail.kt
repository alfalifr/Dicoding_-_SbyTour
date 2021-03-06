package sidev.app.course.sbytour.model

import java.io.Serializable

data class DestinationDetail(
    val id: String,
    val name: String,
    val kind: String,
    val address: String?,
    val village: String?,
    //val city: String,
    //val province: String,
    val postcode: String,
    val imgUrl: String,
    val description: String,
    val website: String,
    val rating: Int,
): Serializable