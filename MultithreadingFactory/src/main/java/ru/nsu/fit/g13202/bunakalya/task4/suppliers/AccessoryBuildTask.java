package ru.nsu.fit.g13202.bunakalya.task4.suppliers;

import ru.nsu.fit.g13202.bunakalya.task4.car.Accessory;
import ru.nsu.fit.g13202.bunakalya.task4.factory.View;
import ru.nsu.fit.g13202.bunakalya.task4.storages.AccessoryStorage;
import ru.nsu.fit.g13202.bunakalya.task4.threadpool.PooledThread;
import ru.nsu.fit.g13202.bunakalya.task4.threadpool.Task;

public class AccessoryBuildTask implements Task {

	private AccessoryStorage accessoryStorage;
	private View view;

	private int procent;
	private static int period = 3000;

	public AccessoryBuildTask(AccessoryStorage accessoryStorage, View view) {

		this.accessoryStorage = accessoryStorage;
		this.view = view;

		procent = 0;

	}

	public String getName() {

		return "AccessoryBuildTask";

	}

	public void performWork() throws InterruptedException {

		for (int i = 0; i < 100; i++) {
			try {
				Thread.sleep(period / 100);
			} catch (InterruptedException e) {
			}

			procent++;
			view.updateAccessorySupplierProcent(
					((PooledThread) Thread.currentThread()).getNumber(),
					procent);
		}

		accessoryStorage.put(new Accessory());
		view.updateAccessorySupplierProcent(
				((PooledThread) Thread.currentThread()).getNumber(), 0);

	}

	public static int getPeriod() {

		return period;

	}

	public static void setPeriod(int period) {

		AccessoryBuildTask.period = period;

	}

}
