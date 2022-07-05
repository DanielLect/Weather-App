package Weather;

import org.json.simple.*;
import org.json.simple.parser.*;

import java.io.*;
import java.net.URL;
import java.util.*;

/*
  Not to be confused with data collecting and checking the city's name,
  I decided to divide these processes on two methods
  One will check the name and if it's correct generate JSON object
  Another will analyze this object and store data in LinkedHashMap
 */

public class Weather211 {
    // the JSON Object with weather data
    static JSONObject alltheinfo;

    // function that returns weather data of the city in LinkedHashMap format
    public static LinkedHashMap<String, String> getWeather(String cityName) {
        LinkedHashMap<String, String> data = new LinkedHashMap<>();
        data.put("City", cityName);

        // description of the weather
        JSONArray weatherdes = (JSONArray) alltheinfo.get("weather");
        JSONObject w = (JSONObject) weatherdes.get(0);

        String des = (String) w.get("description");
        String desmain = (String) w.get("main");
        data.put("Weather", desmain);
        data.put("Details", des);

        // temperature and humidity
        JSONObject main = (JSONObject) alltheinfo.get("main");
        double cityTemp = (double)main.get("temp");
        cityTemp = ((cityTemp - 273.15)*9)/5 + 32;
        data.put("Temperature", String.format("%.1f", cityTemp) + "°F");

        double cityTemp_min = (double)main.get("temp_min");
        cityTemp_min = ((cityTemp_min - 273.15)*9)/5 + 32;
        data.put("Minimal temperature", String.format("%.1f", cityTemp_min) + "°F");

        double cityTemp_max = (double)main.get("temp_max");
        cityTemp_max = ((cityTemp_max - 273.15)*9)/5 + 32;
        data.put("Maximal temperature", String.format("%.1f", cityTemp_max) + "°F");

        long humidity = (long)main.get("humidity");
        data.put("Humidity", humidity + "%");

        return data;
    }

    // function that checks city's name and if correct, creates JSON Object with all required data
    public static boolean CorrectName(String cityName) throws IOException, ParseException {
        try {
            // getting JSON data
            String key = "e5d4da4fc87a70f5de2213d2b583480d";
            String URLfull = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=" + key;
            URL url = new URL(URLfull);

            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            JSONParser parser = new JSONParser();
            alltheinfo = (JSONObject) parser.parse(br); // storing the data
        }
        catch (Exception e) { // Exception is the result of inability to access weather data -> city's name is incorrect
            return false;
        }
        return true;
    }
}
