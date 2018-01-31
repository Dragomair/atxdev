package com.atarx.core.task.impl;

import com.atarx.core.cache.map.Door;
import com.atarx.core.task.Task;

public class TickDoorTask extends Task {

	public TickDoorTask(Door door) {
		super(null, 1);
		if (door.original()) {
			stop();
			return;
		}
	}

	@Override
	public void execute() {

		stop();
	}

	@Override
	public void onStop() {
	}

}
