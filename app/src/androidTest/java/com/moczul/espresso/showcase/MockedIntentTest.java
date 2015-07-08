package com.moczul.espresso.showcase;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.moczul.espresso.showcase.matchers.CustomMatcher.hasText;
import static org.hamcrest.CoreMatchers.allOf;

@RunWith(AndroidJUnit4.class)
public class MockedIntentTest {

    @Rule
    public IntentsTestRule<MainActivity> mRule = new IntentsTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        Intent data = getResultIntent();
        final Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, data);
        intending(hasAction(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)).respondWith(result);
    }

    private Intent getResultIntent() {
        final ArrayList<String> results = new ArrayList<>();
        results.add("ADG Poznań");
        Intent intent = new Intent();
        intent.putStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS, results);
        return intent;
    }

    @Test
    public void testSpeechToRecognition() throws InterruptedException {
        onView(withText(R.string.speech)).check(matches(isClickable()));
        onView(withText(R.string.speech)).perform(click());

        intended(allOf(
                hasAction(RecognizerIntent.ACTION_RECOGNIZE_SPEECH),
                hasExtraWithKey(RecognizerIntent.EXTRA_LANGUAGE_MODEL)
        ));

        onView(withId(R.id.text_input)).check(matches(hasText("ADG Poznań")));
    }
}
