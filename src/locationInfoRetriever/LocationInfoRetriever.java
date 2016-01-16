package locationInfoRetriever;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

public class LocationInfoRetriever {
	private static final String API_BASE_URL = "http://api.goeuro.com/api/v2/position/suggest/en/";
	private LocationInfoHandler locationInfoHandler;
	
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: LocationInfoRetriever [city name]");

			return;
		}

		LocationInfoRetriever locationInfoRetriever = new LocationInfoRetriever();
		locationInfoRetriever.setLocationInfoHandler(new LocationInfoCsvWriter());
		locationInfoRetriever.retrieveLocationInfo(args[0]);
		
	}

	public void retrieveLocationInfo(String location) {
		try (CloseableHttpClient httpClient = HttpClientBuilder.create()
				.build()) {
			HttpGet request = new HttpGet(API_BASE_URL + location);
			request.addHeader("content-type", "application/json");
			HttpResponse result = httpClient.execute(request);
			String json = EntityUtils.toString(result.getEntity(), "UTF-8");
			Gson gson = new Gson();
			LocationInfo[] locationInfo = gson.fromJson(json, LocationInfo[].class);
			locationInfoHandler.handleInfo(locationInfo);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public LocationInfoHandler getLocationInfoHandler() {
		return locationInfoHandler;
	}

	public void setLocationInfoHandler(LocationInfoHandler locationInfoHandler) {
		this.locationInfoHandler = locationInfoHandler;
	}
}
