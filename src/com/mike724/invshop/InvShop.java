package com.mike724.invshop;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class InvShop extends JavaPlugin {
	
	private Logger log;
	private PluginDescriptionFile pdf;

	@Override
	public void onDisable() {
		log.info(pdf.getName()+" v"+pdf.getVersion()+" has been disabled.");
	}
	
	@Override
	public void onEnable() {
		File pluginData = this.getDataFolder();
		if(!pluginData.exists()) {
			pluginData.mkdir();
		}
		File dF = new File(pluginData+File.separator+"shops");
		if(!dF.exists()) {
			dF.mkdir();
		}
		InvShopGlobal.shopsFolder = dF;
		InvShopGlobal.plugin = this;
		log = this.getLogger();
		pdf = this.getDescription();
		this.getServer().getPluginManager().registerEvents(new InvShopEvents(this), this);
		InvShopCommands isc = new InvShopCommands();
		this.getCommand("shop").setExecutor(isc);
		log.info(pdf.getName()+" v"+pdf.getVersion()+" has been enabled.");
	}
	

}