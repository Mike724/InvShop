package com.mike724.invshop;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import com.mike724.invshop.exceptions.ShopNotFoundException;

public class InvShopEvents implements Listener {
	
	private InvShop plugin;
	
	public InvShopEvents(InvShop invshop) {
		plugin = invshop;
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if(e.isCancelled()) {
			return;
		}
		Material type = e.getClickedBlock().getType();
		if((type==Material.SIGN_POST || type==Material.WALL_SIGN) && e.getAction()==Action.RIGHT_CLICK_BLOCK) {
			BlockState bs = e.getClickedBlock().getState();
			Sign sign = (Sign) bs;
			if(sign.getLine(0).endsWith("[Shop]")) {
				int id = -1;
				try {
					id = Integer.parseInt(sign.getLine(3));
				} catch(NumberFormatException ex) {
					ex.printStackTrace();
					return;
				}
				Shop shop;
				try {
					shop = new Shop(id);
				} catch (ShopNotFoundException e1) {
					e.getPlayer().sendMessage(ChatColor.RED+"SHOP NOT FOUND! ALERT ADMIN");
					e1.printStackTrace();
					return;
				}
				if(!e.getPlayer().isSneaking()) {
					String[] pages = new String[1];
					pages[0] = "Hacker!";
					Book book = new Book("50 Coins","§f6 In Stock", pages);
					Inventory inv = plugin.getServer().createInventory(null, 18, String.format("~ %s's Shop", shop.getOwnerName()));
					for(int i=9;i<18;i++) {
						inv.setItem(i, book.generateItemStack());
					}
					e.getPlayer().openInventory(inv);
					e.getPlayer().sendMessage("Opened shop");
				} else {
					Inventory inv = plugin.getServer().createInventory(null, 54, "~ Editing");
					e.getPlayer().openInventory(inv);
					e.getPlayer().sendMessage("Editing shop");
				}
			}
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if(!e.isCancelled()) {
			if(e.getRawSlot()>8 && e.getRawSlot()<19) {
				String title = e.getInventory().getTitle();
				if(title.startsWith("~") && title.endsWith("Shop")) {
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onSignChange(SignChangeEvent e) {
		if(!e.isCancelled()) {
			if(e.getLine(0).equalsIgnoreCase("[Shop]")) {
				Shop s = new Shop(e.getPlayer());
				e.setLine(0, "   §1[Shop]");
				e.setLine(2, s.getOwnerName());
				e.setLine(3, String.valueOf(s.getShopID()));
				Player p = e.getPlayer();
				p.sendMessage(ChatColor.GREEN+"Sneak and right click to edit!");
			}
		}
	}
	
	 @EventHandler
	 public void onBlockPlace(BlockPlaceEvent e) {
		 if(!e.isCancelled()) {
			 Block b = e.getBlockAgainst();
			 if(b.getType()==Material.SIGN_POST||b.getType()==Material.WALL_SIGN) {
				 Sign s = (Sign)b.getState();
				 if(s.getLine(0).endsWith("[Shop]")) {
					 e.setCancelled(true);
				 }
			 }
		 }
		 
	 }

}
