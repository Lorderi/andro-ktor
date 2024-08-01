package ru.lorderi.androktor

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isClickable
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
import ru.lorderi.androktor.ui.fragment.VacancyDetailFragment

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class VacancyDetailFragmentTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun testTextFieldsVisibility() {
        launchFragmentInHiltContainer<VacancyDetailFragment>()

        onView(withId(R.id.profession)).check(matches(isDisplayed()))
        onView(withId(R.id.salary)).check(matches(isDisplayed()))
        onView(withId(R.id.company)).check(matches(isDisplayed()))
        onView(withId(R.id.level)).check(matches(isDisplayed()))
    }

    @Test
    fun testTextFieldsShowInformation() {
        launchFragmentInHiltContainer<VacancyDetailFragment>()

        onView(withId(R.id.profession)).check(matches(withText(emptyString())))
        onView(withId(R.id.salary)).check(matches(withText("null")))
        onView(withId(R.id.company)).check(matches(withText(emptyString())))
        onView(withId(R.id.level)).check(matches(withText(emptyString())))
    }

    @Test
    fun testButtonVisibilityAndClickable() {
        launchFragmentInHiltContainer<VacancyDetailFragment>()

        onView(withId(R.id.company)).check(matches(isDisplayed()))
        onView(withId(R.id.company)).check(matches(isClickable()))
    }
}
