package cl.eilers.tatanpoker09.tc.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherListener implements Listener{
	@EventHandler
	public void onRain(WeatherChangeEvent event){
		if(event.toWeatherState()){
			event.setCancelled(true);
		}
	}
}
