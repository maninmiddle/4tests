package com.example.a4tests.model

data class TestModel(
    val name: String,
    val subject: String,
    val completeTime: Int,
    val endDate: String,
    val password: String
) {
    val id: Int = -1
    var isAnimated: Boolean = false
}