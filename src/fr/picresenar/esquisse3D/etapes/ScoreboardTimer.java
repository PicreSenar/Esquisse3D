package fr.picresenar.esquisse3D.etapes;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Color;
import org.bukkit.Location;

import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;

import fr.picresenar.esquisse3D.MainEsquisse;
import fr.picresenar.esquisse3D.commands.Fonctions;
import fr.picresenar.esquisse3D.commands.parameter.ListeEtapes;

public class ScoreboardTimer {
	
	private MainEsquisse main;

	public ScoreboardTimer(MainEsquisse mainEsquisse) {
		this.main = mainEsquisse;
	}
	


	
	public void updateTimer(Double Timer) {
		
		Integer timerint=Timer.intValue();
		
		ChatColor TimerColor;
		
		main.o.setDisplaySlot(DisplaySlot.SIDEBAR); //Telling the scoreboard where to display when we tell it to display
		
		for(UUID pl:main.getPlayers()) { //Getting E3D players
			Player p=Bukkit.getPlayer(pl);
			
			

		
			Score temps = main.o.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "-> Temps restant :")); //Making a offline player called "Temps_restant:" with a green name and adding it to the scoreboard
			temps.setScore(timerint); //Making it so after "Time:" it displays the int countdown(So how long it has left in seconds.)
			
				
			if (main.etape==ListeEtapes.DRAWING) {
				Score ReadyPlayers = main.o.getScore(Bukkit.getOfflinePlayer(ChatColor.GOLD + "-> Joueurs prêts :")); 
				ReadyPlayers.setScore(main.JoueursPrêts.size());
			}else if(main.o.getScore(Bukkit.getOfflinePlayer(ChatColor.GOLD + "-> Joueurs prêts :"))!=null){
				main.o.getScoreboard().resetScores(Bukkit.getOfflinePlayer(ChatColor.GOLD + "-> Joueurs prêts :"));
				
			}
			
			//if(this.timerBoard==null) return;
			p.setScoreboard(main.timerBoard); //Making it so the player can see the scoreboard
			
			

		}
	}
	
	public void deleteTimer() {

		
		for(UUID pl:main.getPlayers()) { //Getting E3D players
			Player p=Bukkit.getPlayer(pl);
			p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
			
		}
		
		
		
		
		
				
	}
		
	
	
	
	
/*	public void SpawnDragon(Double TimerMax) {
		
		for (Wither dragon:main.dragons)if(dragon!=null) dragon.remove();
		
		
		for (UUID pl:main.getPlayers()) {
			
			if(Bukkit.getPlayer(pl)!=null) {
				
				Player player = Bukkit.getPlayer(pl);
		
				Location destination = player.getLocation(); 
				destination.setY(main.SallesYmin-4);
				
				Chunk chunk=destination.getChunk();
				chunk.load();
				
		
				Wither dragon=Bukkit.getWorld(main.world).spawn(destination, Wither.class);
				
				dragon.setCustomName("Temps restant");
				 
				//Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(),String.format("entitydata %s {NoAI:1}", dragon.getUniqueId()));
				
				
				dragon.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1000000, 10));
				dragon.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1000000, 10));
				
				Vector vect= new Vector(0,0,0);
				dragon.setVelocity(vect);
				
				
				main.PasDragon= dragon.getMaxHealth()/TimerMax;
				main.dragons.add(dragon);
				
				Fonctions f=new Fonctions(main);
				f.WitherNoAI(dragon);
				

	//			System.out.println("Pas dragon :" +main.PasDragon);
			}
		}
		
		main.isSpawned=true;
		
	}
	
	
	public void UpdateHealth(Double Timer) {
		
//		System.out.println("Timer :" + Timer);
//		System.out.println("Future Vie dragon :" + (Timer*main.PasDragon));
		Integer i=0;
		Integer j=0;
		for (Wither dragon:main.dragons) {
			if(dragon!=null) {
				
				Location LinkedPlayerLocation=dragon.getLocation();
				
				j=0;
				
				for(UUID pl:main.getPlayers()) {
					
					if(i==j && Bukkit.getPlayer(pl)!=null) {
						LinkedPlayerLocation=Bukkit.getPlayer(pl).getLocation();	
						LinkedPlayerLocation.setY(main.SallesYmin-4);
						dragon.teleport(LinkedPlayerLocation);
						break;
					}
					
					j++;
				}
				
				if(Timer*main.PasDragon>=dragon.getMaxHealth()) {
					dragon.setHealth(dragon.getMaxHealth());
				}else if(Timer*main.PasDragon>0){
					dragon.setHealth(Timer*main.PasDragon);
				}else {
					System.out.println("Dragon supprimé");
					dragon.remove();
					main.isSpawned=false;
				}
//			System.out.println("Vie dragon :" +dragon.getHealth());
			}
			
			i++;
		}
	}
	*/

}
