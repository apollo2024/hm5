package org.example;

import java.util.concurrent.CountDownLatch;

public class Philosofers implements Runnable{
    CountDownLatch endMeals;
    private final Philosof philosof;
    private final Object monitor;

    public Philosofers(CountDownLatch endMeals, Philosof philosof){
        this.endMeals = endMeals;
        this.philosof = philosof;
        this.philosof.setEndMeals(endMeals);
        this.monitor = Philosofers.class;
    }
    @Override
    public void run(){
        while(!philosof.isInterrupted()){
            synchronized (monitor){
                philosof.run();
                monitor.notify();
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}

class Philosof extends Thread{
    private CountDownLatch endMeals;
    private int mealsCount = 3;
    private String name;

    public Philosof(String name){
//            this.endMeals = endMeals;
        this.name = name;
    }
    public void setEndMeals(CountDownLatch endMeals){
        this.endMeals = endMeals;
    }
    @Override
    public void run(){
        try{

            if(mealsCount>0){
                haveOneMeal();
            }else{
                endMeals.countDown();
                Thread.interrupted();
            }

        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void haveOneMeal() throws InterruptedException{
        System.out.println(name + " обедает");
        Thread.sleep(500);
        mealsCount--;

    }
    private void thinking(boolean canThinkig){
        System.out.println(name + " думает");
        while(canThinkig){

        }
    }
}
