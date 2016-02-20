package com.home.weatherService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.gson.Gson;
/**
 * Handles requests for the application home page.
 */
@Controller
public class WeatherAPI {
	private static final Logger logger = LoggerFactory.getLogger(WeatherAPI.class);
	
	/**
	 * Weather
	 * End point for retrieving current weather conditions : "/weather?city=<someCity>"
	 */
	@RequestMapping(value = "/weather",	method = RequestMethod.GET)
	public @ResponseBody String weather(HttpServletRequest request) {
		WeatherAPI instance = new WeatherAPI();
		Gson gson = new Gson();
		ArrayList<CityWeather> mockData = new ArrayList<CityWeather>();
		String city = request.getParameter("city");
		boolean forecast = false;
		mockData = instance.createData(city, forecast);
		String result = gson.toJson(mockData) ;
			return result;
	}
	
	/**
	 * Weather forecast
	 * End point for retrieving weather forecast for 1 week : "/weather?city=<someCity>"
	 */
	@RequestMapping(value = "/weatherForecast",	method = RequestMethod.GET)
	public @ResponseBody String weatherForecast(HttpServletRequest request) {
		WeatherAPI instance = new WeatherAPI();
		Gson gson = new Gson();
		ArrayList<CityWeather> mockData = new ArrayList<CityWeather>();
		String city = request.getParameter("city");
		boolean forecast = true;
		mockData = instance.createData(city, forecast);
		String result = gson.toJson(mockData) ;
			return result;
	}
	
	//Method to create mock data
	
	public ArrayList<CityWeather> createData(String city, boolean forecast){
		
		ArrayList<String> temperature = new ArrayList<String>(Arrays.asList("97F", "100F", "96F", "103F", "99F", "104F", "100F"));
		ArrayList<String> skyCondition = new ArrayList<String>(Arrays.asList("Partly cloudy", "Clear", "Partly cloudy", "Sunny", "Clear", "Sunny", "Clear"));
		ArrayList<String> wind = new ArrayList<String>(Arrays.asList("3mph","5mph","4mph","6mph","3.5mph","5mph","6mph"));
		ArrayList<String> humidity = new ArrayList<String>(Arrays.asList("28%","32%","27%","28%","30%","31%","29%"));
		ArrayList<String> precipitation = new ArrayList<String>(Arrays.asList("0%", "1%","2%","1%","3%","7%","2%"));
		Collections.shuffle(temperature);
		Collections.shuffle(skyCondition);
		Collections.shuffle(wind);
		Collections.shuffle(humidity);
		Collections.shuffle(precipitation);
		Date today = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy");
		ArrayList<CityWeather> mockData = new ArrayList<CityWeather>();
		
		for(int i=0; i<7; i++){
			CityWeather dayWeather = new CityWeather();
			dayWeather.setCity(city);
			dayWeather.setDate(dateFormat.format(today.getTime()+ i * (1000 * 60 * 60 * 24)).toString());
			dayWeather.setTemperature(temperature.get(i));
			dayWeather.setSkyCondition(skyCondition.get(i));
			dayWeather.setWind(wind.get(i));
			dayWeather.setHumidity(humidity.get(i));
			dayWeather.setPrecipitation(precipitation.get(i));
			mockData.add(dayWeather);
			if(!forecast) break; // Create only necessary data depending on the request (only current or forecast)
		}
		return mockData;
		
	}
	
	
}
