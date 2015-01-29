package com.example;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by eric on 29/1/15.
 */
public class AsyncExample {
    public static void main(String[] args) {
        // create the task to execute
        System.out.println("Main: Run thread");
        FutureTask<Integer> task = new FutureTask<Integer>(
                new Callable<Integer>() {

                    @Override
                    public Integer call() throws Exception {
                        // indicate the beginning of the thread
                        System.out.println("Thread: Start");

                        // decide a timeout between 1 and 5s
                        int timeout = 1000 + new Random().nextInt(4000);

                        // wait the timeout
                        Thread.sleep(timeout);

                        // indicate the end of the thread
                        System.out.println("Thread: Stop after " + timeout + "ms");

                        // return the result of the background execution
                        return timeout;
                    }
                });
        new Thread(task).start();
        // here the thread is running in background

        // during this time we do something else
        System.out.println("Main: Start to work on other things...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Main: I have done plenty of stuff, but now I need the result of my function!");

        // wait for the thread to finish if necessary and retrieve the result.
        Integer result = null;
        try {
            result = task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // now we can go ahead and use the result
        System.out.println("Main: Thread has returned " + result);
        // you can also check task.isDone() before to call task.get() to know
        // if it is finished and do somethings else if it is not the case.
    }
}
