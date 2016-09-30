import java.io.IOException;

public class DataEdit {

	
	private static DataEdit primaryDataEdit = null;

	/**
	 * 
	 * 
	 * @return 
	 * @throws IOException
	 */
	public static DataEdit getMainDataEdit() throws IOException {
		if (primaryDataEdit == null) {
			primaryDataEdit = new DataEdit();
		}
		return primaryDataEdit;
	}

	
	
	
}
