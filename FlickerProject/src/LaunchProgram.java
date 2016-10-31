import java.io.IOException;

public class LaunchProgram {

	private static DataStorage storage;

	public static void main(String[] args) {
		try {
			storage = DataStorage.getMainDataStorage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LoginGUI start = new LoginGUI(false);

	}
}
