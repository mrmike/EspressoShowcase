package com.moczul.espresso.showcase;

import rx.Scheduler;
import rx.schedulers.Schedulers;

public class RxScheduler {

    private static final Scheduler INSTANCE = createInstance();

    public static Scheduler get() {
        return INSTANCE;
    }

    private static Scheduler createInstance() {
        return Schedulers.from(PoolExecutor.get());
    }
}
