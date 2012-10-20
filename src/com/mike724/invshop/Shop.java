package com.mike724.invshop;

import java.io.File;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.mike724.invshop.exceptions.ShopNotFoundException;

public class Shop {
	
	private int shopID = -1;
	private String ownerName = null;
	private ConfigAccessor configA = null;
	private FileConfiguration config = null;
	
	//Create shop
	public Shop(Player owner) {
		this.shopID = ShopUtil.getHighestID()+1;
		configA = new ConfigAccessor(String.valueOf(this.shopID).concat(".yml"),"shop.yml");
		config = configA.getConfig();
		this.ownerName = owner.getName();
		config.set("owner", ownerName);
		configA.saveConfig();
	}
	public Shop(int id) throws ShopNotFoundException {
		File[] files = InvShopGlobal.shopsFolder.listFiles(new ShopFilter(id+".",null,null));
		if(files.length==0) {
			throw new ShopNotFoundException("A shop with ID "+id+" could not be found.");
		}
		configA = new ConfigAccessor(String.valueOf(id).concat(".yml"), "shop.yml");
		config = configA.getConfig();
		this.shopID = id;
		this.ownerName = config.getString("owner");
	}
	public int getShopID() {
		return shopID;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public Player getOwnerPlayer() {
		return Bukkit.getPlayerExact(ownerName);
	}
	public void saveInventory(Inventory inv) {
		Iterator<ItemStack> it = inv.iterator();
		int i = 0;
		while(it.hasNext()) {
			ItemStack is = it.next();
			config.set("inv."+i, is.serialize());
			i++;
		}
	}

}
