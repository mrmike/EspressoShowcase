package com.moczul.espresso.showcase.actions;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Matcher;

public class CustomActions {

    public static ViewAction text(final String text) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(TextView.class);
            }

            @Override
            public String getDescription() {
                return "Set text in TextView";
            }

            @Override
            public void perform(UiController uiController, View view) {
                ((TextView) view).setText(text);
            }
        };
    }

}
