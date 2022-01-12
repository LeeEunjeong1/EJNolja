package com.example.ejnolja.model.models

data class RestByRegionResponse (
    var totalCount: Int,
    var numOfRows: Int,
    var pageNo: Int,
    var data: List<Rest>

)

data class Rest(
    var contentid: Int,
    var title: String,
    var addr1: String,
    var addr2: String,
    var firstimage: String,
    var tel: String
)