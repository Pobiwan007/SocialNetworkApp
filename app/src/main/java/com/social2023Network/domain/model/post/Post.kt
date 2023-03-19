package com.social2023Network.domain.model.post

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Post(
    var id: String? = null,
    val time: String? = null,
    val desc: String? = null,
    var images: List<String>? = null,
    val title: String? = null,
    val author: String? = null
) : Parcelable {

    fun validateData(): Boolean {
        return (
                this.desc?.isNotEmpty() == true ||
                        this.title?.isNotEmpty() == true ||
                        this.images?.isNotEmpty() == true
                )
    }
}
