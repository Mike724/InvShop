package com.mike724.invshop;

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
		log = this.getLogger();
		pdf = this.getDescription();
		this.getServer().getPluginManager().registerEvents(new InvShopEvents(this), this);
		log.info(pdf.getName()+" v"+pdf.getVersion()+" has been enabled.");
	}

}