package me.rof_offlinereward.commands;

import me.rof_offlinereward.ROF_OfflineReward;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandExecutor implements org.bukkit.command.CommandExecutor {
    private final ROF_OfflineReward plugin;
    public CommandExecutor(ROF_OfflineReward plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        String reloadmess = plugin.getConfig().getString("messages.reload");
        String usagemess = plugin.getConfig().getString("messages.usage");
        if(strings.length == 0) {
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', usagemess));
            return true;
        }
        if(strings[0].toLowerCase().equalsIgnoreCase("reload")) {
            if(commandSender.hasPermission("rofow.admin")) {
                plugin.reloadConfig();
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', reloadmess));
                return true;
            }
        }
        return false;
    }
}
