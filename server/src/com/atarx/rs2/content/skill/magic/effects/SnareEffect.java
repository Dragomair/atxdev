package com.atarx.rs2.content.skill.magic.effects;

import com.atarx.rs2.content.combat.impl.CombatEffect;
import com.atarx.rs2.entity.Entity;
import com.atarx.rs2.entity.player.Player;

public class SnareEffect implements CombatEffect {
	@Override
	public void execute(Player p, Entity e) {
		e.freeze(10, 5);
	}
}
