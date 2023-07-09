package com.example.myapplication.response.home

data class Page(
    val contentItems: ContentItems,
    val pageNum: String,
    val pageSize: String,
    val title: String,
    val totalContentItems: String
)