package com.example.ordermatic.model

enum class DishesType(val rawValue: String) {
    DRINKS("Getränke"),
    STARTERS("Vorspeisen"),
    MAIN_DISHES("Hauptspeisen"),
    DESSERTS("Desserts");

    companion object {
        operator fun invoke(rawValue: String) = values().firstOrNull { it.rawValue == rawValue }
    }
}