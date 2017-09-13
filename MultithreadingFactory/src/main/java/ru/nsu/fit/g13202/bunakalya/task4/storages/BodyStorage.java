package ru.nsu.fit.g13202.bunakalya.task4.storages;

import java.util.ArrayList;
import java.util.List;

import ru.nsu.fit.g13202.bunakalya.task4.car.Body;
import ru.nsu.fit.g13202.bunakalya.task4.factory.View;

public class BodyStorage {

	private View view;
	private static int capacity = 30;
	private List<Body> queue;
	private int totalProduced;
	
	public BodyStorage(View view, int capacity) {

		this.view = view;		
		BodyStorage.capacity = capacity;
		queue = new ArrayList<Body>();
		totalProduced = 0;
		
	}
	
	public synchronized void put(Body body) {

		while (true) {
			if (queue.size() != capacity) {
				queue.add(body);
				totalProduced++;

				view.updateBodyStorageQuantity(queue.size());
				view.updateBodyTotalProduced(totalProduced);
				notifyAll();
				return;
			}
			try {
				wait();
			} catch (InterruptedException ex) {
			}
		}

	}
	
	public synchronized Body get() {

		while (true) {
			if (queue.size() != 0) {
				view.updateBodyStorageQuantity(queue.size() - 1);

				notifyAll();
				return queue.remove(0);
			}
			try {
				wait();
			} catch (InterruptedException ex) {
			}
		}

	}
	
	public static int getCapacity() {

		return capacity;

	}

	
}
