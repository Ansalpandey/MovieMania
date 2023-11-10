package com.company.moviemania.Domian

import android.health.connect.datatypes.Metadata
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ListMovie {
    @SerializedName("data")
    @Expose
    var data: List<Datum>? = null

    @SerializedName("metadata")
    @Expose
    var metadata: Metadata? = null
}