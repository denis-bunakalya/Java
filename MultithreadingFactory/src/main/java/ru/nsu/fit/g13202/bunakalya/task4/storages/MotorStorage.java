package ru.nsu.fit.g13202.bunakalya.task4.storages;

import java.util.ArrayList;
import java.util.List;

import ru.nsu.fit.g13202.bunakalya.task4.car.Motor;
import ru.nsu.fit.g13202.bunakalya.task4.factory.View;

public class MotorStorage {

	private View view;
	private static int capacity = 30;
	private List<Motor> queue;
	private int totalProduced;
	
	public MotorStorage(View view, int capacity) {

		this.view = view;
		MotorStorage.capacity = capacity;
		queue = new ArrayList<Motor>();
		totalProduced = 0;
		
	}
	
	public synchronized void put(Motor motor) {

		while (true) {
			if (queue.size() != capacity) {
				queue.add(motor);
				totalProduced++;

				view.updateMotorStorageQuantity(queue.size());
				view.updateMotorTotalProduced(totalProduced);
				notifyAll();
				return;
			}
			try {
				wait();
			} catch (InterruptedException ex) {
			}
		}

	}
	
	public synchronized Motor get() {

		while (true) {
			if (queue.size() != 0) {
				view.updateMotorStorageQuantity(queue.size() - 1);

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
