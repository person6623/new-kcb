package com.kcb360.newkcb.net;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xinshichao on 2017/5/31.
 */

public class SingletonInstance {

    private static ExecutorService executorService;

    public static ExecutorService getInstanceCachePool() {
        return executorService != null ? executorService : Executors.newCachedThreadPool();
    }
}
