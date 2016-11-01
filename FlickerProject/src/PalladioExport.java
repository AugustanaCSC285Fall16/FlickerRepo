import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;

import javax.swing.JOptionPane;

import com.opencsv.CSVWriter;

public class PalladioExport implements Exporter {
	
	@Override
	/**
	 * Overrides the export method from Exporter. Creates a new csv based on
	 * path name and writes the data to the file.
	 */
	public void export(DataStorage storage, String pathName) {
		try {
			exportPalladio(storage, pathName);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Could not save data!");
		}
	}

	/**
	 * Creates a new csv based on the path name and writes all the connections
	 * to the csv for palladio
	 * 
	 * @param storage
	 * @param pathName
	 * @throws IOException
	 */
	public void exportPalladio(DataStorage storage, String pathName) throws IOException {
		Collection<Connection> list = storage.getConnectionList();
		CSVWriter writer;
			writer = new CSVWriter(new OutputStreamWriter(new FileOutputStream(pathName + "-palladio.csv"), "UTF-8"));
			writer.writeNext(new String[] { "Source", "Target" });
			for (Connection connection : list) {
				String direction = connection.getDirection();
				List<Person> personList = connection.getPeopleList();
				if (direction.equals("One-to-One")) {
					writer.writeNext(connection.toPalladioArray(personList.get(0), personList.get(1)));
				} else if (direction.equals("One-to-Many")) {
					for (int i = 1; i < personList.size(); i++) {
						writer.writeNext(connection.toPalladioArray(personList.get(0), personList.get(i)));
					}
				} else if (direction.equals("Many-to-Many")) {
					for (int i = 0; i < personList.size() - 1; i++) {
						for (int j = i + 1; j < personList.size(); j++) {
							writer.writeNext(connection.toPalladioArray(personList.get(i), personList.get(j)));
						}
					}
				}
			}
			writer.close();
		}
}
		
