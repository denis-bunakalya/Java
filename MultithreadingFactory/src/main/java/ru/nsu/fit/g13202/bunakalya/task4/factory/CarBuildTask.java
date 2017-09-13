package ru.nsu.fit.g13202.bunakalya.task4.factory;

import ru.nsu.fit.g13202.bunakalya.task4.car.Accessory;
import ru.nsu.fit.g13202.bunakalya.task4.car.Body;
import ru.nsu.fit.g13202.bunakalya.task4.car.Car;
import ru.nsu.fit.g13202.bunakalya.task4.car.Motor;
import ru.nsu.fit.g13202.bunakalya.task4.storages.AccessoryStorage;
import ru.nsu.fit.g13202.bunakalya.task4.storages.BodyStorage;
import ru.nsu.fit.g13202.bunakalya.task4.storages.MotorStorage;
import ru.nsu.fit.g13202.bunakalya.task4.storages.Storage;
import ru.nsu.fit.g13202.bunakalya.task4.threadpool.*;

public class CarBuildTask implements Task {

	private Storage storage;
	private View view;
	private MotorStorage motorStorage;
	private BodyStorage bodyStorage;
	private AccessoryStorage accessoryStorage;
	
	private int procent;
	private static int period = 4000;

	public CarBuildTask(Storage storage, View view, MotorStorage motorStorage, BodyStorage bodyStorage, AccessoryStorage accessoryStorage) {

		this.storage = storage;
		this.view = view;
		this.motorStorage = motorStorage;
		this.bodyStorage = bodyStorage;
		this.accessoryStorage = accessoryStorage;
		
		
		procent = 0;

	}

	public String getName() {

		return "CarBuildTask";

	}

	public void performWork() throws InterruptedException {

		Motor motor = motorStorage.get();
		Body body = bodyStorage.get();
		Accessory accessory = accessoryStorage.get();

		for (int i = 0; i < 100; i++) {
			try {
				Thread.sleep(period / 100);
			} catch (InterruptedException e) {
			}

			procent++;
			view.updateWorkerProcent(
					((PooledThread) Thread.currentThread()).getNumber(),
					procent);
		}

		storage.put(new Car(motor, body, accessory));
		view.updateWorkerProcent(
				((PooledThread) Thread.currentThread()).getNumber(), 0);

	}

	public static int getPeriod() {

		return period;

	}
	
	public static void setPeriod(int period) {

		CarBuildTask.period = period;

	}

}
