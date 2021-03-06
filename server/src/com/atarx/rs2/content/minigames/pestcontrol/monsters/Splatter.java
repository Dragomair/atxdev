package com.atarx.rs2.content.minigames.pestcontrol.monsters;

import com.atarx.core.util.Utility;
import com.atarx.rs2.content.combat.Hit;
import com.atarx.rs2.content.minigames.pestcontrol.Pest;
import com.atarx.rs2.content.minigames.pestcontrol.PestControlConstants;
import com.atarx.rs2.content.minigames.pestcontrol.PestControlGame;
import com.atarx.rs2.entity.Location;
import com.atarx.rs2.entity.player.Player;

public class Splatter extends Pest {

	public Splatter(Location location, PestControlGame game) {
		super(game, PestControlConstants.SPLATTERS[Utility.randomNumber(PestControlConstants.SPLATTERS.length)], location);
	}

	@Override
	public void onDeath() {
		if (Utility.getManhattanDistance(getGame().getVoidKnight().getLocation(), getLocation()) <= 2) {
			getGame().getVoidKnight().hit(new Hit(1 + Utility.randomNumber(5)));
		}

		for (Player k : getGame().getPlayers()) {
			if (Utility.getManhattanDistance(k.getLocation(), getLocation()) <= 2) {
				k.hit(new Hit(1 + Utility.randomNumber(5)));
			}
		}
	}

	@Override
	public void tick() {
		if (Utility.getManhattanDistance(getGame().getVoidKnight().getLocation(), getLocation()) <= 2) {
			getLevels()[3] = 0;
			checkForDeath();
		}
	}

}
