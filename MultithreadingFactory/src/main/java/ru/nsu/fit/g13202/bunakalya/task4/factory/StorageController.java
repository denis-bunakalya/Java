package ru.nsu.fit.g13202.bunakalya.task4.factory;

import ru.nsu.fit.g13202.bunakalya.task4.storages.AccessoryStorage;
import ru.nsu.fit.g13202.bunakalya.task4.storages.BodyStorage;
import ru.nsu.fit.g13202.bunakalya.task4.storages.MotorStorage;
import ru.nsu.fit.g13202.bunakalya.task4.storages.Storage;
import ru.nsu.fit.g13202.bunakalya.task4.threadpool.InterfaceThreadPoolSubscriber;
import ru.nsu.fit.g13202.bunakalya.task4.threadpool.ThreadPool;

public class StorageController extends Thread implements
		InterfaceThreadPoolSubscriber {

	private View view;
	private ThreadPool workersThreadPool;
	
	private Storage storage;
	private MotorStorage motorStorage;
	private BodyStorage bodyStorage;
	private AccessoryStorage accessoryStorage;
	
	private Object pillow;
	private int queueSize;

	public StorageController(View view, ThreadPool workersThreadPool,
			Storage storage, MotorStorage motorStorage, BodyStorage bodyStorage, AccessoryStorage accessoryStorage) {

		this.view = view;
		this.workersThreadPool = workersThreadPool;
		this.storage = storage;
		this.motorStorage = motorStorage;
		this.bodyStorage = bodyStorage;
		this.accessoryStorage = accessoryStorage;
		
		pillow = storage.getControllerPillow();
		queueSize = 0;

	}

	public void notifyQueueSizeChanged(int newQueueSize) {

		view.updateWaitingTasks(newQueueSize);
		queueSize = newQueueSize;

	}

	public void run() {

		for (int i = 0; i < Storage.getCapacity(); i++) {
			workersThreadPool.addTask(new CarBuildTask(storage, view, motorStorage, bodyStorage, accessoryStorage));
		}

		while (true) {

			try {
				synchronized (pillow) {
					pillow.wait();
				}
			} catch (InterruptedException e) {
			}
			
			if (queueSize + storage.getActualQuantity() < (double) 0.2
					* Storage.getCapacity()) {

				for (int i = 0; i < (double) 0.8 * Storage.getCapacity(); i++) {
					workersThreadPool
							.addTask(new CarBuildTask(storage, view, motorStorage, bodyStorage, accessoryStorage));
				}
			}
		}

	}

}
