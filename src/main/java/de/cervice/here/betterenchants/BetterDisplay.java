package de.cervice.here.betterenchants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class BetterDisplay {

	public static boolean higherten;
	
	public List<String> fixenchantnumbers(ItemStack item) {
		higherten = false;
		List<String> itemLore = new ArrayList<>();
		Map<Enchantment, Integer> ens;
		if(item.getType() == Material.ENCHANTED_BOOK) {
			EnchantmentStorageMeta esm = (EnchantmentStorageMeta)item.getItemMeta();
			ens = esm.getStoredEnchants();
		} else {
			ItemMeta im = item.getItemMeta();
			ens = im.getEnchants();
		}

		for(Enchantment en : ens.keySet()) {
			if(ens.get(en) > 10)
				higherten = true;
		}
		if(higherten) {
			for(Enchantment en : ens.keySet()) {
				String cen = en.toString();
				String[] cenSplit1 = cen.split(":");
				String acenSplit1 = cenSplit1[1];
				String[] cenSplit2 = acenSplit1.split(",");
				String enchantment = cenSplit2[0];
				String firstletter = enchantment.substring(0,1);
				String enchantmentreplace1 = enchantment.replaceFirst(firstletter, "_");
				String bigfirstletter = firstletter.toUpperCase();
				String enchantmentreplace2 =enchantmentreplace1.replaceFirst("_", bigfirstletter);
				String level = ens.get(en).toString();
				String ename = "§7" + enchantmentreplace2 + " " + level;
				itemLore.add(ename);
				
			}	
		}
		/*ItemStack ebook = new ItemStack(Material.ENCHANTED_BOOK);
		EnchantmentStorageMeta se = (EnchantmentStorageMeta) ebook.getItemMeta();
		se.addEnchant(Enchantment.DAMAGE_ALL, 7, true); */
		//Multimap<Attribute, AttributeModifier> am = se.getAttributeModifiers(); 
		//String am = im.getDisplayName();
		//String amstr = am.toString();
		//Main.getPlugin().getLogger().info(am + "krassfunction");
		//CustomItemTagContainer gct = im.getCustomTagContainer();
		//String gctstr = gct.toString();
		//Main.getPlugin().getLogger().info(gctstr + "function");

		return itemLore;
	}
}
