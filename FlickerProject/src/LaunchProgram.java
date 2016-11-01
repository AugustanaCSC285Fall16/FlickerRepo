import java.io.IOException;
import java.net.MalformedURLException;

public class LaunchProgram {

	private static DataStorage storage;

	public static void main(String[] args) {
		try {
			storage = DataStorage.getMainDataStorage();
			SplashScreenGUI gui = new SplashScreenGUI();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
