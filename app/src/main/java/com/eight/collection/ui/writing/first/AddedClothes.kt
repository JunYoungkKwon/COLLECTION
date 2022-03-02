package com.eight.collection.ui.writing.first

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class AddedClothes(
    var bigClass: String?,
    var smallClass: String?,
    var color: String?
) : Serializable, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(bigClass)
        parcel.writeString(smallClass)
        parcel.writeString(color)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AddedClothes> {
        override fun createFromParcel(parcel: Parcel): AddedClothes {
            return AddedClothes(parcel)
        }

        override fun newArray(size: Int): Array<AddedClothes?> {
            return arrayOfNulls(size)
        }
    }
}
