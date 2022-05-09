package com.example.bookfinderapp.ui.main

sealed class TabState() {
    object TabAll : TabState()
    object TabBookMark : TabState()
}