package fr.picresenar.esquisse3D;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import fr.picresenar.esquisse3D.commands.Commands;
import fr.picresenar.esquisse3D.commands.parameter.ListeEtapes;
import fr.picresenar.esquisse3D.commands.parameter.ParametersFromConfig;

public class MainEsquisse extends JavaPlugin {

	public ListeEtapes etape;
	
	public List<UUID> players = new ArrayList<>();
	public List<UUID> nonplayers=new ArrayList<>();
	public List<UUID> specs = new ArrayList<>();
	
	public UUID Guide=null;
	
	public Integer[] RefUrnesX;	//[Joueur]
	public Integer[] RefUrnesZ;
	
	public Integer[][] RefSallesX; 	// [Proposition] [Dessin]
	public Integer[][] RefSallesZ; 	// [Proposition] [Dessin]
	
	public Entity[][] RefCochons;
	public String[] NomJoueurs;
	
	public String world ;
	public Integer Jmax;
	public Integer Jmin;
	public Integer Jtot;
	
	public boolean autoriserTNT;
	public boolean carapaceSalles;
	public Double timermanches;
	
	public Double LobbyX;
	public Double LobbyY;
	public Double LobbyZ;
	
	public Double UrnesXmin;
	public Double UrnesYmin;
	public Double UrnesZmin;
	public Double UrnesXmax;
	public Double UrnesYmax;
	public Double UrnesZmax;
	
	public Double SallesXmin;
	public Double SallesYmin;
	public Double SallesZmin;
	public Double SallesXmax;
	public Double SallesYmax;
	public Double SallesZmax;
	
	public Location JoinMin;
	public Location JoinMax;
	public Location QuitMin;
	public Location QuitMax;
	
	public Location SpecMin;
	public Location SpecMax;
	public Location QuitSpecMin;
	public Location QuitSpecMax;
	
	public String TextePanneauDraw[] = {"","","",""};
	public String TextePanneauGuess[] = {"","","",""};
	
	public Boolean destroyall;
	public Boolean destroy;
	
	public Integer JoueurVisite;
	public Integer DessinVisite;
	
	public List<UUID> JoueursPrêts = new ArrayList<>();
	
	public Material MurMaterial=Material.CLAY;
	
	public Integer Round;
	//public Integer Round;
	
	public Double timer;
	
	public Boolean isSpawned=false;
	public List<Wither> dragons= new ArrayList<>();
	public double PasDragon;
	
	public List<Location> AnvilList= new ArrayList<>();
	
	public Objective o; //Creates a objective called o
	public Objective ob; //Creates a objective called ob
	public Scoreboard timerBoard = null; //Creates a scoreboard called timerBoard(You will see what thats used for later)
	public Objective timerObj = null; // Same as above but it creates a objective called timerObj
	
	public Boolean isStarted=false;
	
	@Override
	public void onEnable() {
		
	
		destroyall=false;
		destroy=true;
		
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		ParametersFromConfig param = new ParametersFromConfig(this);
		param.setParametersFromConfig();
		
		setState(ListeEtapes.LOBBY);
		
		getCommand("adminE3D").setExecutor(new Commands(this));
		getCommand("startE3D").setExecutor(new Commands(this));
		getCommand("addplayerE3D").setExecutor(new Commands(this));
		getCommand("removeplayerE3D").setExecutor(new Commands(this));
		
		getServer().getPluginManager().registerEvents(new E3DListeners(this), this);
		
		
		for(Player OnlinePlayer:Bukkit.getOnlinePlayers()) {
			if(!getPlayers().contains(OnlinePlayer.getUniqueId()))nonplayers.add(OnlinePlayer.getUniqueId());
		}
		
		
		
		RefUrnesX=new Integer[Jmax+1];
		RefUrnesZ=new Integer[Jmax+1];
		RefSallesX=new Integer[Jmax+1][Jmax+1];
		RefSallesZ=new Integer[Jmax+1][Jmax+1];
		
		
		Scoreboard board = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
		Scoreboard bboard = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
		
		o = board.registerNewObjective("timer", "dummy"); //Registering the objective needed for the timer
		o.setDisplayName(ChatColor.RED + "Esquissé 3D" + ChatColor.GRAY); // Setting the title for the scoreboard
		
		ob = bboard.registerNewObjective("timer", "dummy"); //Registering the objective needed for the timer 	
		
		this.timerBoard = board; //Setting timerBoard equal to board.
		this.timerObj = o; //Setting timerObj equal to o. This makes it so we can access it by typing this.timerObj
		
		System.out.println("+------------------Esquisse3D-----------------+");
		System.out.println("|         Plugin created by PicreSenar        |");
		System.out.println("|           Initialization complete           |");
		System.out.println("+---------------------------------------------+");		

		
	}
	
	@Override
	public void onDisable() {

		System.out.println("+------------------Esquisse3D-----------------+");
		System.out.println("|         Plugin created by PicreSenar        |");
		System.out.println("|            Deactivation complete            |");
		System.out.println("+---------------------------------------------+");		


	}
	
	
	
	public void setState(ListeEtapes etape) {
		this.etape=etape;
	}
	
	public boolean isState(ListeEtapes etape) {
		return this.etape==etape;
	}
	
	public List<UUID> getPlayers(){
		return players;
	}
	
	public List<UUID> getNonPlayers(){
		return nonplayers;
	}
	
	public List<UUID> getSpecs(){
		return specs;
	}
	
	
	public List<UUID> getPlayersReady(){
		return JoueursPrêts;
	}
	
	public void setJTot(Integer Jtotal) {
		this.Jtot=Jtotal;
	}
	
	
	public void incrementRound() {
		Round++;
	}
	
	public void resetJoueursPrets(){
		JoueursPrêts.clear();	
	}
	
	
}
