package com.example.mykotlinproject

import android.graphics.drawable.ShapeDrawable
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MainAppLayout() {
    val navControl = rememberNavController()

    Scaffold(
        topBar = { TopBar() },
        bottomBar = { NavBottomBar(navControl)}
    ) {
        NavHost(navController = navControl, startDestination = "create_list" ){
            composable(route = "create_list"){ AddListForm() }
            composable(route = "show_list"){ ToDoListLayout() }
            //composable(route = "done_list"){ CompletedList() }

        }
    }

}


@Composable
fun NavBottomBar(navController: NavHostController) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFF52D17D))
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            painter = painterResource(R.drawable.ic_home),
            contentDescription ="add_toDo",
            modifier = Modifier.clickable { navController.navigate(route="create_list") },
            tint = Color.White
        )
        Icon(
            painter = painterResource(R.drawable.ic_list),
            contentDescription ="show_toDo",
            modifier = Modifier.clickable { navController.navigate(route="show_list") },
            tint = Color.White
        )


    }
}

@Composable
fun TopBar() {
    Row (
        modifier = Modifier
            .height(90.dp)
            .fillMaxWidth()
            .background(Color(0xFFFF52D17D))
            .padding(20.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically){
        Text(
            text = "ToDoList",
            fontStyle = FontStyle.Italic,
            fontFamily = FontFamily.Cursive,
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
fun DoneButton(info: MutableState<String>) {
    OutlinedButton(
        modifier = Modifier.size(60.dp, 60.dp),
        onClick ={info.value="X"}
    ){
        Text(text = "OK", fontWeight = FontWeight.Bold)
    }
}

@Composable
fun ToDoListLayout() {

    var info = remember {mutableStateOf(value="")}
    var toDoVM: ListViewModel = viewModel(LocalContext.current as ComponentActivity)

    Column() {
        toDoVM.toDoLists.forEach {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                DoneButton(info)
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    elevation = 10.dp
                ) {
                    Column() {
                        Text(text = "Title: ${it.title}" )
                        Text(text = "Content: ${it.content}" )
                    }
                }
            }
        }
    }
}

@Composable
fun AddListForm() {

    var toDoVM: ListViewModel = viewModel(LocalContext.current as ComponentActivity)

    var text1 = remember { mutableStateOf(value = "") }
    var text2 = remember { mutableStateOf(value = "") }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top

    ) {
        MyInputField("Title", text1 )
        MyInputField("Content", text2 )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedButton(
            onClick =
            {//"persons" defined in "PersonViewModel Class". // "Person" defined in "Person Class" with values of name and age
                toDoVM.toDoLists.add(ToDoList(text1.value, text2.value))
                text1.value = ""
                text2.value = ""
            }
        ){
            Text(text = "Add", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun MyInputField(label: String, state: MutableState<String>) {
    OutlinedTextField(
        value = state.value,
        onValueChange ={
            state.value = it
        },
        label = { Text(text = label) }
    )
}