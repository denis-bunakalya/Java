package ru.nsu.fit.g13202.bunakalya.task4.threadpool;

import java.util.*;
/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class PooledThread extends Thread {
   private List taskQueue;
   private int number;
   private ThreadPool threadPool;

   public PooledThread(String name, List taskQueue, int number, ThreadPool threadPool) {
      super(name);
      this.taskQueue = taskQueue;
      this.number = number;
      this.threadPool = threadPool;
   }

   private void performTask(ThreadPoolTask t)
   {
      t.prepare();
      try {
         t.go();
      }
      catch (InterruptedException ex) {
         t.interrupted();
      }
      t.finish();
   }

   public void run()
   {
      ThreadPoolTask toExecute = null;
      while (true)
      {
         synchronized (taskQueue)
         {
            if (taskQueue.isEmpty())
            {
               try {
                  taskQueue.wait();
               }
               catch (InterruptedException ex) {
                  System.err.println("Thread was inetrrupted:"+getName());
               }
               continue;
            }
            else
            {
               toExecute = (ThreadPoolTask)taskQueue.remove(0);
               threadPool.notifySubscriberQueueSizeChanged();
            }
         }
//         System.out.println(getName()+" got the job: "+toExecute.getName());
         performTask(toExecute);
      }
   }
   
   public int getNumber() {
	   
	   return number;
	   
   }
   
   
}