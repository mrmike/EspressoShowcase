package com.moczul.espresso.showcase;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.moczul.espresso.showcase.monitor.SchedulerMonitor;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.moczul.espresso.showcase.actions.CustomActions.text;
import static com.moczul.espresso.showcase.matchers.CustomMatchers.hasText;

@RunWith(AndroidJUnit4.class)
public class SyncDoneRightTest {

    @Rule
    public ActivityTestRule<MainActivity> mRule = new ActivityTestRule<>(MainActivity.class);

    private SchedulerMonitor mMonitor;

    @Before
    public void setUp() {
        mMonitor = new SchedulerMonitor(PoolExecutor.get());
        Espresso.registerIdlingResources(mMonitor);
    }

    @Test
    public void syncTest() {
        onView(withId(R.id.text_input)).perform(text("ADG-Poznan"));
        onView(withId(R.id.action)).perform(click());

        onView(withId(R.id.output)).check(matches(hasText("ADG-Poznan")));
    }

    @After
    public void tearDown() {
        Espresso.unregisterIdlingResources(mMonitor);
    }
}
