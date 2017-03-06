package com.mylvyi.baisibudejie.until;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by idea on 2016/10/8.
 */
public class ThreadUtil {

    private static ExecutorService executorService;

    public static void execute(Runnable task){
        if(executorService == null){
            executorService = Executors.newFixedThreadPool(10);
        }

        executorService.submit(task);
    }

}
