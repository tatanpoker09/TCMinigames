package cl.eilers.tatanpoker09.tc.minigame;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import cl.eilers.tatanpoker09.tc.Main;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

public enum SpecialItems {
	KB_STICK(ChatColor.DARK_PURPLE+""+ChatColor.BOLD+"Knockback Wand",50, new ItemStack(Material.STICK)),
	ONE_USE_STONE_SWORD( ChatColor.DARK_PURPLE+""+ChatColor.BOLD+"Single Use Sword", 15,new ItemStack(Material.STONE_SWORD)),
	PUNCH_BOW(ChatColor.DARK_PURPLE+""+ChatColor.BOLD+"Punch Bow", 33,new ItemStack(Material.BOW), new ItemStack(Material.ARROW, 10)),
	HEALTH_BOOST(ChatColor.GREEN+"Health Boost", 0, new ItemStack(Material.EMERALD_BLOCK));

	private ArrayList<ItemStack> items = new ArrayList<ItemStack>();
	private String name;
	private int probability;

	SpecialItems(String name, int probability, ItemStack... itemstacks){
		for(ItemStack item : itemstacks){
			this.getItems().add(item);
		}
		this.probability = probability;
		this.setName(name);
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<ItemStack> getItems() {
		return items;
	}

	public void setItems(ArrayList<ItemStack> items) {
		this.items = items;
	}

	public int getProbability() {
		return probability;
	}

	public static void createItem(Location location, ArrayList<ItemStack> items, SpecialItems item) {
		ArrayList<Item> itemsDropped = new ArrayList<Item>();
		for(ItemStack itemsDropping : items){
			itemsDropped.add(Minigame.getCurrentMinigame().getWorld().dropItemNaturally(location, itemsDropping));
		}
		location.setY(location.getY()+1);
		Hologram  hologram = HologramsAPI.createHologram(Main.getPlugin(), location);
		Minigame.getCurrentMinigame().addSpecialItems(new SpecialItem(hologram, itemsDropped, item));
		System.out.println(item);
		hologram.appendTextLine(item.getName());
	}
}