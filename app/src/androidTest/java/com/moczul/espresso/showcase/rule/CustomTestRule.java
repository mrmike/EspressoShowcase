package com.moczul.espresso.showcase.rule;

import android.app.Activity;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;

public class CustomTestRule<T extends Activity> extends ActivityTestRule{

    public CustomTestRule(Class activityClass) {
        super(activityClass);
    }

    public CustomTestRule(Class activityClass, boolean initialTouchMode) {
        super(activityClass, initialTouchMode);
    }

    public CustomTestRule(Class activityClass, boolean initialTouchMode, boolean launchActivity) {
        super(activityClass, initialTouchMode, launchActivity);
    }

    @Override
    protected void afterActivityLaunched() {
        Intents.init();
        super.afterActivityLaunched();
    }

    @Override
    protected void afterActivityFinished() {
        super.afterActivityFinished();
        Intents.release();
    }
}
