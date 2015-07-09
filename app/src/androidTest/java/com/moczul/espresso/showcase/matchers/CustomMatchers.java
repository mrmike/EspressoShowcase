package com.moczul.espresso.showcase.matchers;

import android.support.test.espresso.matcher.BoundedMatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class CustomMatchers {

    public static Matcher<View> hasVisibleCursor() {
        return new BoundedMatcher<View, EditText>(EditText.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText(" has visible cursor.");
            }

            @Override
            protected boolean matchesSafely(EditText editText) {
                return editText.isCursorVisible();
            }
        };
    }

    public static Matcher<View> hasText(final String title) {
        return new BoundedMatcher<View, TextView>(TextView.class) {

            @Override
            public void describeTo(Description description) {
                description.appendText(" has given title: " + title);
            }

            @Override
            protected boolean matchesSafely(TextView textView) {
                return textView.getText().toString().equals(title);
            }
        };
    }
}
