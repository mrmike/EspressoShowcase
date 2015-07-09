package com.moczul.espresso.showcase;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.moczul.espresso.showcase.matchers.CustomMatcher.hasText;

@RunWith(AndroidJUnit4.class)
public class IntentExtrasTest {

    @Rule
    public ActivityTestRule<MainActivity> mRule = new ActivityTestRule<>(MainActivity.class, false, false);

    @Before
    public void setUp() {
        Intent intent = new Intent();
        intent.putExtra(MainActivity.EXTRA_TITLE, "ADG Poznan");
        mRule.launchActivity(intent);
    }

    @Test
    public void testCustomTitle() {
        onView(withText("ADG Poznan")).check(matches(isDisplayed()));
    }

    @Test
    public void testCustomTitle2() {
        onView(withId(R.id.title)).check(matches(hasText("ADG Poznan")));
    }
}
