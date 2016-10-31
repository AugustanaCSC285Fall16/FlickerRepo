import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.List;
import com.opencsv.CSVWriter;

public class PalladioExport implements Exporter {

	@Override
	public void export(DataStorage storage, String pathName) {
		Collection<Connection> list = storage.getConnectionList();
		CSVWriter writer;
		try {
			writer = new CSVWriter(new OutputStreamWriter(new FileOutputStream(pathName), "UTF-8"));
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
