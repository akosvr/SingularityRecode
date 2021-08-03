package theforgtn;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import theforgtn.data.ConfigFile;
import theforgtn.data.PlayerData;

import java.util.Map;
import java.util.WeakHashMap;

public abstract class Actions implements Listener {

    public Map<Player, Integer> violations = new WeakHashMap<>();
    protected String name;
    protected boolean enabled;
    protected boolean punishable;
    protected int max;
    float messageSent = System.currentTimeMillis();
    int setbacl_lvl = 0;

    public Actions(String name, boolean enabled, boolean punishable, int max) {
        this.name = name;
        this.enabled = enabled;
        this.punishable = punishable;
        this.max = max;

        Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
    }


    protected void flag(Player player, String... information) {

        PlayerData data = Main.getInstance().getDataManager().getDataPlayer(player.getPlayer());


        int violations = this.violations.getOrDefault(player, 0) + 1;

        data.violations = violations;

        for (Player staff : Bukkit.getOnlinePlayers()) {
            if (staff.hasPermission("singularity.verbose") && data.violations > 0) {

                staff.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&5⌛&8] &7" + player.getName() + " &7suspected for &c" + name + " &8|&d " + violations + " &8" + data.ping +" ms"));
            }
        }

        if (violations > max && ConfigFile.kick_enabled) {

            player.kickPlayer(ChatColor.translateAlternateColorCodes('&', ConfigFile.kick_message));

        }
        this.violations.put(player, violations);

    }
}