package ru.nsu.fit.g13202.bunakalya.task4.suppliers;

import ru.nsu.fit.g13202.bunakalya.task4.car.Body;
import ru.nsu.fit.g13202.bunakalya.task4.factory.View;
import ru.nsu.fit.g13202.bunakalya.task4.storages.BodyStorage;

public class BodySupplier extends Thread {

	private BodyStorage bodyStorage;
	private View view;
	private int procent;
	private static int period = 200;
	
	public BodySupplier(BodyStorage bodyStorage, View view) {

		this.bodyStorage = bodyStorage;
		this.view = view;
		procent = 0;

	}
	
	public void run() {

		while (true) {
			for (int i = 0; i < 100; i++) {
				try {
					Thread.sleep(period / 100);
				} catch (InterruptedException e) {
				}
				
				procent++;
				view.updateBodySupplierProcent(procent);
			}			
			
			bodyStorage.put(new Body());
			procent = 0;
		}

	}
	
	public static int getPeriod() {

		return period;

	}
	
	public static void setPeriod(int period) {

		BodySupplier.period = period;

	}
	
	
	
}
