package com.atarx.rs2.content.skill.agility.obstacle.interaction.rooftop.ardougne;

import com.atarx.core.task.Task;
import com.atarx.core.task.TaskQueue;
import com.atarx.rs2.content.skill.agility.obstacle.interaction.ObstacleInteraction;
import com.atarx.rs2.entity.Animation;
import com.atarx.rs2.entity.Location;
import com.atarx.rs2.entity.player.Player;

public interface ArdougneSteepRoofInteraction extends ObstacleInteraction {
	
	@Override
	public default void start(Player player) {
	}
	
	@Override
	public default void onExecution(Player player, Location start, Location end) {
		TaskQueue.queue(new Task(player, 1, true) {
			int ticks = 0;
			
			@Override
			public void execute() {
				switch (ticks++) {
				case 1:
					player.getUpdateFlags().sendFaceToDirection(end);
					player.getUpdateFlags().sendAnimation(new Animation(2586));
					break;
					
				case 2:
					player.teleport(end);
					player.getUpdateFlags().sendAnimation(new Animation(2588));
					stop();
					break;
				}
			}

			@Override
			public void onStop() {
			}
		});
	}
	
	@Override
	public default void onCancellation(Player player) {
		// Climbing has nothing special on cancellation.
	}
}