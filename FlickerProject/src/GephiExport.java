import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JOptionPane;

import com.opencsv.CSVWriter;

public class GephiExport implements Exporter {

	@Override

	/**
	 * Overrides the export method from exporter. Exports the gephiEdges and
	 * gephiNodes data
	 * 
	 * @param DataStorage
	 *            storage get data from
	 * @param String
	 *            path name to store as
	 */
	public void export(DataStorage storage, String pathName) {
		try {
			exportToGephiEdges(storage, pathName);
			exportToGephiNodes(storage, pathName);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Could not save data");
		}

	}

	/**
	 * Creates a new csv based on the path name and writes all the connections
	 * to the csv for the nodes
	 * 
	 * @param storage
	 *            to get data
	 * @param pathName
	 *            what to name file
	 * @throws IOException
	 */
	public void exportToGephiNodes(DataStorage storage, String pathName) throws IOException {
		Collection<Connection> list = storage.getConnectionList();
		CSVWriter writer = new CSVWriter(new OutputStreamWriter(new FileOutputStream(pathName+"-gephi-nodes.csv"), "UTF-8"));
		writer.writeNext(new String[] { "Id", "Label" });
		List<Person> personList = new ArrayList<>();
		List<Person> gephiNodeList = new ArrayList<>();
		for (Connection connection : list) {
			personList = connection.getPeopleList();
			for (Person person : personList) {
				if (!gephiNodeList.contains(person)) {
					gephiNodeList.add(person);
					writer.writeNext(person.toGephiNodeArray());
				}
			}
		}
		writer.close();
	}

	/**
	 * Creates a new csv based on the path name and writes all the connections
	 * to the csv for the edges
	 * 
	 * @param storage
	 *            to get data
	 * @param pathName
	 *            what to name file
	 * @throws IOException
	 */
	public void exportToGephiEdges(DataStorage storage, String pathName) throws IOException {
		Collection<Connection> list = storage.getConnectionList();
		CSVWriter writer = new CSVWriter(new OutputStreamWriter(new FileOutputStream(pathName+"-gephi-edges.csv"), "UTF-8"));
		writer.writeNext(new String[] { "Source", "Target", "Id", "Date", "Location", "Source type" });
		int edgeId = 1;
		for (Connection connection : list) {
			String direction = connection.getDirection();
			List<Person> personList = connection.getPeopleList();
			if (direction.equals("One-to-One")) {
				writer.writeNext(connection.toGephiEdgeArray(personList.get(0), personList.get(1), edgeId,
						connection.getDay(), connection.getMonth(), connection.getYear(), connection.getLocation(),
						connection.getTypeInteraction()));
				edgeId++;
			} else if (direction.equals("One-to-Many")) {
				for (int i = 1; i < personList.size(); i++) {
					writer.writeNext(connection.toGephiEdgeArray(personList.get(0), personList.get(i), edgeId,
							connection.getDay(), connection.getMonth(), connection.getYear(), connection.getLocation(),
							connection.getTypeInteraction()));
					edgeId++;
				}
			} else if (direction.equals("Many-to-Many")) {
				for (int i = 0; i < personList.size() - 1; i++) {
					for (int j = i + 1; j < personList.size(); j++) {
						writer.writeNext(connection.toGephiEdgeArray(personList.get(i), personList.get(j), edgeId,
								connection.getDay(), connection.getMonth(), connection.getYear(),
								connection.getLocation(), connection.getTypeInteraction()));
						edgeId++;
					}
				}
			}
		}
		writer.close();
	}

}
