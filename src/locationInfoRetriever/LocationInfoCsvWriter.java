package locationInfoRetriever;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class LocationInfoCsvWriter implements LocationInfoHandler {
	private static final String NEW_LINE_SEPARATOR = "\n";
	private static final Object[] FILE_HEADER = { "id", "name",
			"type", "latitude", "longitude" };

	@Override
	public void handleInfo(LocationInfo[] locationInfos) {
		FileWriter fileWriter = null;
		CSVPrinter csvFilePrinter = null;
		CSVFormat csvFileFormat = CSVFormat.DEFAULT
				.withRecordSeparator(NEW_LINE_SEPARATOR);

		try {
			fileWriter = new FileWriter("locations.csv");
			csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
			csvFilePrinter.printRecord(FILE_HEADER);

			for (LocationInfo locationInfo : locationInfos) {
				List<String> data = new ArrayList<String>();
				data.add(locationInfo.get_id());
				data.add(locationInfo.getName());
				data.add(locationInfo.getType());
				data.add(locationInfo.getGeo_position().getLatitude());
				data.add(locationInfo.getGeo_position().getLongitude());
				csvFilePrinter.printRecord(data);
			}
		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
				csvFilePrinter.close();
			} catch (IOException e) {
				System.out
						.println("Error while flushing/closing fileWriter/csvPrinter !!!");
				e.printStackTrace();
			}
		}
	}
}
