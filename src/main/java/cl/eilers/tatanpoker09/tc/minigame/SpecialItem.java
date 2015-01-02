package cl.eilers.tatanpoker09.tc.minigame;

import java.util.ArrayList;

import org.bukkit.entity.Item;

import com.gmail.filoghost.holographicdisplays.api.Hologram;

public class SpecialItem {
	private Hologram hologram;
	private ArrayList<Item> items = new ArrayList<Item>();
	private SpecialItems type;

	public SpecialItem(Hologram hologram, ArrayList<Item> items, SpecialItems type){
		this.hologram = hologram;
		this.items = items;
		this.type = type;
		Minigame.getCurrentMinigame().getSpecialItems().add(this);
	}

	public Hologram getHologram() {
		return hologram;
	}
	public void setHologram(Hologram hologram) {
		this.hologram = hologram;
	}
	public SpecialItems getType() {
		return type;
	}

	public void setType(SpecialItems type) {
		this.type = type;
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}
}
