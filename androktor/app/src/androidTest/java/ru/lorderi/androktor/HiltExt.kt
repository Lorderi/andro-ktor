package ru.lorderi.androktor

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.core.app.launchActivity

inline fun <reified T : Fragment> launchFragmentInHiltContainer(
    fragmentArgs: Bundle? = null,
    themeResId: Int = R.style.Theme_Androktor,
    crossinline action: T.() -> Unit = {}
) {
    val activityScenario = launchActivity<HiltTestActivity>()
    activityScenario.onActivity { activity ->
        val fragment = activity.supportFragmentManager.fragmentFactory.instantiate(
            T::class.java.classLoader!!, T::class.java.name
        )
        fragment.arguments = fragmentArgs
        activity.supportFragmentManager.beginTransaction()
            .add(android.R.id.content, fragment, "")
            .commitNow()

        (fragment as T).action()
    }
}

inline fun <reified T : Fragment> launchFragmentWithNavInHiltContainer(
    fragmentArgs: Bundle? = null,
    navController: NavController,
    themeResId: Int = R.style.Theme_Androktor,
    crossinline action: T.() -> Unit = {}
) {
    val activityScenario = launchActivity<HiltTestActivity>()
    activityScenario.onActivity { activity ->
        val fragment = activity.supportFragmentManager.fragmentFactory.instantiate(
            T::class.java.classLoader!!, T::class.java.name
        )
        fragment.arguments = fragmentArgs
        activity.supportFragmentManager.beginTransaction()
            .add(android.R.id.content, fragment, "")
            .commitNow()
        Navigation.setViewNavController(fragment.requireView(), navController)
        (fragment as T).action()
    }
}
