package com.atarx;

import com.atarx.core.cache.map.MapLoading;
import com.atarx.core.cache.map.ObjectDef;
import com.atarx.core.cache.map.RSInterface;
import com.atarx.core.cache.map.Region;
import com.atarx.core.util.FileHandler;
import com.atarx.core.util.GameDefinitionLoader;
import com.atarx.rs2.content.Emotes;
import com.atarx.rs2.content.FountainOfRune;
import com.atarx.rs2.content.GlobalMessages;
import com.atarx.rs2.content.cluescroll.ClueScrollManager;
import com.atarx.rs2.content.combat.impl.PoisonWeapons;
import com.atarx.rs2.content.combat.special.SpecialAttackHandler;
import com.atarx.rs2.content.dialogue.OneLineDialogue;
import com.atarx.rs2.content.minigames.duelarena.DuelingConstants;
import com.atarx.rs2.content.minigames.godwars.GodWarsData;
import com.atarx.rs2.content.minigames.plunder.PlunderConstants;
import com.atarx.rs2.content.minigames.plunder.PyramidPlunder;
import com.atarx.rs2.content.shopping.Shop;
import com.atarx.rs2.content.skill.Skills;
import com.atarx.rs2.content.skill.agility.Agility;
import com.atarx.rs2.content.skill.cooking.CookingData;
import com.atarx.rs2.content.skill.crafting.Craftable;
import com.atarx.rs2.content.skill.crafting.Glass;
import com.atarx.rs2.content.skill.crafting.HideTanData;
import com.atarx.rs2.content.skill.crafting.Jewelry;
import com.atarx.rs2.content.skill.crafting.Spinnable;
import com.atarx.rs2.content.skill.craftingnew.craftable.impl.Hide;
import com.atarx.rs2.content.skill.farming.Farming;
import com.atarx.rs2.content.skill.firemaking.LogData;
import com.atarx.rs2.content.skill.fishing.FishableData;
import com.atarx.rs2.content.skill.fishing.Fishing;
import com.atarx.rs2.content.skill.fishing.ToolData;
import com.atarx.rs2.content.skill.fletching.fletchable.impl.Arrow;
import com.atarx.rs2.content.skill.fletching.fletchable.impl.Bolt;
import com.atarx.rs2.content.skill.fletching.fletchable.impl.Carvable;
import com.atarx.rs2.content.skill.fletching.fletchable.impl.Crossbow;
import com.atarx.rs2.content.skill.fletching.fletchable.impl.Featherable;
import com.atarx.rs2.content.skill.fletching.fletchable.impl.Stringable;
import com.atarx.rs2.content.skill.herblore.FinishedPotionData;
import com.atarx.rs2.content.skill.herblore.GrimyHerbData;
import com.atarx.rs2.content.skill.herblore.GrindingData;
import com.atarx.rs2.content.skill.herblore.UnfinishedPotionData;
import com.atarx.rs2.content.skill.magic.MagicConstants;
import com.atarx.rs2.content.skill.magic.MagicEffects;
import com.atarx.rs2.content.skill.mining.Mining;
import com.atarx.rs2.content.skill.prayer.BoneBurying;
import com.atarx.rs2.content.skill.ranged.AmmoData;
import com.atarx.rs2.content.skill.runecrafting.RunecraftingData;
import com.atarx.rs2.content.skill.slayer.SlayerMonsters;
import com.atarx.rs2.content.skill.smithing.SmeltingData;
import com.atarx.rs2.content.skill.thieving.ThievingNpcData;
import com.atarx.rs2.content.skill.thieving.ThievingStallData;
import com.atarx.rs2.content.skill.woodcutting.WoodcuttingAxeData;
//import com.atarx.rs2.content.ataratixbot.ataratixBot;
import com.atarx.rs2.entity.item.EquipmentConstants;
import com.atarx.rs2.entity.item.impl.GlobalItemHandler;
import com.atarx.rs2.entity.mob.Mob;
import com.atarx.rs2.entity.mob.MobAbilities;
import com.atarx.rs2.entity.mob.MobConstants;
import com.atarx.rs2.entity.object.ObjectConstants;
import com.atarx.rs2.entity.object.ObjectManager;
import com.atarx.rs2.entity.player.net.in.PacketHandler;

/**
 * Loads all of the neccessary game data
 * 
 * @author Michael Sasse
 * 
 */
public class GameDataLoader {

	/**
	 * The stage of the game server
	 */
	private static int stage = 0;

	/**
	 * Loads all of the game data
	 */
	public static void load() {
		try {
			GameDefinitionLoader.declare();
			GlobalMessages.declare();
			//ataratixBot.declare();
			new Thread() {
				@Override
				public void run() {
					try {
						ObjectDef.loadConfig();
						ObjectConstants.declare();
						MapLoading.load();
						Region.sort();
						GameDefinitionLoader.loadAlternateIds();
						MapLoading.processDoors();
						GameDefinitionLoader.clearAlternates();
						ObjectManager.declare();
						GlobalItemHandler.spawnGroundItems();
						Mob.spawnBosses();
						GameDefinitionLoader.loadNpcSpawns();
						//GlobalMessages.initialize();
						//ataratixBot.initialize();
					} catch (Exception e) {
						e.printStackTrace();
					}

					GameDataLoader.stage += 1;
				}
			}.start();

			RSInterface.unpack();
			GameDefinitionLoader.loadNpcDefinitions();
			GameDefinitionLoader.loadItemDefinitions();
			GameDefinitionLoader.loadRareDropChances();	
			GameDefinitionLoader.loadEquipmentDefinitions();
			GameDefinitionLoader.loadShopDefinitions();
			GameDefinitionLoader.setRequirements();
			GameDefinitionLoader.loadWeaponDefinitions();
			GameDefinitionLoader.loadSpecialAttackDefinitions();
			GameDefinitionLoader.loadRangedStrengthDefinitions();
			GameDefinitionLoader.loadSpecialAttackDefinitions();
			GameDefinitionLoader.loadCombatSpellDefinitions();
			GameDefinitionLoader.loadFoodDefinitions();
			GameDefinitionLoader.loadPotionDefinitions();
			GameDefinitionLoader.loadRangedWeaponDefinitions();
			GameDefinitionLoader.loadNpcCombatDefinitions();
			GameDefinitionLoader.loadNpcDropDefinitions();
			GameDefinitionLoader.loadItemBonusDefinitions();			
			GodWarsData.declare();			
			Mining.declare();			
			PyramidPlunder.declare();
			PlunderConstants.UrnBitPosition.declare();
			PlunderConstants.DoorBitPosition.declare();		
			ClueScrollManager.declare();
			FountainOfRune.declare();
			Agility.declare();			
			Arrow.declare();
			Bolt.declare();
			Carvable.declare();
			Crossbow.declare();
			Featherable.declare();
			Stringable.declare();
			Craftable.declare();
			HideTanData.declare();
			Jewelry.declare();
			Spinnable.declare();		
			com.atarx.rs2.content.skill.craftingnew.craftable.impl.Gem.declare();
			Hide.declare();		
			Farming.declare();
			Shop.declare();
			MagicConstants.declare();
			SlayerMonsters.declare();
			DuelingConstants.declare();
			MobConstants.declare();
			Emotes.declare();
			PoisonWeapons.declare();
			SpecialAttackHandler.declare();
			CookingData.declare();
			Glass.declare();
			LogData.declare();
			FishableData.Fishable.declare();
			Fishing.FishingSpots.declare();
			ToolData.Tools.declare();
			FinishedPotionData.declare();
			GrimyHerbData.declare();
			GrindingData.declare();
			UnfinishedPotionData.declare();
			MagicEffects.declare();
			BoneBurying.Bones.declare();
			AmmoData.Ammo.declare();
			RunecraftingData.declare();
			Skills.declare();
			ThievingNpcData.declare();
			ThievingStallData.declare();
			WoodcuttingAxeData.declare();
			EquipmentConstants.declare();
			PacketHandler.declare();
			MobConstants.MobDissapearDelay.declare();
			MobAbilities.declare();
			SmeltingData.declare();
			OneLineDialogue.declare();
			FileHandler.load();
			stage += 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets if the server has been successfully loaded
	 * 
	 * @return
	 */
	public static boolean loaded() {
		return stage == 2;
	}
}
