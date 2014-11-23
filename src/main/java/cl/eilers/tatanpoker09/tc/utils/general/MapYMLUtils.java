package cl.eilers.tatanpoker09.tc.utils.general;

import org.bukkit.configuration.file.YamlConfiguration;

import cl.eilers.tatanpoker09.tc.minigame.MapType;

public class MapYMLUtils {
	public static String loadName(YamlConfiguration yml){
		return yml.getString("name");
	}

	public static String[] loadAuthors(YamlConfiguration yml) {
		String[] authors = yml.getString("authors").split(",");
		return authors;
	}

	public static MapType loadType(YamlConfiguration yml) {
		return MapType.valueOf(yml.getString("type").toUpperCase());
	}
}
