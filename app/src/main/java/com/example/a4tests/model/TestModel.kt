package com.example.a4tests.model

data class TestModel(
    var name: String,
    var subject: String,
    var completeTime: Int,
    var password: String,
    var tasks: MutableList<TaskModel>
) {
    val id: Int = -1
    var isAnimated: Boolean = false
}