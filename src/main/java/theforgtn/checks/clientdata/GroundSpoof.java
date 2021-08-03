package theforgtn.checks.clientdata;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;
import theforgtn.Actions;
import theforgtn.Main;
import theforgtn.data.ConfigFile;
import theforgtn.data.PlayerData;

import static java.lang.Math.abs;
import static theforgtn.data.ConfigFile.cancel_enabled;
import static theforgtn.data.ConfigFile.cancel_vl;

public class GroundSpoof extends Actions {

    public GroundSpoof(String name, boolean enabled, boolean punishable, int max) {
        super(name, enabled, punishable, max);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onMove(PlayerMoveEvent event) {
        PlayerData data = Main.getInstance().getDataManager().getDataPlayer(event.getPlayer());
        Material m = event.getPlayer().getLocation().subtract(0, 1, 0).getBlock().getType();

        if (abs(abs(event.getPlayer().getLocation().getBlockY()) - data.USP_Y) > 2) {

            if (data.ground != data.clientGround && data.deltaY < 0.01) {

                flag(event.getPlayer());

                if (data.GSP_damage < ((event.getPlayer().getLocation().getY() - event.getPlayer().getWorld().getHighestBlockYAt(event.getPlayer().getLocation())) - 3)) {

                    data.GSP_damage = ((event.getPlayer().getLocation().getY() - event.getPlayer().getWorld().getHighestBlockYAt(event.getPlayer().getLocation())) - 3);

                }

                if (ConfigFile.GSP_setback) {

                        event.getPlayer().teleport(new Location(event.getPlayer().getWorld(), data.USP_X, data.USP_Y, data.USP_Z, data.USP_YAW, data.USP_PITCH));

                }
                if(ConfigFile.GSP_damage){
                    event.getPlayer().damage(data.GSP_damage);
                }
            }
        }

    }
}
