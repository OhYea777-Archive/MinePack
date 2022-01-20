package com.ohyea777.minepack.autopickup;

import com.ohyea777.minepack.MinePack;
import com.ohyea777.minepack.config.MineConfiguration;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.ItemSpawnEvent;

public class AutoPickup implements Listener {

    private MinePack plugin;
    private YamlConfiguration config;

    public AutoPickup(MinePack plugin) {
        this.plugin = plugin;
        this.config = MineConfiguration.getConfig("autopickup");
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        event.getBlock().setType(Material.AIR);

        event.get
    }

    @EventHandler
    public void onItemSpawn(ItemSpawnEvent event) {
        event.getEntity().getItemStack();
    }

}
