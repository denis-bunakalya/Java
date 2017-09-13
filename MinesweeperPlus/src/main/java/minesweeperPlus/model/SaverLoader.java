package minesweeperPlus.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class SaverLoader {

	public static void saveGame(String fileName, ModelState modelState, Field field) throws Exception {

		FileWriter fileWriter = new FileWriter(fileName);

		fileWriter.write("MinesweeperPlusSaving \r\n");

		fileWriter.write(modelState.getNumberOfFlags() + "\r\n");
		fileWriter.write(modelState.getGameState() + "\r\n");
		fileWriter.write(modelState.getTime() + "\r\n");

		field.save(fileWriter);

		fileWriter.close();
	}

	public static ModelState loadGame(String fileName, Field field) throws Exception {

		FileReader fileReader = new FileReader(fileName);
		BufferedReader bufferedReader = new BufferedReader(fileReader);

		int numberOfFlags;
		EnumGameState gameState;
		int time;

		try {
			if (!bufferedReader.readLine().equals("MinesweeperPlusSaving ")) {
				throw new Exception("The file format is incorrect");
			}
			numberOfFlags = Integer.parseInt(bufferedReader.readLine());
			gameState = Enum.valueOf(EnumGameState.class, bufferedReader.readLine());

			time = Integer.parseInt(bufferedReader.readLine());
			field.load(bufferedReader);

		} catch (Exception e) {
			throw e;

		} finally {
			bufferedReader.close();
			fileReader.close();
		}

		ModelState modelState = new ModelState();

		modelState.setNumberOfFlags(numberOfFlags);
		modelState.setGameState(gameState);
		modelState.setTime(time);

		return modelState;
	}

}
