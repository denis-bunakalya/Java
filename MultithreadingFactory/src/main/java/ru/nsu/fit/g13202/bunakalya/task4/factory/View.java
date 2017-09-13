package ru.nsu.fit.g13202.bunakalya.task4.factory;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ru.nsu.fit.g13202.bunakalya.task4.storages.AccessoryStorage;
import ru.nsu.fit.g13202.bunakalya.task4.storages.BodyStorage;
import ru.nsu.fit.g13202.bunakalya.task4.storages.MotorStorage;
import ru.nsu.fit.g13202.bunakalya.task4.storages.Storage;
import ru.nsu.fit.g13202.bunakalya.task4.suppliers.AccessoryBuildTask;
import ru.nsu.fit.g13202.bunakalya.task4.suppliers.BodySupplier;
import ru.nsu.fit.g13202.bunakalya.task4.factory.CarBuildTask;
import ru.nsu.fit.g13202.bunakalya.task4.suppliers.MotorSupplier;

public class View {

	private JFrame mainWindow;
	private JPanel suppliersPanel;
	private JPanel storagesPanel;

	private JLabel motorSupplierProcentLabel;
	private JLabel bodySupplierProcentLabel;
	private JLabel[] accessorySupplierProcentLabels;
	private JLabel[] workerProcentLabels;
	private JLabel[] dealerProcentLabels;

	private JLabel motorStorageQuantityLabel;
	private JLabel motorTotalProducedLabel;

	private JLabel bodyStorageQuantityLabel;
	private JLabel bodyTotalProducedLabel;

	private JLabel accessoryStorageQuantityLabel;
	private JLabel accessoryTotalProducedLabel;

	private JLabel storageQuantityLabel;
	private JLabel waitingTasksLabel;
	private JLabel totalProducedLabel;

	private GridBagConstraints c;
	private Hashtable<Integer, JLabel> slidersLabelTable;

	public View(int numberOfWorkers, int numberOfDealers,
			int numberOfAccessorySuppliers) {

		mainWindow = new JFrame("Factory");
		mainWindow.setLayout(new GridBagLayout());
		mainWindow.setResizable(false);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		c = new GridBagConstraints();
		c.gridwidth = GridBagConstraints.REMAINDER;

		makeSlidersLabelTable();

		suppliersPanel = new JPanel(new GridBagLayout());
		mainWindow.add(suppliersPanel);

		makeMotorSupplierPanel();
		makeBodySupplierPanel();
		makeAccessorySuppliersPanel(numberOfAccessorySuppliers);

		storagesPanel = new JPanel(new GridBagLayout());
		mainWindow.add(storagesPanel);

		makeMotorStoragePanel();
		makeBodyStoragePanel();
		makeAccessoryStoragePanel();

		makeFactoryPanel(numberOfWorkers);

		makeStoragePanel();

		makeMarketPanel(numberOfDealers);

		mainWindow.pack();
		mainWindow.setLocationRelativeTo(null);
		mainWindow.setVisible(true);

	}

	private void makeSlidersLabelTable() {

		slidersLabelTable = new Hashtable<Integer, JLabel>();
		slidersLabelTable.put(new Integer(100), new JLabel("100"));
		slidersLabelTable.put(new Integer(1000), new JLabel("1000"));
		slidersLabelTable.put(new Integer(2000), new JLabel("2000"));
		slidersLabelTable.put(new Integer(3000), new JLabel("3000"));
		slidersLabelTable.put(new Integer(4000), new JLabel("4000"));
		slidersLabelTable.put(new Integer(5000), new JLabel("5000"));

	}

	private void makeMotorSupplierPanel() {

		JPanel motorSupplierPanel = new JPanel(new GridBagLayout());

		motorSupplierPanel.add(new JLabel("Motor Supplier "), c);
		motorSupplierProcentLabel = new JLabel("0%");
		motorSupplierPanel.add(motorSupplierProcentLabel, c);

		motorSupplierPanel.add(new JLabel(" "), c);
		motorSupplierPanel.add(new JLabel("period, ms"), c);
		JSlider motorSupplierPeriodSlider = new JSlider(JSlider.HORIZONTAL,
				100, 5000, MotorSupplier.getPeriod());

		motorSupplierPeriodSlider.setLabelTable(slidersLabelTable);
		motorSupplierPeriodSlider.setPaintLabels(true);

		motorSupplierPeriodSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();

				if (!source.getValueIsAdjusting()) {
					MotorSupplier.setPeriod(source.getValue());
				}
			}
		});

		motorSupplierPanel.add(motorSupplierPeriodSlider);
		suppliersPanel.add(motorSupplierPanel, c);
		suppliersPanel.add(new JLabel(" "), c);
		suppliersPanel.add(new JLabel(" "), c);
		suppliersPanel.add(new JLabel(" "), c);

	}

	private void makeBodySupplierPanel() {

		JPanel bodySupplierPanel = new JPanel(new GridBagLayout());

		bodySupplierPanel.add(new JLabel("Body Supplier "), c);
		bodySupplierProcentLabel = new JLabel("0%");
		bodySupplierPanel.add(bodySupplierProcentLabel, c);

		bodySupplierPanel.add(new JLabel(" "), c);
		bodySupplierPanel.add(new JLabel("period, ms"), c);
		JSlider bodySupplierPeriodSlider = new JSlider(JSlider.HORIZONTAL, 100,
				5000, BodySupplier.getPeriod());

		bodySupplierPeriodSlider.setLabelTable(slidersLabelTable);
		bodySupplierPeriodSlider.setPaintLabels(true);

		bodySupplierPeriodSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();

				if (!source.getValueIsAdjusting()) {
					BodySupplier.setPeriod(source.getValue());
				}
			}
		});

		bodySupplierPanel.add(bodySupplierPeriodSlider);
		suppliersPanel.add(bodySupplierPanel, c);
		suppliersPanel.add(new JLabel(" "), c);
		suppliersPanel.add(new JLabel(" "), c);
		suppliersPanel.add(new JLabel(" "), c);

	}

	private void makeAccessorySuppliersPanel(int numberOfAccessorySuppliers) {

		JPanel accessorySuppliersPanel = new JPanel(new GridBagLayout());

		accessorySuppliersPanel.add(new JLabel("Accessory Suppliers"), c);
		accessorySuppliersPanel.add(new JLabel(" "), c);

		accessorySupplierProcentLabels = new JLabel[numberOfAccessorySuppliers];
		for (int i = 0; i < numberOfAccessorySuppliers; i++) {
			accessorySuppliersPanel.add(new JLabel("Supplier " + (i + 1)), c);
			accessorySupplierProcentLabels[i] = new JLabel("0%");
			accessorySuppliersPanel.add(accessorySupplierProcentLabels[i], c);
		}

		accessorySuppliersPanel.add(new JLabel(" "), c);
		accessorySuppliersPanel.add(new JLabel("period, ms"), c);
		JSlider accessorySupplierSlider = new JSlider(JSlider.HORIZONTAL, 100,
				5000, AccessoryBuildTask.getPeriod());

		accessorySupplierSlider.setLabelTable(slidersLabelTable);
		accessorySupplierSlider.setPaintLabels(true);

		accessorySupplierSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();

				if (!source.getValueIsAdjusting()) {
					AccessoryBuildTask.setPeriod(source.getValue());
				}
			}
		});

		accessorySuppliersPanel.add(accessorySupplierSlider);
		suppliersPanel.add(accessorySuppliersPanel);

	}

	private void makeMotorStoragePanel() {

		JPanel motorStoragePanel = new JPanel(new GridBagLayout());

		motorStoragePanel.add(new JLabel("   Motor Storage   "), c);
		motorStorageQuantityLabel = new JLabel("0/"
				+ MotorStorage.getCapacity());
		motorStoragePanel.add(motorStorageQuantityLabel, c);
		motorStoragePanel.add(new JLabel(" "), c);

		motorStoragePanel.add(new JLabel("   Total Produced   "), c);
		motorTotalProducedLabel = new JLabel("0");
		motorStoragePanel.add(motorTotalProducedLabel, c);
		motorStoragePanel.add(new JLabel(" "), c);

		storagesPanel.add(motorStoragePanel, c);
		storagesPanel.add(new JLabel(" "), c);
		storagesPanel.add(new JLabel(" "), c);
		storagesPanel.add(new JLabel(" "), c);

	}

	private void makeBodyStoragePanel() {

		JPanel bodyStoragePanel = new JPanel(new GridBagLayout());

		bodyStoragePanel.add(new JLabel("   Body Storage   "), c);
		bodyStorageQuantityLabel = new JLabel("0/" + BodyStorage.getCapacity());
		bodyStoragePanel.add(bodyStorageQuantityLabel, c);
		bodyStoragePanel.add(new JLabel(" "), c);

		bodyStoragePanel.add(new JLabel("   Total Produced   "), c);
		bodyTotalProducedLabel = new JLabel("0");
		bodyStoragePanel.add(bodyTotalProducedLabel, c);
		bodyStoragePanel.add(new JLabel(" "), c);

		storagesPanel.add(bodyStoragePanel, c);
		storagesPanel.add(new JLabel(" "), c);
		storagesPanel.add(new JLabel(" "), c);
		storagesPanel.add(new JLabel(" "), c);

	}

	private void makeAccessoryStoragePanel() {

		JPanel accessoryStoragePanel = new JPanel(new GridBagLayout());

		accessoryStoragePanel.add(new JLabel("   Accessory Storage   "), c);
		accessoryStorageQuantityLabel = new JLabel("0/"
				+ AccessoryStorage.getCapacity());
		accessoryStoragePanel.add(accessoryStorageQuantityLabel, c);
		accessoryStoragePanel.add(new JLabel(" "), c);

		accessoryStoragePanel.add(new JLabel("   Total Produced   "), c);
		accessoryTotalProducedLabel = new JLabel("0");
		accessoryStoragePanel.add(accessoryTotalProducedLabel, c);
		accessoryStoragePanel.add(new JLabel(" "), c);

		storagesPanel.add(accessoryStoragePanel);

	}

	private void makeFactoryPanel(int numberOfWorkers) {

		JPanel factoryPanel = new JPanel(new GridBagLayout());

		factoryPanel.add(new JLabel("Factory"), c);
		factoryPanel.add(new JLabel(" "), c);

		workerProcentLabels = new JLabel[numberOfWorkers];
		for (int i = 0; i < numberOfWorkers; i++) {
			factoryPanel.add(new JLabel("Worker " + (i + 1)), c);
			workerProcentLabels[i] = new JLabel("0%");
			factoryPanel.add(workerProcentLabels[i], c);
		}

		factoryPanel.add(new JLabel(" "), c);
		factoryPanel.add(new JLabel("period, ms"), c);
		JSlider workerPeriodSlider = new JSlider(JSlider.HORIZONTAL, 100, 5000,
				CarBuildTask.getPeriod());

		workerPeriodSlider.setLabelTable(slidersLabelTable);
		workerPeriodSlider.setPaintLabels(true);

		workerPeriodSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();

				if (!source.getValueIsAdjusting()) {
					CarBuildTask.setPeriod(source.getValue());
				}
			}
		});

		factoryPanel.add(workerPeriodSlider);
		mainWindow.add(factoryPanel);

	}

	private void makeStoragePanel() {

		JPanel storagePanel = new JPanel(new GridBagLayout());

		storagePanel.add(new JLabel("   Storage   "), c);
		storageQuantityLabel = new JLabel("0/" + Storage.getCapacity());
		storagePanel.add(storageQuantityLabel, c);
		storagePanel.add(new JLabel(" "), c);

		storagePanel.add(new JLabel("   Waiting Tasks   "), c);
		waitingTasksLabel = new JLabel("0");
		storagePanel.add(waitingTasksLabel, c);
		storagePanel.add(new JLabel(" "), c);

		storagePanel.add(new JLabel("   Total Produced   "), c);
		totalProducedLabel = new JLabel("0");
		storagePanel.add(totalProducedLabel, c);
		storagePanel.add(new JLabel(" "), c);

		mainWindow.add(storagePanel);

	}

	private void makeMarketPanel(int numberOfDealers) {

		JPanel marketPanel = new JPanel(new GridBagLayout());

		marketPanel.add(new JLabel("Market"), c);
		marketPanel.add(new JLabel(" "), c);

		dealerProcentLabels = new JLabel[numberOfDealers];
		for (int i = 0; i < numberOfDealers; i++) {
			marketPanel.add(new JLabel("Dealer " + (i + 1)), c);
			dealerProcentLabels[i] = new JLabel("0%");
			marketPanel.add(dealerProcentLabels[i], c);
		}

		marketPanel.add(new JLabel(" "), c);
		marketPanel.add(new JLabel("period, ms"), c);
		JSlider dealerPeriodSlider = new JSlider(JSlider.HORIZONTAL, 100, 5000,
				CarSellTask.getPeriod());

		dealerPeriodSlider.setLabelTable(slidersLabelTable);
		dealerPeriodSlider.setPaintLabels(true);

		dealerPeriodSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();

				if (!source.getValueIsAdjusting()) {
					CarSellTask.setPeriod(source.getValue());
				}
			}
		});

		marketPanel.add(dealerPeriodSlider);
		mainWindow.add(marketPanel);

	}

	public void updateMotorSupplierProcent(int procent) {

		motorSupplierProcentLabel.setText(procent + "%");

	}

	public void updateBodySupplierProcent(int procent) {

		bodySupplierProcentLabel.setText(procent + "%");

	}

	public void updateWorkerProcent(int workerNumber, int procent) {

		workerProcentLabels[workerNumber].setText(procent + "%");

	}

	public void updateAccessorySupplierProcent(int accessorySupplierNumber,
			int procent) {

		accessorySupplierProcentLabels[accessorySupplierNumber].setText(procent
				+ "%");

	}

	public void updateStorageQuantity(int quantity) {

		storageQuantityLabel.setText(quantity + "/" + Storage.getCapacity());

	}

	public void updateTotalProduced(int totalProduced) {

		totalProducedLabel.setText(String.valueOf(totalProduced));

	}

	public void updateWaitingTasks(int numberOfWaitingTasks) {

		waitingTasksLabel.setText(String.valueOf(numberOfWaitingTasks));

	}

	public void updateDealerProcent(int dealerNumber, int procent) {

		dealerProcentLabels[dealerNumber].setText(procent + "%");

	}

	public void updateMotorStorageQuantity(int quantity) {

		motorStorageQuantityLabel.setText(quantity + "/"
				+ MotorStorage.getCapacity());

	}

	public void updateMotorTotalProduced(int totalProduced) {

		motorTotalProducedLabel.setText(String.valueOf(totalProduced));

	}

	public void updateAccessoryTotalProduced(int totalProduced) {

		accessoryTotalProducedLabel.setText(String.valueOf(totalProduced));

	}

	public void updateBodyStorageQuantity(int quantity) {

		bodyStorageQuantityLabel.setText(quantity + "/"
				+ BodyStorage.getCapacity());

	}

	public void updateAccessoryStorageQuantity(int quantity) {

		accessoryStorageQuantityLabel.setText(quantity + "/"
				+ AccessoryStorage.getCapacity());

	}

	public void updateBodyTotalProduced(int totalProduced) {

		bodyTotalProducedLabel.setText(String.valueOf(totalProduced));

	}

}
