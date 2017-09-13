package ru.nsu.fit.g13202.bunakalya.task4.storages;

import java.util.ArrayList;
import java.util.List;

import ru.nsu.fit.g13202.bunakalya.task4.car.Accessory;
import ru.nsu.fit.g13202.bunakalya.task4.factory.View;

public class AccessoryStorage {

	private View view;
	private static int capacity = 40;
	private List<Accessory> queue;
	private int totalProduced;
	
	public AccessoryStorage(View view, int capacity) {

		this.view = view;		
		AccessoryStorage.capacity = capacity;
		queue = new ArrayList<Accessory>();
		totalProduced = 0;
		
	}
	
	public synchronized void put(Accessory accessory) {

		while (true) {
			if (queue.size() != capacity) {
				queue.add(accessory);
				totalProduced++;

				view.updateAccessoryStorageQuantity(queue.size());
				view.updateAccessoryTotalProduced(totalProduced);
				notifyAll();
				return;
			}
			try {
				wait();
			} catch (InterruptedException ex) {
			}
		}

	}
	
	public synchronized Accessory get() {

		while (true) {
			if (queue.size() != 0) {
				view.updateAccessoryStorageQuantity(queue.size() - 1);

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
