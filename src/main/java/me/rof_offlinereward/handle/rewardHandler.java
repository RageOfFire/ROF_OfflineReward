package me.rof_offlinereward.handle;

import me.rof_offlinereward.ROF_OfflineReward;
import me.rof_offlinereward.utils.FileLog;
import me.rof_offlinereward.utils.TimeConverter;
import org.bukkit.Bukkit;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class rewardHandler {
    private ROF_OfflineReward plugin;
    private FileLog fileLog;

    public rewardHandler(ROF_OfflineReward plugin) {
        this.plugin = plugin;
        this.fileLog = new FileLog(plugin);
    }

    public void handleRewards(Player player) {
        if (player.hasPermission("rofow.exempt")) {
            return;
        }
        for (String customId : plugin.getConfig().getConfigurationSection("rewards").getKeys(false)) {
            String permission = plugin.getConfig().getString("rewards." + customId + ".permission");
            String executeAs = plugin.getConfig().getString("rewards." + customId + ".execute");
            String timeString = plugin.getConfig().getString("rewards." + customId + ".time");
            String alertMessage = plugin.getConfig().getString("messages.alert")
                    .replace("%player%", player.getName())
                    .replace("%time%", timeString)
                    .replace("%reward%", customId);
            long time = TimeConverter.convertToMilliseconds(plugin.getConfig().getString("rewards." + customId + ".time"));

            if (player.hasPermission(permission) || permission == null) {
                long lastLogin = plugin.getServer().getOfflinePlayer(player.getUniqueId()).getLastPlayed();
                long offlineTime = System.currentTimeMillis() - lastLogin;

                if (offlineTime >= time) {
                    // Alert to console
                    if (plugin.getConfig().getBoolean("logs.console")) {
                        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', alertMessage));
                    }
                    // Alert to everyone have permission
                    if (plugin.getConfig().getBoolean("logs.player")) {
                        for (Player online : Bukkit.getOnlinePlayers()) {
                            if (online.hasPermission("rofow.alert")) {
                                online.sendMessage(ChatColor.translateAlternateColorCodes('&', alertMessage));
                            }
                        }
                    }
                    // Alert to file
                    if (plugin.getConfig().getBoolean("logs.file")) {
                        fileLog.LogToFile(alertMessage);
                    }
                    List<String> commands = plugin.getConfig().getStringList("rewards." + customId + ".commands");
                    for (String command : commands) {
                        String formattedCommand = command.replace("%player%", player.getName());
                        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
                            formattedCommand = PlaceholderAPI.setPlaceholders(player, formattedCommand);
                        }
                        if("CONSOLE".equalsIgnoreCase(executeAs)) {
                            plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), formattedCommand);
                        }
                        if("PLAYER".equalsIgnoreCase(executeAs)) {
                            plugin.getServer().dispatchCommand(player, formattedCommand);
                        }
                    }
                    String message = plugin.getConfig().getString("rewards." + customId + ".message");
                    if (message != null && !message.isEmpty()) {
                        String formattedMessage = message.replace("%player%", player.getName());
                        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
                            formattedMessage = PlaceholderAPI.setPlaceholders(player, formattedMessage);
                        }
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', formattedMessage));
                    }
                }
            }
        }
    }
}
