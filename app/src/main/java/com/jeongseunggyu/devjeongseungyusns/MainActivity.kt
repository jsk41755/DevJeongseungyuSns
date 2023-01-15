package com.jeongseunggyu.devjeongseungyusns

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jeongseunggyu.devjeongseungyusns.routes.AuthRoute
import com.jeongseunggyu.devjeongseungyusns.routes.AuthRouteAction
import com.jeongseunggyu.devjeongseungyusns.ui.screens.auth.LoginScreen
import com.jeongseunggyu.devjeongseungyusns.ui.screens.auth.RegisterScreen
import com.jeongseunggyu.devjeongseungyusns.ui.screens.auth.WelcomeScreen
import com.jeongseunggyu.devjeongseungyusns.ui.theme.DevJeongseungyuSnsTheme
import com.jeongseunggyu.devjeongseungyusns.viewmodels.AuthViewModel
import com.jeongseunggyu.devjeongseungyusns.viewmodels.HomeViewModel

class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setSoftInputMode(
            WindowManager
                .LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        )

        setContent {
            DevJeongseungyuSnsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AppScreen(authViewModel, homeViewModel)
                }
            }
        }
    }
}

@Composable
fun AppScreen(
    authViewModel: AuthViewModel,
    homeViewModel: HomeViewModel
){
    
    val authNavController = rememberNavController()

    val authRouteAction = remember(authNavController){
        AuthRouteAction(authNavController)
    }

    AuthNavHost(
        authNavController = authNavController,
        authViewModel = authViewModel,
        routeAction = authRouteAction
    )
}

//컴포저블에서 네비게이션 처리법
@Composable
fun AuthNavHost(
    authNavController: NavHostController,//네비게이션 호스트 컨트롤러 받기
    startRouter: AuthRoute = AuthRoute.WELCOME, //웰컴에서 시작
    authViewModel: AuthViewModel,
    routeAction: AuthRouteAction
) {
    NavHost(
        navController = authNavController,
        startDestination = startRouter.routeName){
        composable(AuthRoute.WELCOME.routeName){
            WelcomeScreen(routeAction)
        }
        composable(AuthRoute.LOGIN.routeName){
            LoginScreen(routeAction)
        }
        composable(AuthRoute.REGISTER.routeName){
            RegisterScreen(routeAction)
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DevJeongseungyuSnsTheme {
        Greeting("Android")
    }
}