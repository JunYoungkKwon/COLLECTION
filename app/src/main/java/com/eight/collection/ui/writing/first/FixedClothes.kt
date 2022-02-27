package com.eight.collection.ui.writing.first

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class FixedClothes(
    var index: Int,
    var color: String?
) : Serializable, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(index)
        parcel.writeString(color)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FixedClothes> {
        override fun createFromParcel(parcel: Parcel): FixedClothes {
            return FixedClothes(parcel)
        }

        override fun newArray(size: Int): Array<FixedClothes?> {
            return arrayOfNulls(size)
        }
    }
}
