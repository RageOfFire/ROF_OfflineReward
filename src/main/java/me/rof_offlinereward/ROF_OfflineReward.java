package me.rof_offlinereward;

import me.rof_offlinereward.commands.CommandTabCompletion;
import me.rof_offlinereward.commands.CommandExecutor;
import me.rof_offlinereward.events.PlayerJoinEventListener;
import me.rof_offlinereward.handle.rewardHandler;
import me.rof_offlinereward.utils.FileLog;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public final class ROF_OfflineReward extends JavaPlugin {
    private rewardHandler rewardHandler;
    private FileLog fileLog;

    @Override
    public void onEnable() {

        rewardHandler = new rewardHandler(this);
        fileLog = new FileLog(this);

        // Bstats config
        int pluginId = 19429; // <-- Replace with the id of your plugin!
        Metrics metrics = new Metrics(this, pluginId);

        //Setup Config
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        // Listener
        getServer().getPluginManager().registerEvents(new PlayerJoinEventListener(this), this);
        // Commands
        getCommand("rofofflinereward").setExecutor(new CommandExecutor(this));
        getCommand("rofofflinereward").setTabCompleter(new CommandTabCompletion());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
