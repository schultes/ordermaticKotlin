package com.example.ordermatic.model

enum class OrderDishesStatus(val rawValue: String) {
    UNDONE("Noch offen"),
    READY("Bereit"),
    DONE("Abgeschlossen");

    companion object {
        operator fun invoke(rawValue: String) = values().firstOrNull { it.rawValue == rawValue }
    }
}