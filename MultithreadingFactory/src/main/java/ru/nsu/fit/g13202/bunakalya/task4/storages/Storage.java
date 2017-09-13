package ru.nsu.fit.g13202.bunakalya.task4.storages;

import java.util.ArrayList;
import java.util.List;

import ru.nsu.fit.g13202.bunakalya.task4.car.Car;
import ru.nsu.fit.g13202.bunakalya.task4.factory.View;

public class Storage {

	private View view;
	private static int capacity = 30;
	private List<Car> queue;
	
	private int totalProduced;
	private Object controllerPillow;

	public Storage(View view, int capacity) {

		this.view = view;		
		Storage.capacity = capacity;
		queue = new ArrayList<Car>();
		totalProduced = 0;
		controllerPillow = new Object();

	}

	public synchronized void put(Car car) {

		while (true) {
			if (queue.size() != capacity) {
				queue.add(car);
				totalProduced++;

				view.updateStorageQuantity(queue.size());
				view.updateTotalProduced(totalProduced);
				notifyAll();
				return;
			}
			try {
				wait();
			} catch (InterruptedException ex) {
			}
		}

	}

	public synchronized Car get() {

		while (true) {
			if (queue.size() != 0) {
				view.updateStorageQuantity(queue.size() - 1);

				notifyAll();
				synchronized (controllerPillow) {
					controllerPillow.notify();
				}
				return queue.remove(0);
			}
			try {
				wait();
			} catch (InterruptedException ex) {
			}
		}

	}

	public Object getControllerPillow() {

		return controllerPillow;

	}

	public static int getCapacity() {

		return capacity;

	}

	public int getActualQuantity() {

		return queue.size();

	}	

}
