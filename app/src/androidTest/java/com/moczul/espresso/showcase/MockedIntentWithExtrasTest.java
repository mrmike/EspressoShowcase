package com.moczul.espresso.showcase;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.speech.RecognizerIntent;

import com.moczul.espresso.showcase.rule.CustomTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.moczul.espresso.showcase.matchers.CustomMatcher.hasText;

public class MockedIntentWithExtrasTest {

    @Rule
    public CustomTestRule<MainActivity> mRule = new CustomTestRule<>(MainActivity.class, false, false);

    @Before
    public void setUp() {
        Intent intent = new Intent();
        intent.putExtra(MainActivity.EXTRA_TITLE, "ADG Poznań");
        mRule.launchActivity(intent);

        Intent data = getResultIntent();
        final Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, data);
        intending(hasAction(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)).respondWith(result);
    }

    @Test
    public void testCustomTitle() {
        onView(withText("ADG Poznań")).check(matches(isDisplayed()));
    }

    private Intent getResultIntent() {
        final ArrayList<String> results = new ArrayList<>();
        results.add("SpeechRecognition result");
        Intent intent = new Intent();
        intent.putStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS, results);
        return intent;
    }

    @Test
    public void testSpeechToRecognition() throws InterruptedException {
        onView(withText(R.string.speech)).check(matches(isClickable()));
        onView(withText(R.string.speech)).perform(click());

        intended(hasAction(RecognizerIntent.ACTION_RECOGNIZE_SPEECH));
        onView(withId(R.id.text_input)).check(matches(hasText("SpeechRecognition result")));
    }
}
