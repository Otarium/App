package nl.tiebe.otarium.logic.data.wrapper

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

interface TimetableItem : Parcelable {
    val id: Int

}