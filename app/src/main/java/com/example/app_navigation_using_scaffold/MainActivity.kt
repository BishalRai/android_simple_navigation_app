package com.example.app_navigation_using_scaffold

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.app_navigation_using_scaffold.ui.theme.App_Navigation_Using_ScaffoldTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App_Navigation_Using_ScaffoldTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavigationExampleApp()
                }
            }
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    var text by remember { mutableStateOf("") }
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "This is home Screen")
        OutlinedTextField(value = text, onValueChange = { text = it })
        Button(
            onClick = { navController.navigate("second/$text") },
            enabled = text.isNotEmpty()
        ) {
            Text("To second")
        }
    }
}

@Composable
fun NavigationExampleApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable(route = "home") {
            HomeScreen(navController)
        }
        composable(route = "second/{parameter}",
            arguments = listOf(
                navArgument("parameter") {
                    type = NavType.StringType
                }
            )) {
            SecondScreen(
                navController,
                it.arguments?.getString("parameter")
            )
        }
    }
}

@Composable
fun SecondScreen(navController: NavController, parameter: String?) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(text = "This is Second screen")
        Text(text = "Parameter from Home is $parameter")
        Button(onClick = { navController.navigateUp() }) {
            Text("Back to Home")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    App_Navigation_Using_ScaffoldTheme {
        NavigationExampleApp()
    }
}