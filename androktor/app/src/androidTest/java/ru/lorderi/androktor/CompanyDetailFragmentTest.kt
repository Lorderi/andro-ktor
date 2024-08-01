package ru.lorderi.androktor

import androidx.navigation.NavController
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers.emptyString
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import ru.lorderi.androktor.adapter.vacancy.VacancyViewHolder
import ru.lorderi.androktor.ui.fragment.CompanyDetailFragment

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class CompanyDetailFragmentTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun testTextFieldsVisibility() {
        launchFragmentInHiltContainer<CompanyDetailFragment>()

        onView(withId(R.id.name)).check(matches(isDisplayed()))
        onView(withId(R.id.field_of_activity)).check(matches(isDisplayed()))
        onView(withId(R.id.contacts)).check(matches(isDisplayed()))
    }

    @Test
    fun testTextFieldsShowInformation() {
        launchFragmentInHiltContainer<CompanyDetailFragment>()

        onView(withId(R.id.name)).check(matches((withText(emptyString()))))
        onView(withId(R.id.field_of_activity)).check(matches((withText(emptyString()))))
        onView(withId(R.id.contacts)).check(matches((withText(emptyString()))))
    }

    @Test
    fun testButtonNavigation() {
        // Setup mock NavController
        val mockNavController = mock(NavController::class.java)
        mockNavController.setGraph(R.navigation.main_navigation)
        // Launch the fragment in a Hilt container
        launchFragmentWithNavInHiltContainer<CompanyDetailFragment>(navController = mockNavController) {
            // Check that the RecyclerView is displayed
            onView(withId(R.id.list)).check(matches(isDisplayed()))

            // Simulate a click on the first item in the RecyclerView
            onView(withId(R.id.list))
                .perform(
                    RecyclerViewActions.actionOnItemAtPosition<VacancyViewHolder>(
                        0,
                        click()
                    )
                )

            // Verify that the navigation to VacancyDetailFragment is triggered
            verify(mockNavController).navigate(
                eq(R.id.action_companyDetailFragment_to_vacancyDetailFragment),
                any()
            )
        }
    }
}