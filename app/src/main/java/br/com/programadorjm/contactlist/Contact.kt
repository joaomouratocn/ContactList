package br.com.programadorjm.contactlist

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Contact(
    val id:Int = 0,
    val name:String,
    val phone:String
):Parcelable
