package com.ohyea777.minepack.config;

import com.ohyea777.minepack.MinePack;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MineConfiguration {

    private static Map<String, YamlConfiguration> configs = new HashMap<String, YamlConfiguration>();

    static {
        configs.put("config", loadConfiguration("config.yml"));
        configs.put("autopickup", loadConfiguration("autopickup.yml"));
    }

    public static YamlConfiguration loadConfiguration(String configName) {
        File file = null;

        if (MinePack.getInstance().getResource(configName) != null) {
            MinePack.getInstance().saveResource(configName, false);

            file = new File(MinePack.getInstance().getDataFolder(), configName);
        } else {
            file = new File(MinePack.getInstance().getDataFolder(), configName);

            if (!file.exists()) {
                file.getParentFile().mkdirs();

                try {
                    file.createNewFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return YamlConfiguration.loadConfiguration(file);
    }

    public static void reloadConfigs() {
        configs.clear();
        configs.put("config", loadConfiguration("config.yml"));
        configs.put("autopickup", loadConfiguration("autopickup.yml"));
    }

    public static YamlConfiguration getConfig(String configName) {
        return configs.get(configName);
    }

}
