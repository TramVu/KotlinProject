package com.example.mykotlinproject

import androidx.lifecycle.ViewModel

class ListViewModel: ViewModel() {
    var toDoLists = mutableListOf<ToDoList>()
    var isDoneLists = mutableListOf<DoneList>()
}
