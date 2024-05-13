import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecastApp {

    private static final String API_KEY = "YOUR_API_KEY";
    private static final String CITY = "CITY_NAME";
    private static final String API_URL = "http://api.openweathermap.org/data/2.5/weather?q=" + CITY + "&appid=" + API_KEY;

    public static void main(String[] args) {
        try {
            String response = getWeatherData(API_URL);
            double temperature = extractTemperature(response);
            System.out.println("Temperature in " + CITY + ": " + temperature + " °C");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getWeatherData(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        return response.toString();
    }

    private static double extractTemperature(String json) {
        // Parse JSON to extract temperature, assuming JSON structure provided by the API
        // For simplicity, you can use a JSON parsing library like Gson or org.json
        // Here, we're just assuming a simple structure and parsing it manually
        // Example: {"main":{"temp":285.32},"..."}
        int startIndex = json.indexOf("\"temp\":") + 7;
        int endIndex = json.indexOf(",", startIndex);
        String tempStr = json.substring(startIndex, endIndex);
        return Double.parseDouble(tempStr) - 273.15; // Convert from Kelvin to Celsius
    }
}