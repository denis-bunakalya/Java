package ru.nsu.fit.g13202.bunakalya.task4.factory;

import ru.nsu.fit.g13202.bunakalya.task4.storages.Storage;
import ru.nsu.fit.g13202.bunakalya.task4.threadpool.PooledThread;
import ru.nsu.fit.g13202.bunakalya.task4.threadpool.Task;

public class CarSellTask implements Task {

	private Storage storage;
	private View view;
	private int procent;
	private static int period = 4000;

	public CarSellTask(Storage storage, View view) {

		this.storage = storage;
		this.view = view;
		procent = 0;

	}

	public String getName() {

		return "CarSellTask";

	}

	public void performWork() throws InterruptedException {

		storage.get();

		for (int i = 0; i < 100; i++) {
			try {
				Thread.sleep(period / 100);
			} catch (InterruptedException e) {
			}

			procent++;
			view.updateDealerProcent(
					((PooledThread) Thread.currentThread()).getNumber(),
					procent);
		}

		view.updateDealerProcent(
				((PooledThread) Thread.currentThread()).getNumber(), 0);

	}

	public static int getPeriod() {

		return period;

	}
	
	public static void setPeriod(int period) {

		CarSellTask.period = period;

	}

}
