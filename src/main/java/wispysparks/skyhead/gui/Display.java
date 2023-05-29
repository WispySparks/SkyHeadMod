package wispysparks.skyhead.gui;

import static wispysparks.skyhead.SkyHead.MC;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import wispysparks.skyhead.SkyHead;
import wispysparks.skyhead.api.API;
import wispysparks.skyhead.api.Cache;

/**
 * This class is used for setting and refreshing player names with levels.
 */
public class Display {
	
    public static void setLevel(Entity entity) { 
    	if (entity instanceof EntityOtherPlayerMP) { 
			final EntityOtherPlayerMP player = (EntityOtherPlayerMP) entity; 
			String result = Cache.query(player.getName());
			// I want to try and get their level again if it failed before
			if (!Cache.contains(player.getName()) || result.isEmpty() || result.equals(" §fLimit")) { 
            	new Thread(() -> {
					String level = API.getLevel(player.getUniqueID()); 
					Cache.addPlayer(player.getName(), level);
					player.refreshDisplayName(); 
				}).start();
			}
			else { 
				player.refreshDisplayName(); 
			}
		}
    }

    public static void setLevels() {
		if (SkyHead.enabled()) {
			for (EntityPlayer player : MC.theWorld.playerEntities) { 
				setLevel(player);
			}
		} else {
			for (EntityPlayer player : MC.theWorld.playerEntities) {
				player.refreshDisplayName();
			}
		}
	}

}
