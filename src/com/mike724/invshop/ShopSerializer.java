package com.mike724.invshop;

import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ShopSerializer {
	public static String serialize(Inventory inv) {
		String f = inv.getSize()+"!";
		for(ItemStack is : inv.getContents()) {
			if(is.getType()!=Material.WRITTEN_BOOK) {
				f += getBasicSection(is);
				Map<Enchantment,Integer> enchs = is.getEnchantments();
				if(!enchs.isEmpty()) {
					f += "e#";
					for(Entry<Enchantment,Integer> ench : enchs.entrySet()) {
						f += getEnchantmentSection(ench.getKey(),ench.getValue())+"$";
					}
					f = f.substring(0,f.lastIndexOf("$")-1);
				}
			} else {
				
			}
			f += "&";
		}
		return f.substring(0, f.length()-1);
	}
	public static Inventory deserialize(String s) {
		String size = s.substring(0,s.indexOf("!")-1);
		Inventory inv = Bukkit.getServer().createInventory(null, Integer.parseInt(size));
		String[] istacks = s.split("&");
		for(int i=0;i<istacks.length;i++) {
			ItemStack is = deserializeBasicSection(istacks[i]);
			if(istacks[i].contains("e#")) {
				String[] enchs = istacks[i].substring(istacks[i].indexOf("e#")).split("$");
				for(String ench : enchs) {
					String[] ed = ench.split(",");
					if(ed.length==2) {
						is.addEnchantment(Enchantment.getById(Integer.parseInt(ed[0])), Integer.parseInt(ed[1]));
					}
				}
			}
			
		}
		return inv;
	}
	private static String getBasicSection(ItemStack is) {
		String f = is.getTypeId()+",";
		f += is.getDurability()+",";
		f += is.getAmount();
		return f;
	}
	private static ItemStack deserializeBasicSection(String s) {
		String[] d = s.split(",");
		ItemStack is = new ItemStack(0);
		for(int i=0;i<d.length;i++) {
			switch(i) {
				case 0: is.setTypeId(Integer.parseInt(d[i]));
						break;
				case 1: is.setDurability(Short.parseShort(d[i]));
						break;
				case 2: is.setAmount(Integer.parseInt(d[i]));
						break;
				default: break;
			}
		}
		return is;
	}
	private static String getEnchantmentSection(Enchantment e, Integer i) {
		String f = e.getId()+",";
		f+= (int)i;
		return f;
	}
}
