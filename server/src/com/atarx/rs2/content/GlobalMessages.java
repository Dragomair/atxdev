package com.atarx.rs2.content;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.atarx.core.task.Task;
import com.atarx.core.task.TaskQueue;
import com.atarx.core.util.Utility;
import com.atarx.rs2.entity.World;

/**
 * Handles the global messages
 * @author Daniel
 *
 */
public class GlobalMessages {
	
	/**
	 * The logger for the class
	 */
	private static Logger logger = Logger.getLogger(GlobalMessages.class.getSimpleName());

	/**
	 * The news color text
	 */
	private static String newsColor = "<col=013B4F>";

	/**
	 * The news icon
	 */
	private static String iconNumber = "<img=8>";
	
	/**
	 * Holds all the announcements in a arraylist
	 */
	public final static ArrayList<String> announcements = new ArrayList<String>();

	/**
	 * The random messages that news will send
	 */
	public static final String[] ANNOUNCEMENTS = { 
		"This is a test auto-announcement, please ignore.",
	};
	
	/**
	 * Declares all the announcements
	 */
	public static void declare() {
		for (int i = 0; i < ANNOUNCEMENTS.length; i++) {
			announcements.add(ANNOUNCEMENTS[i]);
		}
		logger.info(Utility.format(announcements.size()) + " Announcements have been loaded successfully.");
	}

	/**
	 * Initializes the task
	 */
	public static void initialize() {
		TaskQueue.queue(new Task(250, false) {
			@Override
			public void execute() {
				final String announcement = Utility.randomElement(announcements);
				World.sendGlobalMessage(iconNumber + newsColor + " " + announcement);
			}

			@Override
			public void onStop() {
			}
		});
	}
	
}
