package fr.picresenar.esquisse3D.etapes;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.picresenar.esquisse3D.MainEsquisse;

public class Timer extends BukkitRunnable {

	
	public int sens=-1;
	public int timerstop=0;
	public Player player;
	private boolean firstime=true;

	private MainEsquisse main;

	public Timer(MainEsquisse mainEsquisse) {
		this.main = mainEsquisse;
	}
	
	
	@Override
	public void run() {
		
		switch(main.etape) {
		
		case LOBBY:
			break;
		case STARTING:
			if(firstime) {
				Bukkit.broadcastMessage("§2[Esquissé 3D] §aLancement de la partie dans "+(int)Math.round(main.timer)+" secondes !!");
				for(UUID player:main.getPlayers()) {
					
					if(Bukkit.getPlayer(player)==null&&Bukkit.getOfflinePlayer(player)!=null) {
						for(UUID players:main.getPlayers())if(Bukkit.getPlayer(players)!=null)Bukkit.getPlayer(players).sendMessage("§2[Info] §d"+Bukkit.getOfflinePlayer(player).getName()+" n'est pas en ligne, il sera supprimé de la liste des joueurs de l'Esquissé 3D s'il ne se connecte pas");
						System.out.print("§2[Info] §d"+ Bukkit.getOfflinePlayer(player).getName()+"§a n'est pas en ligne, il sera supprimé de la liste des joueurs de l'Esquissé 3D s'il ne se connecte pas");
					}
				}
			firstime=false;
			}else if(main.timer<=3&&main.timer>=1) {
				for(UUID players:main.getPlayers())if(Bukkit.getPlayer(players)!=null)Bukkit.getPlayer(players).sendMessage("§4"+(int)Math.round(main.timer)+"...");
			}else if(main.timer<=0) {
			  MainEtapes me=new MainEtapes(main);
			  cancel();
			  me.LancementPartie();
			}
			
			break;
			
		case CHOSING:
			
			if(main.timer<=0) {
				
				MainEtapes me=new MainEtapes(main);
			
				cancel();	
				
				main.JoueursPrêts.clear();
				me.Drawing(false,null);
				
				
			}else if(main.timer<=3&&main.timer>=1) {
				for(UUID players:main.getPlayers())if(Bukkit.getPlayer(players)!=null)Bukkit.getPlayer(players).sendMessage("§4"+(int)Math.round(main.timer));
			}
			
			
			break;
		case DRAWING:
			
			ScoreboardTimer st=new ScoreboardTimer(main);			
			
			
			st.updateTimer(main.timer);
			
			if(main.timer==Math.round(main.timermanches/2)||main.timer==Math.round(main.timermanches/4)||main.timer==60||main.timer==30) {
				for(UUID players:main.getPlayers())if(Bukkit.getPlayer(players)!=null)Bukkit.getPlayer(players).sendMessage("§2[Esquissé 3D] §aPlus que §4"+(int)Math.round(main.timer)+" §asecondes");
				for(UUID specs:main.getSpecs())if(Bukkit.getPlayer(specs)!=null)Bukkit.getPlayer(specs).sendMessage("§2[Esquissé 3D] §aPlus que §4"+(int)Math.round(main.timer)+" §asecondes");

			}else if(main.timer<=10&&main.timer>=1) {
				for(UUID players:main.getPlayers())if(Bukkit.getPlayer(players)!=null)Bukkit.getPlayer(players).sendMessage("§4"+(int)Math.round(main.timer));
				for(UUID specs:main.getSpecs())if(Bukkit.getPlayer(specs)!=null)Bukkit.getPlayer(specs).sendMessage("§4"+(int)Math.round(main.timer));
			}else if(main.timer<=0) {
			
				main.JoueursPrêts.clear();
				main.incrementRound();
				MainEtapes me=new MainEtapes(main);
				
				main.timer=10.0;
				cancel();	 
			  me.Guessing(false,null);
			  
			  st.deleteTimer();
			  
			}
					
			break;
		case GUESSING:
			
			if(main.timer<=0) {
				main.JoueursPrêts.clear();
				main.incrementRound();
				MainEtapes me=new MainEtapes(main);
			
				if(main.Round>main.Jtot-main.Jtot%2) {
					cancel();
					me.Revealing(false,null);
					
				}else {
					cancel();
					me.Drawing(false,null);
					
				}
			}else if(main.timer<=3&&main.timer>=1) {
				for(UUID players:main.getPlayers())if(Bukkit.getPlayer(players)!=null)Bukkit.getPlayer(players).sendMessage("§4"+(int)Math.round(main.timer));
				for(UUID specs:main.getSpecs())if(Bukkit.getPlayer(specs)!=null)Bukkit.getPlayer(specs).sendMessage("§4"+(int)Math.round(main.timer));

			}
			
			break;
			
		case DESTRUCTION:
			
			ScoreboardTimer st2=new ScoreboardTimer(main);
			
			st2.updateTimer(main.timer);
			
			if(main.timer<=0) {
				
				st2.deleteTimer();
				
				for(UUID players:main.getPlayers())if(Bukkit.getPlayer(players)!=null)Bukkit.getPlayer(players).sendMessage("§dC'est terminé !");
				MainEtapes me=new MainEtapes(main);
				me.ArretPartie();
				cancel();
				
				
				
			}else if(main.timer==Math.round(main.timermanches/2)||main.timer==Math.round(main.timermanches/4)||main.timer==60||main.timer==30) {
					for(UUID players:main.getPlayers())if(Bukkit.getPlayer(players)!=null)Bukkit.getPlayer(players).sendMessage("§2[Esquissé 3D] §aPlus que §4"+(int)Math.round(main.timer)+" §asecondes");
	
				

			}else if(main.timer<=10&&main.timer>=1) {
				
				for(UUID players:main.getPlayers())if(Bukkit.getPlayer(players)!=null)Bukkit.getPlayer(players).sendMessage("§4"+(int)Math.round(main.timer));
			}
			
			break;
			
			
		case ENDING:
			cancel();
			break;
		
		default:	
		cancel();
		}
			
	
		main.timer=main.timer+sens;
	}

}
