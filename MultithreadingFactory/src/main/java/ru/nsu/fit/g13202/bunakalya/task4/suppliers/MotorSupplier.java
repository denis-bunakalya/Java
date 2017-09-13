package ru.nsu.fit.g13202.bunakalya.task4.suppliers;

import ru.nsu.fit.g13202.bunakalya.task4.car.Motor;
import ru.nsu.fit.g13202.bunakalya.task4.factory.View;
import ru.nsu.fit.g13202.bunakalya.task4.storages.MotorStorage;

public class MotorSupplier extends Thread {

	private MotorStorage motorStorage;
	private View view;
	private int procent;
	private static int period = 200;
	
	public MotorSupplier(MotorStorage motorStorage, View view) {

		this.motorStorage = motorStorage;
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
				view.updateMotorSupplierProcent(procent);
			}			
			
			motorStorage.put(new Motor());
			procent = 0;
		}

	}
	
	public static int getPeriod() {

		return period;

	}
	
	public static void setPeriod(int period) {

		MotorSupplier.period = period;

	}
	
	
	
}
