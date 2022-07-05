/*
Daniil Marozau, CS211 (Project 3 Weather_211)
202518560
05/16/2022

This application provide user with the weather and map in any city in the world in html page format
*/

package Weather;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.*;

import static Weather.Weather211.*;
import static java.lang.System.exit;

public class myWeatherApp {
    // I decided to use LinkedHashMap to store the weather data, because it gives opportunity to save the order of pairs
    static LinkedHashMap<String, String> weatherinfo = new LinkedHashMap<>();
    static Scanner input = new Scanner(System.in);
    static String cityName = "";
    static int maptype;
    static int zoomlevel = 14;

    // getting the data about the weather
    public static void getWeatherInfo(String cityName) {
        try {
            weatherinfo = getWeather(cityName);
        }
        catch (Exception e) {
            System.out.println("Sorry, something went wrong");
            exit(0);
        }
    }

    // printing the weather to console and generating the html page
    public static void Print() throws IOException {
        for (String i : weatherinfo.keySet()) {
            System.out.println(i + ": " + weatherinfo.get(i));
        }
        new Map211(weatherinfo,maptype,zoomlevel,cityName);
    }

    // getting all user's data
    public static void Input() throws IOException, ParseException {
        // we need to be sure that name of the city is correct, otherwise, we'll have no data for weather
        boolean coorectName = false;
        System.out.println("Input a city name");
        while(!coorectName) {
            cityName = input.next();
            if (CorrectName(cityName)) {
                coorectName = true;
            } else {
                System.out.println("Please type the correct city name");
            }
        }

        // in case of user's mistake, program will set default settings
        System.out.println("Select the map type: 1) roadmap 2) satellite");
        maptype = input.nextInt();
        if (maptype!=2 && maptype!=1) {
            System.out.println("Since you've entered the wrong value, type automatically changed to roadmap");
            maptype = 1;
        }
        System.out.println("Select zoom level: 0 ~ 21 (default = 14)");
        zoomlevel = input.nextInt();
        if (!(zoomlevel>=0 && zoomlevel<=21)) {
            System.out.println("Since you've entered the wrong value, type automatically changed to 14");
            zoomlevel = 14;
        }
    }

    public static void main(String[] args) throws IOException, ParseException {
        // main method is simple, just input, data collecting and output
        Input();
        getWeatherInfo(cityName);
        Print();
    }
}
