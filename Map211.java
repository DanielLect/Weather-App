package Weather;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;


public class Map211 {
    // initialization
    static String html;
    static String weather;
    static String mapFileName="myMap.html";
    static ArrayList<String> weatherInfo = new ArrayList<>();

    // Generate web page
    private static String GenerateHtml(String weather, String city, String mapType, int zoom) {
        return "<!DOCTYPE html>"
                + "<html>"
                + "<body>"
                + weather
                + "<iframe"
                + "  width=1200"
                + "  height=900"
                + "  style=border:0"
                + "  loading=lazy"
                + "  allowfullscreen"
                + "  referrerpolicy=\"no-referrer-when-downgrade\""
                + "src=\"https://www.google.com/maps/embed/v1/place?key=AIzaSyBbu352SR1kQQmv0OlOpCSbhmHzr4Tsj4M&q="+ city +"&zoom="+ zoom
                + "&maptype=" + mapType+"\""
                + "</iframe>"
                + "</body>"
                + "</html>";
    }

    // converting weather data into more "web acceptable" format
    private static String DoStringWeather(LinkedHashMap<String, String> weatherinfomap) {
        String weather = "";
        for (String i : weatherinfomap.keySet()) {
            if (i.equals("City")) {
                // this line makes city's name the title of the page
                weather = "<h2> <center>" + weather + weatherinfomap.get(i).toUpperCase() + "</h2> </center>"+ "<br>";
            }
            else {
                weather = "<h3>" + weather + i + ": " + weatherinfomap.get(i) + "</h3> <br>";
            }
        }
        return weather;
    }

    // constructor. Initializing all variables needed
    Map211(LinkedHashMap<String, String> weatherinfomap, int mapType, int zoom, String city) throws IOException {
        String WeatherNow = DoStringWeather(weatherinfomap);

        writeHTMl(WeatherNow,city,mapType,zoom);

        String url = mapFileName;
        File htmlFile = new File(url);
        Desktop.getDesktop().browse(htmlFile.toURI());
    }

    // generating html page with the data we need
    public static void writeHTMl(String weather, String city, int mapType, int zoom) {
        // converting user's input to google api acceptable variant
        String mapTypeS = "";
        if (mapType == 1) {
            mapTypeS = "roadmap";
        }
        else mapTypeS = "satellite";

        // generating the page
        html = GenerateHtml(weather, city, mapTypeS, zoom);

        // opening the page
        File f = new File(mapFileName);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write(html);
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
