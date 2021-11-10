package com.madcrew.pravamobil.models.responsemodels

data class GroupsResponse(
    var groups: MutableList<FilialGroup>,
    var status: String
)

data class FilialGroup(
    var id: String,
    var start: String,
    var time: String
)
