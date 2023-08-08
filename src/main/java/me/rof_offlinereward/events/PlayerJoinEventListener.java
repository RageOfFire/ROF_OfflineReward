package me.rof_offlinereward.events;

import me.rof_offlinereward.ROF_OfflineReward;
import me.rof_offlinereward.handle.rewardHandler;
import me.rof_offlinereward.utils.TimeConverter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinEventListener implements Listener {
    private ROF_OfflineReward plugin;
    private rewardHandler rewardHandler;

    public PlayerJoinEventListener(ROF_OfflineReward plugin) {
        this.plugin = plugin;
        this.rewardHandler = new rewardHandler(plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        long TimerPLayer = player.getPlayerTime();
        long time = TimeConverter.convertToMilliseconds(plugin.getConfig().getString("min-time-play"));
        if(TimerPLayer >= time || plugin.getConfig().getString("min-time-play") == null) {
            // Run rewards
            rewardHandler.handleRewards(player);
        }
    }
}
