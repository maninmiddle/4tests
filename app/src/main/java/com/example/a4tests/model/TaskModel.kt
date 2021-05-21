package com.example.a4tests.model

data class TaskModel(
    var text: String,
    var variants: MutableList<String>,
    var rightVariants: MutableList<Int>
) {
    var testId: Int = -1
    var id: Int = -1
    lateinit var variantsModel: MutableList<VariantModel>
}