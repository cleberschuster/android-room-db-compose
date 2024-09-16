package br.com.schuster.todolist.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import br.com.schuster.todolist.ui.feature.AddOrEditScreen
import br.com.schuster.todolist.ui.feature.ListScreen
import kotlinx.serialization.Serializable

@Serializable
object ListScreenRoute

@Serializable
data class AddOrEditScreenRoute(
    val id: Long? = null
)

@Composable
fun TaskNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ListScreenRoute
    ) {
        composable<ListScreenRoute> {
            ListScreen(
                navigateToAddOrEditScreen = { id ->
                    navController.navigate(AddOrEditScreenRoute(id = id))
                }
            )
        }

        composable<AddOrEditScreenRoute> { navBackStackEntry ->
            val addOrEditScreenRoute = navBackStackEntry.toRoute<AddOrEditScreenRoute>()
            AddOrEditScreen()
        }
    }
}