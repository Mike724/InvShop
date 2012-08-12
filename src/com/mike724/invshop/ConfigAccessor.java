package com.mike724.invshop;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigAccessor {

    private final String fileName;
    private final String defaultFile;
    private final JavaPlugin plugin;
    
    private File configFile;
    private FileConfiguration fileConfiguration;

    public ConfigAccessor(String fileName, String defaultFile) {
    	this.plugin = InvShopGlobal.plugin;
        if (!plugin.isInitialized()) {
            throw new IllegalArgumentException("plugin must be initiaized");
        }
        this.fileName = fileName;
        this.defaultFile = defaultFile;
    }

    public void reloadConfig() {
        if (configFile == null) {
            File dataFolder = InvShopGlobal.shopsFolder;
            if (dataFolder == null)
                throw new IllegalStateException();
            configFile = new File(dataFolder, fileName);
        }
        fileConfiguration = YamlConfiguration.loadConfiguration(configFile);

        // Look for defaults in the jar
        InputStream defConfigStream = plugin.getResource(this.defaultFile);
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            fileConfiguration.setDefaults(defConfig);
        }
    }

    public FileConfiguration getConfig() {
        if (fileConfiguration == null) {
            this.reloadConfig();
        }
        return fileConfiguration;
    }

    public void saveConfig() {
        if (fileConfiguration == null || configFile == null) {
            return;
        } else {
            try {
                getConfig().save(configFile);
            } catch (IOException ex) {
                plugin.getLogger().log(Level.SEVERE, "Could not save config to " + configFile, ex);
            }
        }
    }
    
    public void saveDefaultConfig() {
        if (!configFile.exists()) {            
            this.plugin.saveResource(fileName, false);
        }
    }

}