package sidev.app.course.sbytour.util

object Util {
    fun <T> List<T>.asArrayList(): ArrayList<T> =
        if(this is ArrayList) this else ArrayList(this)
}