package com.moczul.espresso.showcase;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PoolExecutor {

    private static final ThreadPoolExecutor INSTANCE = createInstance();

    public static ThreadPoolExecutor get() {
        return INSTANCE;
    }

    private static ThreadPoolExecutor createInstance() {
        return new ThreadPoolExecutor(4, 4, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
    }
}
