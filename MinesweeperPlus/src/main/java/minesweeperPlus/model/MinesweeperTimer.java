package minesweeperPlus.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.Timer;

public class MinesweeperTimer implements ActionListener {

	private Set<InterfaceTimeDisplay> subscribers;
	private int time;
	private Timer timer;

	public MinesweeperTimer() {

		subscribers = new HashSet<InterfaceTimeDisplay>();
		time = 0;
		timer = new Timer(1000, this);
	}

	public void start() {

		timer.start();
	}

	public void actionPerformed(ActionEvent actionEvent) {

		time++;
		if (time == 999) {
			timer.stop();
		}

		for (InterfaceTimeDisplay timeDisplay : subscribers) {
			timeDisplay.notifyTimerChanged();
		}
	}

	public int getTime() {

		return time;
	}

	public void setTime(int time) {

		this.time = time;
	}

	public void resetAndStart() {

		time = 0;
		timer.start();
	}

	public void stop() {

		timer.stop();
	}

	public void subscribeTimeDisplay(InterfaceTimeDisplay timeDisplay) {

		subscribers.add(timeDisplay);
	}

	public void unsubscribeTimeDisplay(InterfaceTimeDisplay timeDisplay) {

		subscribers.remove(timeDisplay);
	}

}
