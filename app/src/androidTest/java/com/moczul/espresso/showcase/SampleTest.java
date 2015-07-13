package com.moczul.espresso.showcase;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.moczul.espresso.showcase.matchers.CustomMatchers.hasVisibleCursor;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anyOf;

@RunWith(AndroidJUnit4.class)
public class SampleTest {

    @Rule
    public ActivityTestRule<MainActivity> mRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testTitle() {
        onView(withText(R.string.title)).check(matches(isDisplayed()));
    }

    @Test
    public void testCustomMatcher() {
        onView(withHint(R.string.hint)).check(matches(hasVisibleCursor()));
    }

    @Test
    public void testAllOf() {
        onView(allOf(withHint(R.string.hint), withId(R.id.text_input),
                hasVisibleCursor())).check(matches(isDisplayed()));
    }

    @Test
    public void testAnyOfFailing() {
        onView(anyOf(withHint(R.string.hint), withId(R.id.title)))
                .check(matches(isDisplayed()));
    }

}
