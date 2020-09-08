package me.ofearr.activity_rewards;

import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Breeding implements Listener {

    FileConfiguration config = Activity_Rewards.plugin.getConfig();

    @EventHandler
    public void EntityBreed(EntityBreedEvent e){
        Player player = (Player) e.getBreeder();
        if(!(player instanceof Player)) return;

        if(config.getString("breeding." + e.getEntity().getType().toString()) == null) {
            return;
        } else {
            for (int i = 0; i < 100; i++) {
                Double chance = config.getDouble("breeding." + e.getEntity().getType().toString() + "." + i + ".chance");
                if (ThreadLocalRandom.current().nextDouble() <= chance) {
                    System.out.println("[ActivityRewards] > [Logger] Dispatching breeding reward commands for " + player.getName() + "!");
                    List<String> messages = config.getStringList("breeding." + e.getEntity().getType().toString() + "." + i + ".messages");
                    for(int m = 0; m < messages.size(); m++){
                        String UMSG = messages.get(m);
                        player.sendMessage(Activity_Rewards.converter(UMSG));
                    }

                    List<String> commands = config.getStringList("breeding." + e.getEntity().getType().toString() + "." + i + ".commands");
                    for (int c = 0; c < commands.size(); c++) {
                        String UCMD = commands.get(c).replace("<player>", player.getName());
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), UCMD);
                    }
                    return;
                }
            }

        }

    }

}
