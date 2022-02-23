package de.cervice.here.betterenchants;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class AnvilListener implements Listener {
	public static ItemStack result;
	public static boolean check;
	public static ItemStack b;
	public static EnchantmentStorageMeta sm;
	public static ItemMeta resultMeta;
	public static AnvilInventory anvilInv;
	public static int neededexp;
	public static boolean capreached;
	
	@EventHandler
	public void prepareAnvil(PrepareAnvilEvent e) {
		FileConfiguration config = Main.getPlugin().getConfig();
		int enchantcap = config.getInt("Max Enchantment Level");
		AnvilListener.result = e.getResult();
		//AnvilListener.checker = false;
		anvilInv = (AnvilInventory) e.getInventory();
		anvilInv.remove(Material.AIR);
		ItemStack[] canvil = anvilInv.getContents();
		ArrayList<ItemStack> canvilInv = new ArrayList<>();
		canvilInv.add(canvil[0]);
		canvilInv.add(canvil[1]);
		canvilInv.remove(null);
		canvilInv.remove(null);
		int size = canvilInv.size();

		if(size > 1) {
			boolean check = false;
			ItemStack slot1 = canvilInv.get(0);
			ItemStack slot2 = canvilInv.get(1);
			resultMeta = AnvilListener.result.getItemMeta();
			if(resultMeta == null)
				resultMeta = slot1.getItemMeta();
			ItemMeta slot1Meta = slot1.getItemMeta();
			ItemMeta slot2Meta = slot2.getItemMeta();
			Map<Enchantment, Integer> slot1Ench;
			Map<Enchantment, Integer> slot2Ench;
			if(slot1.getType() == Material.ENCHANTED_BOOK) {
				EnchantmentStorageMeta slot1MetaEnch = (EnchantmentStorageMeta)slot1.getItemMeta();
				slot1Ench = slot1MetaEnch.getStoredEnchants();
			} else {
				slot1Ench = slot1Meta.getEnchants();
			}
			if(slot2.getType() == Material.ENCHANTED_BOOK) {
				EnchantmentStorageMeta slot2MetaEnch = (EnchantmentStorageMeta)slot2.getItemMeta();
				slot2Ench = slot2MetaEnch.getStoredEnchants();
			} else {
				slot2Ench = slot2Meta.getEnchants();
			}
			HashMap<Enchantment, Integer> slot12Ench = new HashMap<>();
			slot12Ench.putAll(slot1Ench);
			slot12Ench.putAll(slot2Ench);
			for(Enchantment enchcheck : slot12Ench.keySet()) {
				if(Main.betterEnchants.containsKey(enchcheck)) {
					int EnchLevel = slot12Ench.get(enchcheck);
					if(EnchLevel >= Main.betterEnchants.get(enchcheck)) {
						check = true;
						//AnvilListener.checker = true;
						
					}
				}
				if(Main.highEnchants.containsKey(enchcheck)) {
					if(slot1Ench.containsKey(enchcheck) && slot2Ench.containsKey(enchcheck)) {
						int EnchLevel2 = slot12Ench.get(enchcheck);
						if(EnchLevel2 >= Main.highEnchants.get(enchcheck)) 
							check = true;
					}
				}
			}
			if(check) {
				for(Enchantment ench1Port : slot1Ench.keySet()) {
					int levelslot1Ench = slot1Ench.get(ench1Port);
					resultMeta.addEnchant(ench1Port, levelslot1Ench, true);
				}
			}
			if(check) {
				for(Enchantment ench2Port: slot2Ench.keySet()) {
					if(!slot1Ench.containsKey(ench2Port)) {
						int levelslot2Ench = slot2Ench.get(ench2Port);
						resultMeta.addEnchant(ench2Port, levelslot2Ench, true);				
					}
				}
			}
			for(Enchantment ench : slot12Ench.keySet()) {
				if(check) {
					if(Main.highEnchants.containsKey(ench) || Main.betterEnchants.containsKey(ench)) {
						if(slot1Ench.containsKey(ench) && slot2Ench.containsKey(ench)) {
							int slot1EnchLevel = slot1Ench.get(ench);
							int slot2EnchLevel = slot2Ench.get(ench);
							if(slot1EnchLevel == slot2EnchLevel) {
								int resultEnchLevel = slot1EnchLevel + 1;
								if(resultEnchLevel > enchantcap)
									capreached = true;
								if(resultEnchLevel <= enchantcap)
									capreached = false;
								resultMeta.removeEnchant(ench);
								resultMeta.addEnchant(ench, resultEnchLevel, true);
							}
							if(slot1EnchLevel < slot2EnchLevel) {
								resultMeta.removeEnchant(ench);
								resultMeta.addEnchant(ench, slot2EnchLevel, true);
							}
						}	
					}
				}
			}
			if(check) {	
				if(slot1.getType() == Material.ENCHANTED_BOOK && slot2.getType() == Material.ENCHANTED_BOOK) {
					  b = new ItemStack(Material.ENCHANTED_BOOK);
					  sm = (EnchantmentStorageMeta) result.getItemMeta();
						Map<Enchantment, Integer> enchsBook = resultMeta.getEnchants();
						//Main.getPlugin().getLogger().info("Result: " + sm);
						for(Enchantment enchant : enchsBook.keySet()) {
							int blevel = enchsBook.get(enchant);
							if(blevel > enchantcap)
								capreached = true;
							if(blevel <= enchantcap)
								capreached = false;
							sm.addStoredEnchant(enchant, blevel, true);
							b.setItemMeta(sm);
							BetterDisplay fix = new BetterDisplay();
							List<String> lore = fix.fixenchantnumbers(b);
							sm.setLore(lore);
							if(BetterDisplay.higherten)
								sm.addItemFlags(ItemFlag.values());
							b.setItemMeta(sm);
							e.setResult(b);
						}
				} else {		
					result.setItemMeta(resultMeta);
					BetterDisplay fix = new BetterDisplay();
					List<String> lore = fix.fixenchantnumbers(result);
					resultMeta.setLore(lore);
					if(BetterDisplay.higherten)
						resultMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					result.setItemMeta(resultMeta);
					e.setResult(result);
					//Main.getPlugin().getLogger().info("Result: " + resultMeta);
				}
			}
		}
	}
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if(e.getWhoClicked() instanceof Player) {
			if(e.getClickedInventory() != null) {
				if(e.getClickedInventory().getType() == InventoryType.ANVIL) {
					if(capreached) {
						if(e.getCurrentItem().isSimilar(b) || e.getCurrentItem().isSimilar(result)) {
							Player player = (Player) e.getWhoClicked();
							/*int currentexp = player.getLevel();
							if(currentexp < neededexp) {
								player.sendMessage("§8You don´t have enough EXP.");
								int diffexp = neededexp - currentexp; */
								//player.sendMessage("§c There are §6" + diffexp + " §cneeded!");
							if(capreached) {
								e.setCancelled(true);
								player.sendMessage("§cEnchantCap reached!");
							}
						}
					}
				}
			}
		}
	}
}