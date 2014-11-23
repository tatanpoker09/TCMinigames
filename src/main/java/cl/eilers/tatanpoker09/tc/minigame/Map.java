package cl.eilers.tatanpoker09.tc.minigame;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.YamlConfiguration;

import cl.eilers.tatanpoker09.tc.Main;
import cl.eilers.tatanpoker09.tc.utils.general.FileUtils;
import cl.eilers.tatanpoker09.tc.utils.general.MapYMLUtils;

public class Map {
	private String name;
	private File mapDirectory;
	private String[] authors;
	private MapType type;
	private YamlConfiguration ymlConfig;
	private static ArrayList<Map> mapsList = new ArrayList<Map>();
	private World world;
	
	public Map(String name, String[] authors, MapType type, YamlConfiguration ymlConfig, File mapDirectory){
		this.setMapDirectory(mapDirectory);
		this.setName(name);
		this.setAuthors(authors);
		this.setType(type);
		this.setYmlConfig(ymlConfig);
	}
	
	private void setMapDirectory(File mapDirectory) {
		this.mapDirectory = mapDirectory;
	}
	public File getMapDirectory(){
		return mapDirectory;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String[] getAuthors() {
		return authors;
	}
	public void setAuthors(String[] authors) {
		this.authors = authors;
	}
	public MapType getType() {
		return type;
	}
	public void setType(MapType type) {
		this.type = type;
	}

	public YamlConfiguration getYmlConfig() {
		return ymlConfig;
	}

	public void setYmlConfig(YamlConfiguration ymlConfig) {
		this.ymlConfig = ymlConfig;
	}
	
	public static void registerNewMap(Map map){
		mapsList.add(map);
		System.out.println("[TCMinigames] Se ha cargado: "+map.getName());
		System.out.println(map.getYmlConfig());
	}
	
	public static int loadMaps(){
		int mapsLoaded = 0;
		for(File mapFolder : Main.getMapsdirectory().listFiles()){
			if(new File(mapFolder+"/map.yml").exists() && new File(mapFolder+"/region").exists() && new File(mapFolder+ "/level.dat").exists()){
				//Loads map info from yml file
				System.out.println(new File(mapFolder+"/map.yml").exists());
				YamlConfiguration ymlFile = YamlConfiguration.loadConfiguration(new File(mapFolder+"/map.yml"));
				String name = MapYMLUtils.loadName(ymlFile);
				String[] authors = MapYMLUtils.loadAuthors(ymlFile);
				MapType type = MapYMLUtils.loadType(ymlFile);
				registerNewMap(new Map(name, authors, type, ymlFile, mapFolder));
				mapsLoaded++;
			}
		}
		return mapsLoaded;
	}
	public static ArrayList<Map> getMaps() {
		return mapsList;
	}

	public World getWorld() {
		return world;
	}
	
	public static void loadMap(Map map){
		try {
			FileUtils.copyFolder(map.getMapDirectory(), new File(map.getName()));
			map.world = WorldCreator.name(map.getName()).createWorld();
		} catch (IOException e) {
			Main.getPlugin().getLogger().info("[TCMinigames] Error whie loading map: " + map.getName());
			e.printStackTrace();
		}
	}
	public static Map getMap(String name){
		Map returnedMap = null;
		for(Map map : mapsList){
			if(map.getName().equalsIgnoreCase(name)){
				returnedMap = map;
			}
		}
		return returnedMap;
	}
}
