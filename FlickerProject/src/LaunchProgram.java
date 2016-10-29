import java.io.IOException;

public class LaunchProgram {

	private static DataStorage storage;

	public static void main(String[] args) {
		try {
			storage = DataStorage.getMainDataStorage();
			LoginGUI start = new LoginGUI();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
}
