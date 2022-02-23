package de.cervice.here.betterenchants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;




public class Main extends JavaPlugin {
	
	private static Main plugin;
	public static ArrayList<Material> enchItems = new ArrayList<>(Arrays.asList(Material.DIAMOND_SWORD, Material.IRON_SWORD, Material.STONE_SWORD));
	public static ArrayList<Material> enchanter = new ArrayList<>(Arrays.asList(Material.ENCHANTED_BOOK));
	public static HashMap<Enchantment, Integer> betterEnchants = new HashMap<>();
	public static HashMap<Enchantment, Integer> highEnchants = new HashMap<>();

	
	public void onEnable() {
		plugin = this;
		
		
		PluginManager pluginManager = Bukkit.getPluginManager();
		pluginManager.registerEvents(new AnvilListener(), this);
		
	/*	for(Player current : Bukkit.getOnlinePlayers())
			current.sendMessage("§6" + current.getName() + ", §athe BetterEnchantment Plugin is now loaded!");
		getLogger().info("Tada!"); */
		
		betterEnchants.put(Enchantment.DAMAGE_ALL, 6);
		betterEnchants.put(Enchantment.LOOT_BONUS_MOBS, 4);
		betterEnchants.put(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
		betterEnchants.put(Enchantment.ARROW_DAMAGE, 6);
		betterEnchants.put(Enchantment.DIG_SPEED, 6);
		betterEnchants.put(Enchantment.LOOT_BONUS_BLOCKS, 4);
		betterEnchants.put(Enchantment.PROTECTION_FALL, 5);
		betterEnchants.put(Enchantment.DURABILITY, 4);
		
		highEnchants.put(Enchantment.DAMAGE_ALL, 5);
		highEnchants.put(Enchantment.LOOT_BONUS_MOBS, 3);
		highEnchants.put(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
		highEnchants.put(Enchantment.ARROW_DAMAGE, 5);
		highEnchants.put(Enchantment.DIG_SPEED, 5);
		highEnchants.put(Enchantment.LOOT_BONUS_BLOCKS, 3);
		highEnchants.put(Enchantment.PROTECTION_FALL, 4);
		highEnchants.put(Enchantment.DURABILITY, 3);
		
		FileConfiguration config = getConfig();
		if(config.getInt("Max Enchantment Level") == 0)
			config.set("Max Enchantment Level", 15);
		saveConfig();
	}
	
	public static Main getPlugin() {
		return plugin;
	}
	
}
