import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.opencsv.CSVWriter;

public class GephiExport implements Exporter{

	@Override
	public void export(DataStorage storage, String pathName) {
		
		try {
			exportToGephiEdges(storage, pathName);
			exportToGephiNodes(storage, pathName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void exportToGephiNodes(DataStorage storage, String pathName) throws IOException {
		Collection<Connection> list = storage.getConnectionList();
		CSVWriter writer = new CSVWriter(new FileWriter(pathName));
		writer.writeNext(new String[] { "Node ID", "Label" });
		ArrayList<Person> personList = new ArrayList<>();
		for(Connection connection: list) {
			for (Person person : personList) {
				writer.writeNext(person.toGephiNodeArray());
			}
		}
		writer.close();
	}

	public void exportToGephiEdges(DataStorage storage, String pathName) throws IOException {
		Collection<Connection> list = storage.getConnectionList();
		CSVWriter writer = new CSVWriter(new FileWriter(pathName));
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
