package com.atarx.rs2.content.combat.impl;

import com.atarx.rs2.entity.Entity;
import com.atarx.rs2.entity.player.Player;

public abstract interface CombatEffect {

	public abstract void execute(Player paramPlayer, Entity paramEntity);
}
