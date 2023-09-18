package com.anksite.movindex


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.anksite.movindex.discover.ActivityDiscover
import com.anksite.movindex.util.ToolBatch
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.`is`
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class UserFlowTest {

/**    Before runing the test:
    # Disable animation in developer option
    # Uncoment following code in the project:
        ToolBatch.EspressoIdlingResource.increment()
        ToolBatch.EspressoIdlingResource.decrement()
 **/

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(ActivityDiscover::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(ToolBatch.EspressoIdlingResource.idlingresource)
    }

    @Test
    fun activityDiscoverTest() {
        val recyclerView = onView(
            allOf(
                withId(R.id.rvDiscover),
                childAtPosition(
                    withClassName(`is`("android.widget.LinearLayout")),
                    1
                )
            )
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        onView(withText("Overview")).check(matches(isDisplayed()))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(ToolBatch.EspressoIdlingResource.idlingresource)
    }
}
