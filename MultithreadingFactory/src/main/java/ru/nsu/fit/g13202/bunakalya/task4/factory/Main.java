package ru.nsu.fit.g13202.bunakalya.task4.factory;

import ru.nsu.fit.g13202.bunakalya.task4.storages.AccessoryStorage;
import ru.nsu.fit.g13202.bunakalya.task4.storages.BodyStorage;
import ru.nsu.fit.g13202.bunakalya.task4.storages.MotorStorage;
import ru.nsu.fit.g13202.bunakalya.task4.storages.Storage;
import ru.nsu.fit.g13202.bunakalya.task4.suppliers.AccessoryBuildTask;
import ru.nsu.fit.g13202.bunakalya.task4.suppliers.BodySupplier;
import ru.nsu.fit.g13202.bunakalya.task4.suppliers.MotorSupplier;
import ru.nsu.fit.g13202.bunakalya.task4.threadpool.ThreadPool;

public class Main {

	public static void main(String[] args) {

		int numberOfWorkers = 3;
		int numberOfDealers = 2;
		int numberOfAccessorySuppliers = 3;

		View view = new View(numberOfWorkers, numberOfDealers,
				numberOfAccessorySuppliers);

		MotorStorage motorStorage = new MotorStorage(view, 20);
		MotorSupplier motorSupplier = new MotorSupplier(motorStorage, view);

		BodyStorage bodyStorage = new BodyStorage(view, 30);
		BodySupplier bodySupplier = new BodySupplier(bodyStorage, view);

		AccessoryStorage accessoryStorage = new AccessoryStorage(view, 40);
		ThreadPool accessorySuppliersThreadPool = new ThreadPool(
				numberOfAccessorySuppliers);
		for (int i = 0; i < 1000; i++) {
			accessorySuppliersThreadPool.addTask(new AccessoryBuildTask(
					accessoryStorage, view));
		}

		Storage storage = new Storage(view, 30);
		
		ThreadPool workersThreadPool = new ThreadPool(numberOfWorkers);
		StorageController storageController = new StorageController(view,
				workersThreadPool, storage, motorStorage, bodyStorage, accessoryStorage);

		workersThreadPool.subscribe(storageController);

		motorSupplier.start();
		bodySupplier.start();
		storageController.start();

		ThreadPool dealersThreadPool = new ThreadPool(numberOfDealers);
		for (int i = 0; i < 1000; i++) {
			dealersThreadPool.addTask(new CarSellTask(storage, view));
		}

	}

}
