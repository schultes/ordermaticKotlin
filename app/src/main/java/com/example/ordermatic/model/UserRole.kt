package com.example.ordermatic.model

enum class UserRole(val rawValue: String) {
    ADMIN("Administrator"),
    KITCHEN("Küche"),
    BAR("Bar"),
    SERVICE("Service");

    companion object {
        operator fun invoke(rawValue: String) = values().firstOrNull { it.rawValue == rawValue }
    }

}