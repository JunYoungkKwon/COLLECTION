package com.eight.collection.ui.main.match.color

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class MatchClothes(
    var name : String?,
    var color: String?
) : Serializable, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(color)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MatchClothes> {
        override fun createFromParcel(parcel: Parcel): MatchClothes {
            return MatchClothes(parcel)
        }

        override fun newArray(size: Int): Array<MatchClothes?> {
            return arrayOfNulls(size)
        }
    }
}
