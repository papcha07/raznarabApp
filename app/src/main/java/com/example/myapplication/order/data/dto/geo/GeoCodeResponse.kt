package com.example.myapplication.order.data.dto.geo

import com.example.myapplication.order.data.dto.Response
import com.google.gson.annotations.SerializedName

data class GeocodeResponse(
    @SerializedName("response") val response: GeoObjectCollection
) : Response()

data class GeoObjectCollection(
    @SerializedName("GeoObjectCollection") val geoObjectCollection: GeoObjectList
)

data class GeoObjectList(
    @SerializedName("featureMember") val featureMember: List<GeoObjectWrapper>
)

data class GeoObjectWrapper(
    @SerializedName("GeoObject") val geoObject: GeoObject
)

data class GeoObject(
    @SerializedName("metaDataProperty") val metaDataProperty: MetaDataProperty,
    @SerializedName("Point") val point: Point
)

data class MetaDataProperty(
    @SerializedName("GeocoderMetaData") val geocoderMetaData: GeocoderMetaData
)

data class GeocoderMetaData(
    @SerializedName("text") val text: String
)

data class Point(
    @SerializedName("pos") val pos: String // "37.617635 55.755814"
)
