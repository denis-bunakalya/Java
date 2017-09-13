package ru.nsu.fit.g13202.bunakalya.task4.threadpool;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Title: Description: Copyright: Copyright (c) 2002 Company:
 * 
 * @author
 * @version 1.0
 */

public class ThreadPool implements TaskListener {
	private static final int THREAD_COUNT = 3;
	// private List allocatedThreads = new ArrayList();
	private List taskQueue = new LinkedList();
	private Set availableThreads = new HashSet();
	private InterfaceThreadPoolSubscriber subscriber;

	public void taskStarted(Task t) {
		// System.out.println("Started: "+t.getName());
	}

	public void taskFinished(Task t) {
		// System.out.println("Finished: "+t.getName());
	}

	public void taskInterrupted(Task t) {
		System.out.println("Interrupted: " + t.getName());
	}

	public void addTask(Task t) {
		addTask(t, this);
	}

	public void addTask(Task t, TaskListener l) {
		synchronized (taskQueue) {
			taskQueue.add(new ThreadPoolTask(t, l));
			notifySubscriberQueueSizeChanged();
			taskQueue.notify();
		}
	}

	public ThreadPool(int numberOfThreads) {
		for (int i = 0; i < numberOfThreads; i++) {
			availableThreads.add(new PooledThread("Performer_" + i, taskQueue,
					i, this));
		}
		for (Iterator iter = availableThreads.iterator(); iter.hasNext();) {
			((Thread) iter.next()).start();
		}

	}

	public void subscribe(InterfaceThreadPoolSubscriber subscriber) {

		this.subscriber = subscriber;

	}

	protected void notifySubscriberQueueSizeChanged() {

		if (subscriber != null) {
			subscriber.notifyQueueSizeChanged(taskQueue.size());
		}

	}

}