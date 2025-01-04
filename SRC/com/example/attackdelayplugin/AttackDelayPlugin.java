package com.example.attackdelayplugin;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class AttackDelayPlugin extends JavaPlugin implements Listener {

    private boolean isAttackDelayDisabled = true;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        if (isAttackDelayDisabled) {
            disableAttackDelayForAllPlayers();
        }
        getLogger().info("AttackDelayPlugin has been enabled!");
    }

    @Override
    public void onDisable() {
        resetAttackDelayForAllPlayers();
        getLogger().info("AttackDelayPlugin has been disabled!");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (isAttackDelayDisabled) {
            disableAttackDelay(event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        resetAttackDelay(event.getPlayer());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("attackdelay")) {
            if (args.length == 0) {
                sender.sendMessage("Usage: /attackdelay <on|off>");
                return true;
            }

            if (args[0].equalsIgnoreCase("on")) {
                isAttackDelayDisabled = true;
                disableAttackDelayForAllPlayers();
                sender.sendMessage("Attack delay has been disabled!");
                return true;
            }

            if (args[0].equalsIgnoreCase("off")) {
                isAttackDelayDisabled = false;
                resetAttackDelayForAllPlayers();
                sender.sendMessage("Attack delay has been enabled!");
                return true;
            }
        }

        return false;
    }

    private void disableAttackDelay(Player player) {
        player.getAttribute(org.bukkit.attribute.Attribute.GENERIC_ATTACK_SPEED).setBaseValue(100);
    }

    private void resetAttackDelay(Player player) {
        player.getAttribute(org.bukkit.attribute.Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4); // Default
    }

    private void disableAttackDelayForAllPlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            disableAttackDelay(player);
        }
    }

    private void resetAttackDelayForAllPlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            resetAttackDelay(player);
        }
    }
}
