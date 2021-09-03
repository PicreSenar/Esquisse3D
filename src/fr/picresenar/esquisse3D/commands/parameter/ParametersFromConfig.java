package fr.picresenar.esquisse3D.commands.parameter;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import fr.picresenar.esquisse3D.MainEsquisse;

public class ParametersFromConfig {
	
	public MainEsquisse main;

	public ParametersFromConfig(MainEsquisse mainEsquisse) {
		this.main = mainEsquisse;
	}

	
	public void setParametersFromConfig(){
		
		
		
		//Definition des variables de config
		main.world = main.getConfig().getString("Coordonnees.Monde");
		
		main.Jmax=main.getConfig().getInt("Joueurs.max");
		main.Jmin=main.getConfig().getInt("Joueurs.min");
		
		main.autoriserTNT = main.getConfig().getBoolean("Gameplay.autoriserTNT");
		main.carapaceSalles = main.getConfig().getBoolean("Gameplay.carapaceSalles");
		
		main.timermanches=main.getConfig().getDouble("Gameplay.timermanches");
		
		main.LobbyX=main.getConfig().getDouble("Coordonnees.CoordLobby.x");
		main.LobbyY=main.getConfig().getDouble("Coordonnees.CoordLobby.y");
		main.LobbyZ=main.getConfig().getDouble("Coordonnees.CoordLobby.z");
		
		main.UrnesXmin=main.getConfig().getDouble("Coordonnees.CoordUrnes.xmin");
		main.UrnesYmin=main.getConfig().getDouble("Coordonnees.CoordUrnes.ymin");
		main.UrnesZmin=main.getConfig().getDouble("Coordonnees.CoordUrnes.zmin");
		main.UrnesXmax=main.getConfig().getDouble("Coordonnees.CoordUrnes.xmax");
		main.UrnesYmax=main.getConfig().getDouble("Coordonnees.CoordUrnes.ymax");
		main.UrnesZmax=main.getConfig().getDouble("Coordonnees.CoordUrnes.zmax");
		
		main.SallesXmin=main.getConfig().getDouble("Coordonnees.CoordSalles.xmin");
		main.SallesYmin=main.getConfig().getDouble("Coordonnees.CoordSalles.ymin");
		main.SallesZmin=main.getConfig().getDouble("Coordonnees.CoordSalles.zmin");
		main.SallesXmax=main.getConfig().getDouble("Coordonnees.CoordSalles.xmax");
		main.SallesYmax=main.getConfig().getDouble("Coordonnees.CoordSalles.ymax");
		main.SallesZmax=main.getConfig().getDouble("Coordonnees.CoordSalles.zmax");
		
		main.JoinMin=new Location(Bukkit.getWorld(main.world),main.getConfig().getDouble("Coordonnees.CoordRejoindre.xmin"),main.getConfig().getDouble("Coordonnees.CoordRejoindre.ymin"),main.getConfig().getDouble("Coordonnees.CoordRejoindre.zmin"));
		main.JoinMax=new Location(Bukkit.getWorld(main.world),main.getConfig().getDouble("Coordonnees.CoordRejoindre.xmax"),main.getConfig().getDouble("Coordonnees.CoordRejoindre.ymax"),main.getConfig().getDouble("Coordonnees.CoordRejoindre.zmax"));

		main.QuitMin=new Location(Bukkit.getWorld(main.world),main.getConfig().getDouble("Coordonnees.CoordQuitter.xmin"),main.getConfig().getDouble("Coordonnees.CoordQuitter.ymin"),main.getConfig().getDouble("Coordonnees.CoordQuitter.zmin"));
		main.QuitMax=new Location(Bukkit.getWorld(main.world),main.getConfig().getDouble("Coordonnees.CoordQuitter.xmax"),main.getConfig().getDouble("Coordonnees.CoordQuitter.ymax"),main.getConfig().getDouble("Coordonnees.CoordQuitter.zmax"));

		main.SpecMin=new Location(Bukkit.getWorld(main.world),main.getConfig().getDouble("Coordonnees.CoordSpec.xmin"),main.getConfig().getDouble("Coordonnees.CoordSpec.ymin"),main.getConfig().getDouble("Coordonnees.CoordSpec.zmin"));
		main.SpecMax=new Location(Bukkit.getWorld(main.world),main.getConfig().getDouble("Coordonnees.CoordSpec.xmax"),main.getConfig().getDouble("Coordonnees.CoordSpec.ymax"),main.getConfig().getDouble("Coordonnees.CoordSpec.zmax"));

		main.QuitSpecMin=new Location(Bukkit.getWorld(main.world),main.getConfig().getDouble("Coordonnees.CoordQuitterSpec.xmin"),main.getConfig().getDouble("Coordonnees.CoordQuitterSpec.ymin"),main.getConfig().getDouble("Coordonnees.CoordQuitterSpec.zmin"));
		main.QuitSpecMax=new Location(Bukkit.getWorld(main.world),main.getConfig().getDouble("Coordonnees.CoordQuitterSpec.xmax"),main.getConfig().getDouble("Coordonnees.CoordQuitterSpec.ymax"),main.getConfig().getDouble("Coordonnees.CoordQuitterSpec.zmax"));

		
		Integer i=0;
		
		for(String string: main.getConfig().getStringList("Panneaux.EnclumeDraw")) {
			main.TextePanneauDraw[i]=string;
			i++;
		}
		
		i=0;
		
		for(String string: main.getConfig().getStringList("Panneaux.EnclumeGuess")) {
			main.TextePanneauGuess[i]=string;
			i++;
		}
		
	}
	
}
