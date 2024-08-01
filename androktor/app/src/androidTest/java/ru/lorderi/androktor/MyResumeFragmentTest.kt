package ru.lorderi.androktor

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isClickable
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
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
import ru.lorderi.androktor.ui.fragment.MyResumeFragment

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MyResumeFragmentTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun testTextFieldsVisibility() {
        launchFragmentInHiltContainer<MyResumeFragment>()

        onView(withId(R.id.name)).check(matches(isDisplayed()))
        onView(withId(R.id.profession)).check(matches(isDisplayed()))
        onView(withId(R.id.sex)).check(matches(isDisplayed()))
        onView(withId(R.id.birthDate)).check(matches(isDisplayed()))
        onView(withId(R.id.phone)).check(matches(isDisplayed()))
        onView(withId(R.id.email)).check(matches(isDisplayed()))
        onView(withId(R.id.relocation)).check(matches(isDisplayed()))
        onView(withId(R.id.educationType)).check(matches(isDisplayed()))
//        onView(withId(R.id.educationYearStart)).check(matches(isDisplayed()))
//        onView(withId(R.id.educationYearEnd)).check(matches(isDisplayed()))
//        onView(withId(R.id.educationDescription)).check(matches(isDisplayed()))
//        onView(withId(R.id.jobExperienceDateStart)).check(matches(isDisplayed()))
//        onView(withId(R.id.jobExperienceDateEnd)).check(matches(isDisplayed()))
//        onView(withId(R.id.JobExperienceCompanyName)).check(matches(isDisplayed()))
//        onView(withId(R.id.JobExperienceDescription)).check(matches(isDisplayed()))
//        onView(withId(R.id.freeForm)).check(matches(isDisplayed()))
//        onView(withId(R.id.tags)).check(matches(isDisplayed()))
    }

    @Test
    fun testTextFieldsShowInformation() {
        launchFragmentInHiltContainer<MyResumeFragment>()

        onView(withId(R.id.name)).check(matches(withText(emptyString())))
        onView(withId(R.id.profession)).check(matches(withText(emptyString())))
        onView(withId(R.id.sex)).check(matches(withText(emptyString())))
        onView(withId(R.id.birthDate)).check(matches(withText(emptyString())))
        onView(withId(R.id.phone)).check(matches(withText(emptyString())))
        onView(withId(R.id.email)).check(matches(withText(emptyString())))
        onView(withId(R.id.relocation)).check(matches(withText(emptyString())))
        onView(withId(R.id.educationType)).check(matches(withText(emptyString())))
//        onView(withId(R.id.educationYearStart)).check(matches(not(withText(emptyString()))))
//        onView(withId(R.id.educationYearEnd)).check(matches(not(withText(emptyString()))))
//        onView(withId(R.id.educationDescription)).check(matches(not(withText(emptyString()))))
//        onView(withId(R.id.jobExperienceDateStart)).check(matches(not(withText(emptyString()))))
//        onView(withId(R.id.jobExperienceDateEnd)).check(matches(not(withText(emptyString()))))
//        onView(withId(R.id.JobExperienceCompanyName)).check(matches(not(withText(emptyString()))))
//        onView(withId(R.id.JobExperienceDescription)).check(matches(not(withText(emptyString()))))
//        onView(withId(R.id.freeForm)).check(matches(not(withText(emptyString()))))
//        onView(withId(R.id.tags)).check(matches(not(withText(emptyString()))))
    }

    @Test
    fun testActionButtonVisibilityAndClickable() {
        launchFragmentInHiltContainer<MyResumeFragment>()

        onView(withId(R.id.actionButton)).check(matches(isDisplayed()))
        onView(withId(R.id.actionButton)).check(matches(isClickable()))
    }

    @Test
    fun testActionButtonFunctionality() {
        launchFragmentInHiltContainer<MyResumeFragment>()

        onView(withId(R.id.actionButton)).perform(click())
        onView(withId(R.id.name)).check(matches(isEnabled()))
    }
}