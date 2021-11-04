package com.madcrew.pravamobil.models.responsemodels

data class CategoryResponse(
    var categories: List<Categories>,
    var status: String
)

data class Categories(
    var id: String,
    var title: String
)