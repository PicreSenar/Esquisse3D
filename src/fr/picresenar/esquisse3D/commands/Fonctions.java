package fr.picresenar.esquisse3D.commands;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;

import fr.picresenar.esquisse3D.MainEsquisse;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class Fonctions {
	
	private MainEsquisse main;

	public Fonctions(MainEsquisse mainEsquisse) {
		this.main = mainEsquisse;
	}
	
	
	

	public void TP2Lobby(Player player) {
		
		 Double Xlob=main.LobbyX;
		 Double Ylob=main.LobbyY;
		 Double Zlob=main.LobbyZ;
	
		if(Xlob==null || Ylob==null || Zlob==null) return;
		
		Location destination = new Location(player.getWorld(),Xlob,Ylob,Zlob);
		
		player.teleport(destination);
		
	}
	
	@SuppressWarnings("unused")
	public void TP2FirstUrne(Player player) {
		
		 Double XminU=main.UrnesXmin;
		 Double YminU=main.UrnesYmin;
		 Double ZminU=main.UrnesZmin;
		 Double XmaxU=main.UrnesXmax;
		 Double YmaxU=main.UrnesYmax;
		 Double ZmaxU=main.UrnesZmax;
		 
		Integer Jmax=main.getConfig().getInt("Joueurs.max");
		
		Integer JmaxX=1;
		Integer JmaxZ=1;
		String AxePrincipal="X";
		
		Integer orientation;
		
		if((XmaxU-XminU)>(ZmaxU-ZminU)) {
			AxePrincipal="X";
			JmaxX =Jmax;
			JmaxZ=1;
			orientation=180;
			
		}else {
			AxePrincipal="Z";
			JmaxX=1;
			JmaxZ=Jmax;
			orientation=90;
		}
		
		
		Double XUrne=((XmaxU-XminU)/JmaxX)/2+XminU;
		Double YUrne=((YmaxU-YminU))/2+YminU;
		Double ZUrne=((ZmaxU-ZminU)/JmaxZ)/2+ZminU;
		
		//player.sendMessage("Vous allez être envoyé en "+ XUrne+"/"+YUrne+"/"+ZUrne);
		
		if(XUrne==null || XUrne==null || ZUrne==null) return;
		
		Location destination = new Location(Bukkit.getWorld(main.world),XUrne,YUrne,ZUrne,orientation,0);
		
		player.teleport(destination);
	}
	
	public void TP2FirstSalle(Player player) {
		
		 Double XminS=main.SallesXmin;
		 Double YminS=main.SallesYmin;
		 Double ZminS=main.SallesZmin;
		 Double XmaxS=main.SallesXmax;
		 Double ZmaxS=main.SallesZmax;
		 
		 Integer Jmax=main.getConfig().getInt("Joueurs.max");
		 
		 Integer orientation;
				
		Integer JmaxX=1;
		Integer JmaxZ=1;
		//String AxePrincipal="X";
		
		if((XmaxS-XminS)>(ZmaxS-ZminS)) {
			//AxePrincipal="X";
			JmaxX =Jmax;
			JmaxZ=Jmax/2;
			orientation=180;
			
		}else {
			//AxePrincipal="Z";
			JmaxX=Jmax/2;
			JmaxZ=Jmax;
			orientation=90;
		}
		
	
		Integer PasX= (int)(XmaxS-XminS)/JmaxX;
		Integer PasZ= (int)(ZmaxS-ZminS)/JmaxZ;	
		
		Double XSalle=PasX/2+XminS;
		Double YSalle=YminS+3;
		Double ZSalle=PasZ/2+ZminS;
		
		//player.sendMessage("Vous allez être envoyé en "+ XSalle+"/"+YSalle+"/"+ZSalle);
		
		if(XSalle==null || XSalle==null || ZSalle==null) return;
		
		Location destination = new Location(Bukkit.getWorld(main.world),XSalle,YSalle,ZSalle,orientation,0);
		
		player.teleport(destination);
	}
	
	public boolean TP2NextUrne(Player player,Integer Sens) {
		
		 Double XminU=main.UrnesXmin;
		 Double ZminU=main.UrnesZmin;
		 Double XmaxU=main.UrnesXmax;
		 Double ZmaxU=main.UrnesZmax;
		 
		 Integer Jmax=main.getConfig().getInt("Joueurs.max");
		 
		 String ConfigWorld=(String) main.world;
		 String PlayerWorld=(String) player.getWorld().getName();
		 
		// player.sendMessage("Non config :'"+ConfigWorld+"'/ Non actuel :'"+PlayerWorld+"' => Egaux?" + ((ConfigWorld.equalsIgnoreCase(PlayerWorld))?"TRUE":"FALSE"));
		
		if(!ConfigWorld.equalsIgnoreCase(PlayerWorld)) {
			player.sendMessage("§4[Erreur] §cVous devez être dans le même monde que le Esquissé 3D");
			return false;
		}		
				
		Integer JmaxX=1;
		Integer JmaxZ=1;
		String AxePrincipal="X";
		Integer	orientation;
		
		if((XmaxU-XminU)>(ZmaxU-ZminU)) {
			AxePrincipal="X";
			JmaxX =Jmax;
			JmaxZ=1;
			orientation=180;
		}else {
			AxePrincipal="Z";
			JmaxX=1;
			JmaxZ=Jmax;
			orientation=90;
		}
		
		Double XDest=null;
		Double YDest=null;
		Double ZDest=null;
		
		if(AxePrincipal=="X") {
			 XDest=player.getLocation().getX()+Sens*(XmaxU-XminU)/JmaxX;
			 YDest=player.getLocation().getY();
			 ZDest=player.getLocation().getZ();
		}else {
			 XDest=player.getLocation().getX();
			 YDest=player.getLocation().getY();
			 ZDest=player.getLocation().getZ()+Sens*(ZmaxU-ZminU)/JmaxZ;
		}
		

		
	
		if(XDest==null || YDest==null || ZDest==null) return false;
		
		if(XDest<XmaxU && ZDest<ZmaxU && XDest>XminU && ZDest>ZminU) {
			Location destination = new Location(player.getWorld(),XDest,YDest,ZDest,orientation,0);
			//player.sendMessage("Téléportation en "+ XDest+"/"+YDest+"/"+ZDest);
			player.teleport(destination);
			return true;
		}else {
			if((XDest>=XmaxU && player.getLocation().getX()<XmaxU )|| (ZDest>=ZmaxU && player.getLocation().getZ()<ZmaxU)) {
				player.sendMessage("§4[Erreur] §cVous êtes dans la dernière Urne");
				return false;
			}else if((XDest<=XminU && player.getLocation().getX()>XminU )|| (ZDest<=ZminU && player.getLocation().getZ()>ZminU) ){
				player.sendMessage("§4[Erreur] §cVous êtes dans la première Urne");
				return false;
			}else {
				player.sendMessage("§4[Erreur] §cVous devez lancer cette commande depuis une urne");
				return false;
				
			}
				
		}

	}
	
	
	public boolean TP2NextSalle(Player player,Integer SensX,Integer SensZ) {
		
		 Double XminS=main.SallesXmin;
		 Double ZminS=main.SallesZmin;
		 Double XmaxS=main.SallesXmax;
		 Double ZmaxS=main.SallesZmax;
		 
		 Integer Jmax=main.getConfig().getInt("Joueurs.max");
		
		if(!player.getWorld().getName().equalsIgnoreCase(main.world)) {
			player.sendMessage("§4[Erreur] §cVous devez être dans le même monde que le Esquissé 3D");
			return false;
		}
		
		Integer JmaxX=1;
		Integer JmaxZ=1;
		Integer	orientation;
		

		
		if((XmaxS-XminS)>(ZmaxS-ZminS)) {
			JmaxX =Jmax;
			JmaxZ=Jmax/2;
			orientation=180;
			
		}else {
			JmaxX=Jmax/2;
			JmaxZ=Jmax;
			orientation=90;
		}
		
		Double XDest=null;
		Double YDest=null;
		Double ZDest=null;
		
			 XDest=player.getLocation().getX()+SensX*(XmaxS-XminS)/JmaxX;
			 YDest=player.getLocation().getY();
			 ZDest=player.getLocation().getZ()+SensZ*(ZmaxS-ZminS)/JmaxZ;
	
	
		if(XDest==null || YDest==null || ZDest==null) return false;;
		
		if(XDest<XmaxS && ZDest<ZmaxS && XDest>XminS && ZDest>ZminS) {
			Location destination = new Location(player.getWorld(),XDest,YDest,ZDest,orientation,0);
			//player.sendMessage("Téléportation en "+ XDest+"/"+YDest+"/"+ZDest);
			player.teleport(destination);
			return true;
		}else {
			if((XDest>=XmaxS && player.getLocation().getX()<XmaxS )|| (ZDest>=ZmaxS && player.getLocation().getZ()<ZmaxS)) {
				player.sendMessage("§4[Erreur] §cVous ne pouvez pas aller par là (maximum atteint)");
				return false;
			}else if((XDest<=XminS && player.getLocation().getX()>XminS )|| (ZDest<=ZminS && player.getLocation().getZ()>ZminS) ){
				player.sendMessage("§4[Erreur] §cVous ne pouvez pas aller par là (minimum atteint)");
				return false;
			}else {
				player.sendMessage("§4[Erreur] §cVous devez lancer cette commande depuis une salle");
				return false;
			}
				
		}

	}
	
	public void TP2Urne(Integer RefJoueur,UUID player) {
		
		 Double XminU=main.UrnesXmin;
		 Double ZminU=main.UrnesZmin;
		 Double XmaxU=main.UrnesXmax;
		 Double ZmaxU=main.UrnesZmax;
		 
		 Integer Jmax=main.getConfig().getInt("Joueurs.max");
		 
		Integer MidX=1;
		Integer MidZ=1;
		Integer	orientation;

		
		if((XmaxU-XminU)>(ZmaxU-ZminU)) {

			MidX =(int) (XmaxU-XminU)/(Jmax*2);
			MidZ=(int) (ZmaxU-ZminU)/2;
			orientation=180;
		}else {

			MidX=(int) (XmaxU-XminU)/(2);
			MidZ=(int) (ZmaxU-ZminU)/(Jmax*2);
			orientation=90;
		}
		
		Integer XDest=main.RefUrnesX[RefJoueur]+MidX;
		Integer YDest=(int) (main.UrnesYmin+3);
		Integer ZDest=main.RefUrnesZ[RefJoueur]+MidZ;
		
		Location destination = new Location(Bukkit.getWorld(main.world),XDest,YDest,ZDest,orientation,0);
		
		//player.sendMessage("Téléportation en "+ XDest+"/"+YDest+"/"+ZDest);
		if(Bukkit.getPlayer(player)!=null)Bukkit.getPlayer(player).teleport(destination);
		return ;
		
		
		
	}
	
	public void TP2Salle(Integer RefMot, Integer RefDessin, Player player) {
		
		 Double XminS=main.SallesXmin;
		 Double ZminS=main.SallesZmin;
		 Double XmaxS=main.SallesXmax;
		 Double ZmaxS=main.SallesZmax;
		 
		 Integer Jmax=main.getConfig().getInt("Joueurs.max");
		 
		 
		
		Integer MidX=1;
		Integer MidZ=1;
		Integer	orientation;
		
		if((XmaxS-XminS)>(ZmaxS-ZminS)) {
			orientation=0;
			
		}else {
			orientation=-90;
		}
		
		
		MidX =(int) (XmaxS-XminS)/(Jmax*2);
		MidZ=(int) (ZmaxS-ZminS)/(Jmax*2);
		
		
		
		Integer XDest=main.RefSallesX[RefMot][RefDessin]+MidX;
		Integer YDest=(int) (main.UrnesYmin+3);
		Integer ZDest=main.RefSallesZ[RefMot][RefDessin]+MidZ;
		
		Location destination = new Location(Bukkit.getWorld(main.world),XDest,YDest,ZDest,orientation,0);
		
		//player.sendMessage("Téléportation en "+ XDest+"/"+YDest+"/"+ZDest);
		player.teleport(destination);
		return ;
		
		
		
	}
	
	
	public void FillBlocks(Double Xmin,Double Ymin,Double Zmin,Double Xmax,Double Ymax,Double Zmax,Material material) {
		Integer x;
		Integer y;
		Integer z;
		
		//System.out.println("Setblock : " + main.destroy);
				
		for (x = (int) Math.round(Xmin); x <= Xmax; x++) {
			for (y = (int) Math.round(Ymin); y <= Ymax; y++) {
				for (z = (int) Math.round(Zmin); z <= Zmax; z++) { 
					
					Location loc=new Location(Bukkit.getWorld(main.world),x,y,z);
					loc.getChunk().load();
					
					Material PreviousMaterial=Bukkit.getWorld(main.world).getBlockAt(x, y, z).getType();
					
//					System.out.println(main.destroyall +"/"+PreviousMaterial+"/"+material);
					
					if(main.destroyall||((material)!=Material.AIR)||(material==Material.AIR && PreviousMaterial!=main.MurMaterial&& PreviousMaterial!=Material.BEDROCK && PreviousMaterial!=Material.BARRIER)) {
						if((main.destroy)||(PreviousMaterial==Material.AIR))Bukkit.getWorld(main.world).getBlockAt(x, y, z).setType(material);
					}
				}
			}
			
		}	
			
	}
	
	public void KillEntityInArea(Double Xmin,Double Ymin,Double Zmin,Double Xmax,Double Ymax,Double Zmax) {
		
		for(Entity e : Bukkit.getWorld(main.world).getEntities()) {
			if(e.getLocation().getX()>=Xmin && e.getLocation().getX()<=Xmax && e.getLocation().getZ()>=Zmin && e.getLocation().getZ()<=Zmax && e.getType()!=EntityType.PLAYER) {
			e.remove();
			}
		}
	}
	
	
	public void PutSign(Double X,Double Y, Double Z,BlockFace face,String Texte[]) {
		Integer x=(int) Math.round(X);
		Integer y=(int) Math.round(Y);
		Integer z=(int) Math.round(Z);
		Integer i;
		

		Block block = Bukkit.getWorld(main.world).getBlockAt(x, y, z);
		block.setType(Material.OAK_WALL_SIGN);	
		

	    org.bukkit.block.data.type.WallSign signdata = (org.bukkit.block.data.type.WallSign) block.getBlockData();
//      Bukkit.broadcastMessage(face.name());
        signdata.setFacing(face);
	    block.setBlockData(signdata);
        
	    
	    org.bukkit.block.Sign blockSign = (org.bukkit.block.Sign) block.getState();
		for(i=0;i<=3;i++) blockSign.setLine(i, Texte[i]);
		blockSign.update();
	
					
	}
	
	public ItemStack getSkull(String owner,String name) {
		
		ItemStack skull = new ItemStack(Material.PLAYER_HEAD,1,(byte)3);
		SkullMeta meta=(SkullMeta) skull.getItemMeta();
		meta.setOwner(owner);
		meta.setDisplayName(name);
		
		skull.setItemMeta(meta);
		
		return skull;
	}

	public ItemStack getItem(Material material, String customName,List<String> lore) {
		
		ItemStack it = new ItemStack(material);
		ItemMeta itM = it.getItemMeta();
		
		if(customName!=null) itM.setDisplayName(customName);
		
		if(lore!=null)itM.setLore(lore);
		
		it.setItemMeta(itM);
		
		return it;
		
	}
	
	
	
	public String getSalleName(Location location) {
		

		
		Integer Jmax=main.getConfig().getInt("Joueurs.max");
		
		 Double XminU=main.UrnesXmin;
//		 Double YminU=main.UrnesYmin;
		 Double ZminU=main.UrnesZmin;
		 Double XmaxU=main.UrnesXmax;
//		 Double YmaxU=main.UrnesYmax;
		 Double ZmaxU=main.UrnesZmax;
		 
		 Double XminS=main.SallesXmin;
//		 Double YminS=main.SallesYmin;
		 Double ZminS=main.SallesZmin;
		 Double XmaxS=main.SallesXmax;
//		 Double YmaxS=main.SallesYmax;
		 Double ZmaxS=main.SallesZmax;
	
		
		if(location.getBlockX()<=XmaxS && location.getBlockZ()<=ZmaxS && location.getBlockX()>=XminS && location.getBlockZ()>=ZminS) {
		//Joueur est dans une salle
			for(int i=0;i<=Jmax-1;i++) {
				for(int j=0;j<=Jmax-1;j++) {
					//System.out.println(i+"/"+j);
					if (main.RefSallesX[i+1][j+1]!=null && main.RefSallesZ[i+1][j+1]!=null  && main.RefSallesX[i][j] !=null  && main.RefSallesZ[i][j]!=null ) {
						if(location.getBlockX()<=main.RefSallesX[i+1][j+1] && location.getBlockZ()<=main.RefSallesZ[i+1][j+1]  && location.getBlockX()>=main.RefSallesX[i][j]  && location.getBlockZ()>=main.RefSallesZ[i][j] ) {
							return "§eMot n°"+(i+1)+"/§dDessin n°"+(j+1);

						}
					}
				}
			}
			
		
		}else if(location.getBlockX()<=XmaxU && location.getBlockZ()<=ZmaxU && location.getBlockX()>=XminU && location.getBlockZ()>=ZminU) {
			//Le joueur est dans une urne
			
			Integer MaxXUrne;
			Integer MaxZUrne;
			
			for(int i=0;i<=Jmax-1;i++) {
				
				if(main.RefUrnesX[i+1]==main.RefUrnesX[i]) {
					MaxXUrne=main.RefUrnesX[i+1];
					MaxZUrne=main.RefUrnesZ[Jmax];
				}else {
					MaxXUrne=main.RefUrnesX[Jmax];
					MaxZUrne=main.RefUrnesZ[i+1];
				}
//				System.out.println(location.getBlockX()+"/"+location.getBlockZ());
//				System.out.println(main.RefUrnesX[i]+" "+MaxXUrne+"/"+main.RefUrnesZ[i]+" "+MaxZUrne);
					if(location.getBlockX()<= MaxXUrne && location.getBlockZ()<= MaxZUrne  && location.getBlockX()>=main.RefUrnesX[i]  && location.getBlockZ()>=main.RefUrnesZ[i] ) {
//						System.out.println("STOP");
						return "§2Joueur"+(i+1);
					}
			}
			
						
		}else {
			return "§4[Erreur] §cVous n'êtes pas dans une salle du Esquissé 3D";
		}
		
		return "§4[Erreur] §cVotre position n'a pas été détectée";
	}
	
	
	public boolean isInE3DArea(Location location) {
		
		 Double XminU=main.UrnesXmin;
		 Double YminU=main.UrnesYmin;
		 Double ZminU=main.UrnesZmin;
		 Double XmaxU=main.UrnesXmax;
		 Double YmaxU=main.UrnesYmax;
		 Double ZmaxU=main.UrnesZmax;
		 
		 Double XminS=main.SallesXmin;
		 Double YminS=main.SallesYmin;
		 Double ZminS=main.SallesZmin;
		 Double XmaxS=main.SallesXmax;
		 Double YmaxS=main.SallesYmax;
		 Double ZmaxS=main.SallesZmax;
		 
		if(location.getX()<=XmaxU && location.getY()<=YmaxU && location.getZ()<=ZmaxU && location.getX()>=XminU && location.getY()>=YminU && location.getZ()>=ZminU) {
			return true;
		}else if(location.getX()<=XmaxS && location.getY()<=YmaxS && location.getZ()<=ZmaxS && location.getX()>=XminS && location.getY()>=YminS && location.getZ()>=ZminS) {
			return true;
		}else {
			return false;
		}
	}
	
	public void PigNoAI(Pig pig) {	
		pig.setAI(false);
	}
	
	public void WitherNoAI(Wither wither) {	
		wither.setAI(false);
	}
	


	public boolean isBetweenLocations(Location cible,Location min,Location max) {
		
		
		if(cible.getX()<=max.getX() && cible.getY()<=max.getY() && cible.getZ()<=max.getZ() && cible.getX()>=min.getX() && cible.getY()>=min.getY() && cible.getZ()>=min.getZ()) {
			return true;

		}else {
			return false;
		}
		
	}
	

}
