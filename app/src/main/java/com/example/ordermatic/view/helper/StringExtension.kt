package com.example.ordermatic.view.helper

fun String.formatDate(): String {
    val year = this.substring(0, 4)
    var month = this.substring(5, 7)
    val day = this.substring(8, 10)

    when (month) {
        "01" -> month = "Januar"
        "02" -> month = "Februar"
        "03" -> month = "März"
        "04" -> month = "April"
        "05" -> month = "Mai"
        "06" -> month = "Juni"
        "07" -> month = "Juli"
        "08" -> month = "August"
        "09" -> month = "September"
        "10" -> month = "Oktober"
        "11" -> month = "November"
        "12" -> month = "Dezember"
    }

    return "$day.$month.$year"
}