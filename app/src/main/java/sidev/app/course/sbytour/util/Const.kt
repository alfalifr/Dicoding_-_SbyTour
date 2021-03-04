package sidev.app.course.sbytour.util

object Const {
    const val API_KEY = "5ae2e3f221c38a28845f05b6ab585891b95ff54f116893c3cb20d607"
    const val API_PLACES = "http://api.opentripmap.com/0.1/en/places/bbox?" +
            "lon_min=112.57&lon_max=112.86&lat_min=%2D7.42&lat_max=%2D7.12" +
            "&format=json&apikey=$API_KEY"

    fun apiPlacesLimitTo(limit: Int): String = "$API_PLACES&limit=$limit"

    const val DATA = "_data_"

    /*
    http://api.opentripmap.com/0.1/en/places/xid/Q372040?apikey=5ae2e3f221c38a28845f05b6ab585891b95ff54f116893c3cb20d607

http://api.opentripmap.com/0.1/en/places/bbox?lon_min=112.57&lon_max=112.86&lat_min=-7.42&lat_max=-7.12&kinds=churches&format=geojson&apikey=5ae2e3f221c38a28845f05b6ab585891b95ff54f116893c3cb20d607


%2D


http://api.opentripmap.com/0.1/en/places/bbox?lon_min=112.57&lon_max=112.86&lat_min=-7.42&lat_max=-7.12&format=geojson&apikey=5ae2e3f221c38a28845f05b6ab585891b95ff54f116893c3cb20d607
http://api.opentripmap.com/0.1/en/places/xid/W35603368?apikey=5ae2e3f221c38a28845f05b6ab585891b95ff54f116893c3cb20d607
http://api.opentripmap.com/0.1/en/places/xid/W35603368
     */
}