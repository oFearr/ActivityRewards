package me.ofearr.activity_rewards;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public final class Activity_Rewards extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        loadconfig();
        Bukkit.getServer().getPluginManager().registerEvents(this, this);

    }

    private void loadconfig() {
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    //Mining
    @EventHandler
    public void onPlayerMineBlock(BlockBreakEvent e){
        Player player = e.getPlayer();
        player.sendMessage(e.getBlock().getType().toString());
        if(getConfig().getString("mining." + e.getBlock().getType().toString()) == null){
            return;
        }else{
                for(int i = 0; i < 100; i++){
                    Double chance = getConfig().getDouble("mining." + e.getBlock().getType().toString() + "." + i + ".chance");
                    if (ThreadLocalRandom.current().nextDouble() <= chance) {
                        List<String> commands = getConfig().getStringList("mining." + e.getBlock().getType().toString() + "." + i + ".commands");
                        for(int c = 0; c < commands.size(); c++){
                            String UCMD = commands.get(c).replace("<player>", player.getName());
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), UCMD);
                        }
                        return;
                    }
                }


        }

    }

    //Slaying
    @EventHandler
    public void PlayerSlayEntityEvent(EntityDeathEvent e){
        if(!(e.getEntity().getKiller() instanceof Player)) return;
        Player player = e.getEntity().getKiller().getPlayer();
        if(getConfig().getString("slaying." + e.getEntity().getType().toString()) == null) {
            return;
        } else {
            for (int i = 0; i < 100; i++) {
                Double chance = getConfig().getDouble("slaying." + e.getEntity().getType().toString() + "." + i + ".chance");
                if (ThreadLocalRandom.current().nextDouble() <= chance) {
                    List<String> commands = getConfig().getStringList("slaying." + e.getEntity().getType().toString() + "." + i + ".commands");
                    for (int c = 0; c < commands.size(); c++) {
                        String UCMD = commands.get(c).replace("<player>", player.getName());
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), UCMD);
                    }
                    return;
                }
            }

        }


    }

/*
    //Breeding
    @EventHandler
    public void EntityBreed(EntityBreedEvent e){
        Player player = (Player) e.getBreeder();
        if(!(player instanceof Player)) return;

        if(getConfig().getString("breeding." + e.getEntity().getType().toString()) == null) {
            return;
        } else {
                for (int i = 0; i < 100; i++) {
                    Double chance = getConfig().getDouble("breeding." + e.getEntity().getType().toString() + "." + i + ".chance");
                    if (ThreadLocalRandom.current().nextDouble() <= chance) {
                        List<String> commands = getConfig().getStringList("breeding." + e.getEntity().getType().toString() + "." + i + ".commands");
                        for (int c = 0; c < commands.size(); c++) {
                            String UCMD = commands.get(c).replace("<player>", player.getName());
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), UCMD);
                        }
                        return;
                    }
                }


        }


    } */

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().toLowerCase().equals("reloadrewards")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (!(player.hasPermission("activityrewards.reload"))) {
                    player.sendMessage("&cInsufficent permissions!");
                } else {
                    reloadConfig();
                    player.sendMessage(ChatColor.GREEN + "Config successfully reloaded!");
                    System.out.println(ChatColor.GREEN + "[ActivityRewards] " + player.getName() + " successfully reloaded config!");
                }
            }
        }
        return false;
    }



    @EventHandler
    public void Farm(BlockBreakEvent e){
        Player player = e.getPlayer();
        Block crop = e.getBlock();

        if(getConfig().getString("farming." + crop.getType().toString()) == null) {
            return;
        } else {
                for (int i = 0; i < 100; i++) {
                    Double chance = getConfig().getDouble("farming." + crop.getType().toString() + "." + i + ".chance");
                    if (ThreadLocalRandom.current().nextDouble() <= chance) {
                        List<String> commands = getConfig().getStringList("farming." + crop.getType().toString() + "." + i + ".commands");
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
