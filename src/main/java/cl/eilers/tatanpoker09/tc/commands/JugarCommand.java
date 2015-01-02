package cl.eilers.tatanpoker09.tc.commands;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cl.eilers.tatanpoker09.tc.minigame.MatchState;
import cl.eilers.tatanpoker09.tc.minigame.Minigame;
import cl.eilers.tatanpoker09.tc.minigame.Team;
import cl.eilers.tatanpoker09.tc.minigame.cob.CircleOfBoom;
import cl.eilers.tatanpoker09.tc.minigame.cob.Survivor;

public class JugarCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if(Minigame.getCurrentMinigame()!=null){
			if(Minigame.getCurrentMinigame().getState().equals(MatchState.PREMATCH)){
				for(Team team : Minigame.getCurrentMinigame().getTeams()){
					if(!team.equals(Minigame.getObservers())){
						for(Player player : team.getPlayers()){
							for(Player playerObs : Minigame.getObservers().getPlayers())
								player.hidePlayer(playerObs);
						}
					}
				}
				switch(Minigame.getCurrentMinigame().getMap().getType()){
				case CIRCLE_OF_BOOM:
					if(Minigame.getCurrentMinigame().getPlayersPlaying()<2){
						sender.sendMessage(ChatColor.DARK_RED+"[TCMinigames]"+ChatColor.RED+" You need at least 2 players playing.");
					} else {
						CircleOfBoom minigame = (CircleOfBoom)Minigame.getCurrentMinigame();
						for(Survivor survivor : minigame.getSurvivors()){
							if(minigame.getSpawnPoints().size()>1){
								survivor.setSpawnPoint(minigame.getSpawnPoints().get(0));
								minigame.getSpawnPoints().remove(0);
							} else {
								survivor.setSpawnPoint(minigame.getCenter());
							}
							survivor.getPlayer().getInventory().clear();
							survivor.getPlayer().teleport(survivor.getSpawnPoint());
							survivor.getPlayer().setGameMode(GameMode.ADVENTURE);
							survivor.getPlayer().setHealth(minigame.getVida());
							if(minigame.isCorrer()){
								survivor.getPlayer().setFoodLevel(20);
							} else {
								survivor.getPlayer().setFoodLevel(6);
							}
						}
						Minigame.getCurrentMinigame().setState(MatchState.STARTED);
						minigame.startMatch();
					}
					break;
				case KEY_QUEST:
					break;
				case WATER_THE_MONUMENT:
					break;
				default:
					break;
				}
			} else {
				sender.sendMessage(ChatColor.DARK_RED+"[TCMinigames] "+ChatColor.RED+"No puedes jugar en este momento, recarga el mapa.");
			}
		}
		return false;
	}

}
