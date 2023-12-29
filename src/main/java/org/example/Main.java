package org.example;

import java.util.concurrent.CountDownLatch;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch endMeals = new CountDownLatch(7);
        for (int i = 0; i < 7; i++) {
            Thread thread = new Thread(new Philosofers(endMeals, new Philosof(String.valueOf(i))));
            thread.setDaemon(true);
            thread.start();
        }
        endMeals.await();
        System.out.println("Все пообедали");
    }
}