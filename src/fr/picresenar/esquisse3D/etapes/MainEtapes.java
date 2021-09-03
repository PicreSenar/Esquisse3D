package fr.picresenar.esquisse3D.etapes;

import java.util.Arrays;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
//import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.picresenar.esquisse3D.MainEsquisse;
import fr.picresenar.esquisse3D.Players;
import fr.picresenar.esquisse3D.commands.Fonctions;
//import fr.picresenar.esquisse3D.commands.Titles;
import fr.picresenar.esquisse3D.commands.parameter.ListeEtapes;


public class MainEtapes {
	

	
	private MainEsquisse main;

	public MainEtapes(MainEsquisse mainEsquisse) {
		this.main = mainEsquisse;
	}
	
	public void CheckDemarrage() {
		Integer AFKPlayers=0;
		
		for(UUID pl:main.getPlayers()) {
			if(!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(pl)))AFKPlayers++;
		}
		
		System.out.print("Joueurs AFK : "+AFKPlayers);
		
		if(main.getPlayers().toArray().length<main.Jmin) {
			for(UUID players:main.getPlayers())if(Bukkit.getPlayer(players)!=null)Bukkit.getPlayer(players).sendMessage("§4[Erreur]§c Pas assez de joueurs inscrits : §d"+ main.getPlayers().toArray().length +  " (min : "+main.Jmin+")");
			//System.out.print("§4[Erreur] §cPas assez de joueurs inscrits : "+ main.getPlayers().toArray().length +  " (min : "+main.Jmin+")");
		}else if(main.getPlayers().toArray().length-AFKPlayers<main.Jmin) {
			for(UUID players:main.getPlayers())if(Bukkit.getPlayer(players)!=null)Bukkit.getPlayer(players).sendMessage("§4[Erreur] §cPas assez de joueurs inscrits et/ou trop d'inscrits déconnectés : §d"+ (main.getPlayers().toArray().length-AFKPlayers) +  " (min : "+main.Jmin+")");
			//System.out.print("§4[Erreur] §cPas assez de joueurs inscrits et/ou trop d'inscrits déconnectés : "+ (main.getPlayers().toArray().length-AFKPlayers) +  " (min : "+main.Jmin+")");
		}else {
			DebutPartie();
		}
	}

	public void DebutPartie() {
		
		main.isStarted =true;
		
		main.etape=ListeEtapes.STARTING;
		
		main.timer=30.0;
		Timer task = new Timer(main);
		task.runTaskTimer(this.main, 0, 20);
		
		
	}
	
	
	public void LancementPartie() {
		
//		 Fonctions f= new Fonctions(main);
		 Builders b= new Builders(main);
//		 AdminMenu adm=new AdminMenu(main);
		// Players p=new Players(main);
		 
		 main.etape=ListeEtapes.INITIALIZING;
		
		for(UUID player:main.getPlayers()) {
			
			if(Bukkit.getPlayer(player)!=null) {
				Bukkit.getPlayer(player).sendMessage("§2[Esquissé 3D]§a Démarrage..."+"\n\n");
				Bukkit.getPlayer(player).setGameMode(GameMode.CREATIVE);
			}else if (Bukkit.getOfflinePlayer(player)!=null){
				for(UUID players:main.getPlayers())if(Bukkit.getPlayer(players)!=null)Bukkit.getPlayer(players).sendMessage("§4[Esquissé 3D] §3"+ Bukkit.getOfflinePlayer(player).getName()+"§c n'est toujours pas en ligne, il a donc été supprimé des participants");
				main.JoueursPrêts.add(player);
			}
			
			
		}
		
		for(UUID player:main.JoueursPrêts) main.getPlayers().remove(player);
		
		main.resetJoueursPrets();
		
		
		for(UUID spec:main.getSpecs()) {
			if(Bukkit.getPlayer(spec)!=null) {
				Bukkit.getPlayer(spec).sendMessage("§2[Esquissé 3D]§a Démarrage..."+"\n\n");
				Bukkit.getPlayer(spec).setGameMode(GameMode.SPECTATOR);
			}
		}
		
		
		Bukkit.broadcastMessage("§2[Esquissé 3D]§a Initialisation : §c/!\\ §aRalentissements potentiels"+"\n\n");
		//System.out.println("Avant construction : " + main.destroy);
		b.GetRefSalles();
		b.ConstructionUrnes(true);
		b.ConstructionSalles(true,main.carapaceSalles);
		b.ConstructionPanneauxSalles(main.TextePanneauDraw);
		
		main.NomJoueurs=new String[main.getPlayers().toArray().length+1];
		
		main.setJTot(main.getPlayers().toArray().length+1);
		
		ChoixdesMots(false,null);	
		
		
		
	}
	
	public void ChoixdesMots(Boolean rattrapage, Player player) {
		 Fonctions f= new Fonctions(main);
		 Builders b= new Builders(main);
//		 AdminMenu adm=new AdminMenu(main);
//		 Players p=new Players(main);
//		 Titles t= new Titles(main);
		 
		 main.etape=ListeEtapes.CHOSING;
		 
		 Integer i=0;
		 Entity entity;
		 
		 
		 
		 if(!rattrapage) {
			 for(UUID specs:main.getSpecs()) {
				if(Bukkit.getPlayer(specs)!=null) {
					Bukkit.getPlayer(specs).sendMessage("§2[Esquissé 3D]§a Etape 1 : Choix des mots" );	
					f.TP2Urne(0, specs);
				}
			 }
		 }
		 
		 
		 
		 
		 for(UUID pl:main.getPlayers()) {
			 
			
			 if((!rattrapage && Bukkit.getPlayer(pl)!=null)||(rattrapage && player.getUniqueId().toString().equalsIgnoreCase(pl.toString()))) {
				 

					
					f.TP2Urne(i, pl); 
					
					Bukkit.getPlayer(pl).getInventory().clear();	
					Bukkit.getPlayer(pl).getInventory().setItemInHand(f.getItem(Material.NAME_TAG, null, Arrays.asList("1- Renommez-moi dans l'enclume","2 - Faites clic-droit avec moi sur le cochon","3 - ...","4 - PROFIT !")));
	
					Bukkit.getPlayer(pl).sendMessage("§2[Esquissé 3D]§a Etape 1 : Choisir son mot" );		
					Bukkit.getPlayer(pl).sendMessage("§a - Choisissez un mot" );		
					Bukkit.getPlayer(pl).sendMessage("§a - Faites clic-droit avec le nametag sur l'enclume");
					Bukkit.getPlayer(pl).sendMessage("§a - Renommez-le par le mot choisi");
					Bukkit.getPlayer(pl).sendMessage("§a - Faites clic-droit sur le cochon avec le nametag renommé"+"\n\n");
					
					//t.sendTitle(Bukkit.getPlayer(pl), "Etape 1 : Choix des mots", 10, 1000, 10, ChatColor.DARK_GREEN); 
					
					if(Bukkit.getPlayer(pl)!=null)main.NomJoueurs[i]=Bukkit.getPlayer(pl).getName();
			}
			
			if(!rattrapage)main.setJTot(i+1);
			
			i++;
			
		 }
		 
		 
		 
		
		 if(!rattrapage) {
		
			 main.RefCochons=new Entity[main.Jtot][main.Jtot+1];
			 
			 for(i=0;i<=main.Jtot-1;i++) {
		 
			 
			 
			 entity=b.SpawnCochons(true, i, 0);
			 entity.setCustomName("Mot n°"+(i+1));
			 Pig pig=(Pig)entity;
			 f.PigNoAI(pig);
			 pig.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1000000, 10));
			 main.RefCochons[i][0]=entity;
			}
		 }
		 
		 main.etape=ListeEtapes.CHOSING;
		 
		
	}
	
	public void Drawing(Boolean Rattrapage, Player player) {
		
		 Fonctions f= new Fonctions(main);
		 Builders b= new Builders(main);
//		 AdminMenu adm=new AdminMenu(main);
//		 Players p=new Players(main);
//		 Titles t= new Titles(main);
		 
		 main.etape=ListeEtapes.DRAWING;
		 
		 Integer i=0;
		 Entity entity;
		 String NomJoueurPrecedent;
		 
		 String NameLastPig;
		 
		if (!Rattrapage) b.ConstructionPanneauxSalles(main.TextePanneauDraw);
		else System.out.print("Rattrapage de " + player);
		 
		 
		 for(UUID pl:main.getPlayers()) {
			
			 if((!Rattrapage && Bukkit.getPlayer(pl)!=null)||(Rattrapage && player.getUniqueId().toString().equalsIgnoreCase(pl.toString()))) {
				
				Integer round =main.Round;
				
				Integer RefMot;
				Integer RefDessin;
				Integer decalage;
				 
				//System.out.println(main.Jtot%2);
				if (Rattrapage) {
					System.out.print("Rattrapage de " + pl);
					
				}

				
				
				if(main.Jtot%2!=0) {
					decalage=1;
				}else {
					decalage=0;
				}	

				if(i+round+decalage-1<0) {
					RefMot=main.Jtot-1;
				}else if(i+round+decalage-1>=main.Jtot){
					RefMot=i+round+decalage-1-main.Jtot;
				}else {
					RefMot=i+round+decalage-1;
				}
				
				
				
							
				RefDessin=Math.round((main.Round-1)/2);
				
				System.out.println("Mot n°"+RefMot+" "+"Dessin:"+(RefDessin) +" Round:"+ round);
					
				NameLastPig= main.RefCochons[RefMot][round-1].getCustomName();
				
				System.out.println("Nom dernier cochon :"+NameLastPig);
				
				if(main.Jtot%2==0 && round==1) {
					NomJoueurPrecedent=main.NomJoueurs[i];
			 	}else if(i>=main.Jtot-1) {
					NomJoueurPrecedent=main.NomJoueurs[0];
				}else {
					NomJoueurPrecedent=main.NomJoueurs[i+1];
				}
				
				
				if(i==0&&round==1)for(UUID specs:main.getSpecs()) {
					if(Bukkit.getPlayer(specs)!=null) {
						Bukkit.getPlayer(specs).sendMessage("§2[Esquissé 3D]§a Etape "+ (round+1)+" : Construire" );
						f.TP2Salle(RefMot, RefDessin, Bukkit.getPlayer(specs));
					}
				}
				
				
						
				f.TP2Salle(RefMot, RefDessin, Bukkit.getPlayer(pl));
				//Bukkit.getPlayer(pl).sendMessage(f.getSalleName(Bukkit.getPlayer(pl).getLocation()));
				Bukkit.getPlayer(pl).getInventory().clear();	
				
			
				ItemStack Carotte = new ItemStack(Material.CARROT);
				ItemMeta CarotteM = Carotte.getItemMeta();
				
				CarotteM.setDisplayName("§dCarotte délicieuse");
				CarotteM.setLore(Arrays.asList("Lorsque vous aurez fini de dessiner,","donnez-moi au cochon,","pour signifier que vous êtes prêt"));
				
				Carotte.setItemMeta(CarotteM);
				
				Bukkit.getPlayer(pl).getInventory().setItemInHand(Carotte);
				
				Bukkit.getPlayer(pl).sendMessage("§2[Esquissé 3D]§a Etape "+ (round+1)+" : Construire" );		
				if(main.Jtot%2==0 && round==1) {
					Bukkit.getPlayer(pl).sendMessage("§a - Vous êtes un nombre de joueurs pair, dessinez donc votre mot : §e"+ NameLastPig);		
				}else {
					Bukkit.getPlayer(pl).sendMessage("§a - Dessinez le mot donné par §3"+NomJoueurPrecedent+" §a: §e"+ NameLastPig);
				}
				Bukkit.getPlayer(pl).sendMessage("§a - Vous avez §4"+ main.timermanches+"§a secondes");
				Bukkit.getPlayer(pl).sendMessage("§a - Donnez une carotte au cochon quand vous aurez fini"+"\n\n");
				
				if (main.RefCochons[RefMot][round]==null) {
					 entity=b.SpawnCochons(false, RefMot, RefDessin);
					 entity.setCustomName(NameLastPig);
					
					 Pig pig=(Pig)entity;
					 f.PigNoAI(pig);
					 pig.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1000000, 10));
					 
					// System.out.println("Nouveau cochon :"+entity.getCustomName());
					main.RefCochons[RefMot][round]=entity;
				}
			 
			 }
			
			i++;
			
		 }
		 
		 if (!Rattrapage) {
			Timer task = new Timer(main);
			main.timer=main.timermanches;
			task.timerstop=0;
			task.runTaskTimer(this.main, 0, 20); 
		 }

			
	}
		 
	public void Guessing(Boolean Rattrapage, Player player) {
		
		 Fonctions f= new Fonctions(main);
		 Builders b= new Builders(main);
//		 AdminMenu adm=new AdminMenu(main);
//		 Players p=new Players(main);
//		 Titles t= new Titles(main);
		 
		 main.etape=ListeEtapes.GUESSING;
		 
		 Integer i=0;
		 Entity entity;
		 String NomJoueurPrecedent;
		 
		 Location nurserie=new Location(Bukkit.getWorld(main.world),main.SallesXmin,main.SallesYmin,main.SallesZmin);
		 
		 b.ConstructionPanneauxSalles(main.TextePanneauGuess);
		 
		 for(@SuppressWarnings("unused") UUID pl:main.getPlayers()) {
			 
			if(!Rattrapage){
			 for(Entity pig:main.RefCochons[i]) {
				 
				 if(pig!=null) {
					 //System.out.println(i+": Le cochon "+pig.getCustomName()+" a été tp à la nurserie");
					 pig.teleport(nurserie);
				 }
			 }
			 i++;
			}
		}
		 	
		 i=0;
		 
		 for(UUID pl:main.getPlayers()) {
			
			 if((!Rattrapage && Bukkit.getPlayer(pl)!=null)||(Rattrapage && player.getUniqueId().toString().equalsIgnoreCase(pl.toString()))) {
				
				Integer round =main.Round;
				
				Integer RefMot;
				Integer RefDessin;
				Integer decalage;
				 
				
				if(main.Jtot%2!=0) {
					decalage=1;
				}else {
					decalage=0;
				}	
				
				
				if(i+round+decalage-1<0) {
						
					RefMot=main.Jtot-1;
						
				}else if((i+round+decalage-1>=main.Jtot)){
						
					RefMot=i+round+decalage-1-main.Jtot;
						
				}else {
						
					RefMot=i+round+decalage-1;
				
				}
				
							
				RefDessin=Math.round((main.Round-1)/2);
					
				if(i==main.Jtot-1) {
					NomJoueurPrecedent=main.NomJoueurs[0];
				}else {
					NomJoueurPrecedent=main.NomJoueurs[i+1];
				}
				
				if(i==0)for(UUID specs:main.getSpecs()) {
					if(Bukkit.getPlayer(specs)!=null) {
						Bukkit.getPlayer(specs).sendMessage("§2[Esquissé 3D]§a Etape "+ (round+1)+" : Deviner" );	
					}
				}
				
					
				f.TP2Salle(RefMot, RefDessin, Bukkit.getPlayer(pl));
				//Bukkit.getPlayer(pl).sendMessage(f.getSalleName(Bukkit.getPlayer(pl).getLocation()));
				Bukkit.getPlayer(pl).getInventory().clear();	
								
				Bukkit.getPlayer(pl).getInventory().setItemInHand(f.getItem(Material.NAME_TAG, null, Arrays.asList("1- Renommez-moi dans l'enclume","2 - Faites clic-droit avec moi sur le cochon","3 - ...","4 - PROFIT !")));
				
				Bukkit.getPlayer(pl).sendMessage("§2[Esquissé 3D]§a Etape "+ (round+1)+" : Deviner" );		
				Bukkit.getPlayer(pl).sendMessage("§a - Devinez le mot donné par §3"+NomJoueurPrecedent);		
				Bukkit.getPlayer(pl).sendMessage("§a - Renommez le nameTag dans l'enclume");
				Bukkit.getPlayer(pl).sendMessage("§a - Et faites clic-droit sur le cochon avec notre nametag"+"\n\n");
						 
				 
				if(main.RefCochons[RefMot][round]==null) {
					entity=b.SpawnCochons(false, RefMot, RefDessin);
					 entity.setCustomName("Renommez moi avec le name Tag");
					
					 Pig pig=(Pig)entity;
					 f.PigNoAI(pig);
					 pig.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1000000, 10));
					 
					// System.out.println("Nouveau cochon :"+entity.getCustomName());
					 main.RefCochons[RefMot][round]=entity;
				}
			 
			 }
			
			i++;
			if (i>=main.Jtot)break;
			
		 }
		 
			
	}	

	@SuppressWarnings({ "unused", "deprecation" })
	public void Revealing(Boolean rattrapage, Player player) {
		
		 Fonctions f= new Fonctions(main);
//		 Builders b= new Builders(main);
//		 AdminMenu adm=new AdminMenu(main);
//		 Players p=new Players(main);
//		 Titles t= new Titles(main);
		 
		 main.etape=ListeEtapes.REVEALING;
		 
		 Integer i=0;
//		 Entity entity;
//		 String NomJoueurPrecedent;
		 
		 Location nurserie=new Location(Bukkit.getWorld(main.world),main.SallesXmin,main.SallesYmin,main.SallesZmin);
		 		 
		 for(UUID pl:main.getPlayers()) {
			 
			 for(Entity pig:main.RefCochons[i]) if(pig!=null) pig.teleport(nurserie);
			 
			 i++;
		 }
		 	
		 i=0;
		
		 String NomGuide=null;
		
		 
		 for(UUID pl:main.getPlayers()) {
			
			 if((!rattrapage &&Bukkit.getPlayer(pl)!=null)||(rattrapage && player.getUniqueId().toString().equalsIgnoreCase(pl.toString()))) {
				
				Integer round =main.Round;
				
				
				Integer RefDessin;

				//System.out.println(Math.round((main.Jtot-1)/2)+"/"+(main.Jtot-1)/2);
				
				
							
				RefDessin=Math.round((main.Jtot-main.Jtot%2)/2)-1;

								
				f.TP2Salle(i, RefDessin, Bukkit.getPlayer(pl));
				//Bukkit.getPlayer(pl).sendMessage(f.getSalleName(Bukkit.getPlayer(pl).getLocation()));
				Bukkit.getPlayer(pl).getInventory().clear();	
				
				
				
				if(i==0||(i!=0 && main.Guide==null)) {
					main.Guide = pl;
					NomGuide=Bukkit.getPlayer(main.Guide).getName();
				}
				
				Bukkit.getPlayer(pl).sendMessage("§2[Esquissé 3D]§a Etape "+ (round+1)+" : Découvrir" );		
				Bukkit.getPlayer(pl).sendMessage("§a - Voilà le résultat final ...");		
				Bukkit.getPlayer(pl).sendMessage("§a - Vous aviez donné le mot : §e"+main.RefCochons[i][0].getCustomName());
				Bukkit.getPlayer(pl).sendMessage("§a - Qui est devenu : §5"+main.RefCochons[i][round-1].getCustomName());
				if(main.Guide!=pl)Bukkit.getPlayer(pl).sendMessage("§3"+NomGuide+" §dsera votre guide pour visiter vos oeuvres"+"\n\n");		 
				
				
				if(i==0)for(UUID specs:main.getSpecs()) {
					if(Bukkit.getPlayer(specs)!=null) {
					
					
					Bukkit.getPlayer(specs).sendMessage("§2[Esquissé 3D]§a Etape "+ (round+1)+" : Découvrir" );		
					Bukkit.getPlayer(specs).sendMessage("§3"+NomGuide+" §dsera le guide pour visiter les oeuvres"+"\n\n");
					}
				}
				
				
			 }
					
			i++;
			if (i>=main.Jtot)break;
			
			
		 }
		 
		if(!rattrapage) {
			main.JoueurVisite =0;
			main.DessinVisite =-1;
		}
		
			if(Bukkit.getPlayer(main.Guide)!=null) {
				Bukkit.getPlayer(main.Guide).getInventory().setItem(0,f.getItem(Material.BOOK, "§dSalle E3D précédente", null));
				Bukkit.getPlayer(main.Guide).getInventory().setItem(8,f.getItem(Material.BOOK, "§dSalle E3D suivante", null));
				if(!rattrapage)Bukkit.getPlayer(main.Guide).sendMessage("§dTu es le guide, utilise le livre pour passer de salle en salle"+"\n\n");
				Bukkit.getPlayer(main.Guide).updateInventory();
			}
	
			
			

		
		
	}
	
	
	public void Visite() {
		
		Integer i=0;

		Integer JoueurVisited=main.JoueurVisite;
		Integer DessinVisited=main.DessinVisite;
		

//		String NomJoueurGive;
		String NomJoueurDraw;
		String NomJoueurGuess;
		
		Integer decalage=0;
		
		if(main.Jtot%2!=0)decalage=1;
		
		Fonctions f=new Fonctions(main);
		
		
//		NomJoueurGive="";
		
		if(JoueurVisited-2*(DessinVisited)-decalage>=0) {
			NomJoueurDraw=main.NomJoueurs[JoueurVisited-2*(DessinVisited)-decalage];
		}else {
			NomJoueurDraw=main.NomJoueurs[main.Jtot+JoueurVisited-2*(DessinVisited)-decalage];
		}
		
		
		if(JoueurVisited-2*(DessinVisited)-decalage-1>=0) {
			NomJoueurGuess=main.NomJoueurs[JoueurVisited-2*(DessinVisited)-decalage-1];
		}else {
			NomJoueurGuess=main.NomJoueurs[main.Jtot+JoueurVisited-2*(DessinVisited)-decalage-1];
		}
		
		
//		if(DessinVisited==0) {
//		
//			NomJoueurGive=main.NomJoueurs[JoueurVisited];
//			
//		}else if(JoueurVisited-2*(DessinVisited-1)-decalage>=0) {
//			
//			NomJoueurGive=main.NomJoueurs[JoueurVisited-2*(DessinVisited-1)-decalage];
//			
//		}else {
//			NomJoueurGive=main.NomJoueurs[main.Jtot+JoueurVisited-2*(DessinVisited-1)-decalage];
//		}
		
		for(UUID pl:main.getPlayers()) {
			
			if(Bukkit.getPlayer(pl)!=null) {
			
				if(i==0)for(UUID specs:main.getSpecs()) {
					f.TP2Salle(main.JoueurVisite, main.DessinVisite, Bukkit.getPlayer(specs));
					
					Bukkit.getPlayer(specs).sendMessage("§2[Esquissé 3D]§a Evolution du mot de §3"+main.NomJoueurs[JoueurVisited]+"§a - Salle n°"+(DessinVisited+1));		
					if(DessinVisited==0)Bukkit.getPlayer(specs).sendMessage("§a - §3"+main.NomJoueurs[JoueurVisited]+"§a a donné le mot : "+main.RefCochons[JoueurVisited][0].getCustomName() );		
					Bukkit.getPlayer(specs).sendMessage("§a - §3"+NomJoueurDraw+"§a a construit : §e"+main.RefCochons[JoueurVisited][1+2*DessinVisited].getCustomName());	
					Bukkit.getPlayer(specs).sendMessage("§a - Et §3"+NomJoueurGuess+"§a a deviné : §5"+main.RefCochons[JoueurVisited][2*DessinVisited+2].getCustomName()+"\n\n");	

				}
			
			f.TP2Salle(main.JoueurVisite, main.DessinVisite, Bukkit.getPlayer(pl));
			
			Bukkit.getPlayer(pl).sendMessage("§2[Esquissé 3D]§a Evolution du mot de §3"+main.NomJoueurs[JoueurVisited]+"§a - Salle n°"+(DessinVisited+1));		
			if(DessinVisited==0)Bukkit.getPlayer(pl).sendMessage("§a - §3"+main.NomJoueurs[JoueurVisited]+"§a a donné le mot : "+main.RefCochons[JoueurVisited][0].getCustomName() );		
			Bukkit.getPlayer(pl).sendMessage("§a - §3"+NomJoueurDraw+"§a a construit : §e"+main.RefCochons[JoueurVisited][1+2*DessinVisited].getCustomName());	
			Bukkit.getPlayer(pl).sendMessage("§a - Et §3"+NomJoueurGuess+"§a a deviné : §5"+main.RefCochons[JoueurVisited][2*DessinVisited+2].getCustomName()+"\n\n");	
			
			}
			
			i++;
		}
		
	}
	
	
	
	@SuppressWarnings("deprecation")
	public void Destroy(Boolean rattrapage,Player player){
		
		main.etape=ListeEtapes.DESTRUCTION;
		Fonctions f=new Fonctions(main);
		
		 for(UUID pl:main.getPlayers()) {
		 
			 if((!rattrapage &&Bukkit.getPlayer(pl)!=null)||(rattrapage && player.getUniqueId().toString().equalsIgnoreCase(pl.toString()))) {
				 
				 Bukkit.getPlayer(pl).sendMessage("§2[Esquissé 3D]§a Etape Finale : §4Destruction");
				 Bukkit.getPlayer(pl).sendMessage("§a- Vous avez "+(main.timermanches/2)+" pour tout casser !");
				 Bukkit.getPlayer(pl).sendMessage("§a- La TNT est, bien-entendu, réactivée !");
				 Bukkit.getPlayer(pl).sendMessage("§a- ENJOY !"+"\n\n");
				 
				 Bukkit.getPlayer(pl).getInventory().clear();
				 
				 Bukkit.getPlayer(pl).getInventory().setItem(0,f.getItem(Material.TNT,null,null));
				 Bukkit.getPlayer(pl).getInventory().setItem(1,f.getItem(Material.FLINT_AND_STEEL,null,null));
				 Bukkit.getPlayer(pl).getInventory().setItem(8,f.getItem(Material.BOOK,"§dTéléportation Esquissé 3D",null));
				 Bukkit.getPlayer(pl).updateInventory();
			 }

		 }
		 
		 if(!rattrapage) {
			 for(UUID specs:main.getSpecs()) {
				 Bukkit.getPlayer(specs).sendMessage("§2[Esquissé 3D]§a Etape Finale : §4Destruction");
				 Bukkit.getPlayer(specs).sendMessage("§a- Les joueurs ont "+(main.timermanches/2)+" pour tout casser !");
				 Bukkit.getPlayer(specs).sendMessage("§a- La TNT est, bien-entendu, réactivée !");
	
			 }
			 
			
			 if(!rattrapage){	
				Timer task = new Timer(main);
				main.timer=main.timermanches/2;
				task.timerstop=0;
				task.runTaskTimer(this.main, 0, 20); 
			 }else {
				 f.TP2Salle(0, 0, player);
			 }
		 }
		
	}
		
	
	
	
	@SuppressWarnings("deprecation")
	public void ArretPartie(){
		
		Players p=new Players(main);
		Fonctions f=new Fonctions(main);
		
		main.isStarted =false;
		
		main.etape=ListeEtapes.ENDING;
		
		main.timer=0.0;

		for(UUID players:main.getPlayers()) {
			
			if(Bukkit.getPlayer(players)!=null) {
				
				Bukkit.getPlayer(players).sendMessage("§2[Esquissé 3D]§a Fin de la partie");
				
				
				Bukkit.getPlayer(players).getInventory().clear();
				Bukkit.getPlayer(players).updateInventory();
				
				Bukkit.getPlayer(players).setGameMode(Bukkit.getDefaultGameMode());
				
				
				
				
				f.TP2Lobby(Bukkit.getPlayer(players));
				
			}	
				
		}
		
		for(UUID specs:main.getSpecs()) {
			Bukkit.getPlayer(specs).sendMessage("§2[Esquissé 3D]§a Fin de la partie");
			
			Bukkit.getPlayer(specs).setGameMode(Bukkit.getDefaultGameMode());
			
			f.TP2Lobby(Bukkit.getPlayer(specs));

		}
		
		p.RemoveAllPlayer();
		
		main.JoueursPrêts.clear();
		
		
		
		main.etape=ListeEtapes.LOBBY;
		
		
	}
}
