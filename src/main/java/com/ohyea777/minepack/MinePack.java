package com.ohyea777.minepack;

import com.ohyea777.minepack.autopickup.AutoPickup;
import com.ohyea777.minepack.util.reflection.CraftItemStack;
import com.ohyea777.minepack.util.reflection.MinecraftItemStack;
import com.ohyea777.minepack.util.reflection.MojangsonParser;
import com.ohyea777.minepack.util.reflection.NBTTagCompound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class MinePack extends JavaPlugin {

    private static MinePack instance;

    private AutoPickup autoPickup;

    @Override
    public void onEnable() {
        instance = this;
        autoPickup = new AutoPickup(instance);
    }

    public static MinePack getInstance() {
        return instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length == 1) {
                NBTTagCompound tagCompound = MojangsonParser.parse(args[0]);
                MinecraftItemStack minecraftItemStack = MinecraftItemStack.createStack(tagCompound);

                player.getInventory().addItem(CraftItemStack.asCraftMirror(minecraftItemStack));
            } else {
                MinecraftItemStack minecraftItemStack = CraftItemStack.asNMSCopy(player.getItemInHand());
                NBTTagCompound tagCompound = new NBTTagCompound();

                minecraftItemStack.save(tagCompound);
                sender.sendMessage(tagCompound.toString());
            }
        }

        return false;
    }
}
