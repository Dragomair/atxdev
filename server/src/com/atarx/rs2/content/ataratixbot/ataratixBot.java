package com.atarx.rs2.content.ataratixbot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import com.atarx.ataratixConstants;
import com.atarx.core.task.Task;
import com.atarx.core.task.TaskQueue;
import com.atarx.core.util.Utility;
import com.atarx.rs2.content.achievements.AchievementHandler;
import com.atarx.rs2.content.achievements.AchievementList;
import com.atarx.rs2.entity.World;
import com.atarx.rs2.entity.player.Player;
import com.atarx.rs2.entity.player.net.out.impl.SendBanner;
import com.atarx.rs2.entity.player.net.out.impl.SendMessage;

/**
 * ataratix Bot - Asks random questions which players can race to answer
 * @author Daniel
 *
 */
public class ataratixBot {
	
	/**
	 * The logger for the class
	 */
	private static Logger logger = Logger.getLogger(ataratixBot.class.getSimpleName());
	
	/**
	 * Holds all the bot data
	 */
	private final static Set<ataratixBotData> BOT_DATA = new HashSet<>();
	
	/**
	 * The current question/answer set
	 */
	private static ataratixBotData current = null;
	
	/*
	 * Holds all the ataratixBot attempted answers
	 */
	public final static ArrayList<String> attempts = new ArrayList<String>();
	
	/**
	 * Color of the ataratixBot messages
	 */
	private static final String COLOR = "<col=8814B3>";
	
	/**
	 * Declares the ataratix data
	 */
	public static void declare() {
		for (ataratixBotData data : ataratixBotData.values()) {
			BOT_DATA.add(data);
		}
		logger.info("Loaded " + BOT_DATA.size() + " ataratixBot questions.");
	}
	
	/**
	 * Initializes the ataratixBot task
	 */
	public static void initialize() {
		TaskQueue.queue(new Task(650, false) {
			@Override
			public void execute() {
				if (current == null) {
					assign();	
					return;
				}
				sendMessage("[" + COLOR + "ataratixBot</col>] " + current.getQuestion());
				sendNotification("[" + COLOR + "ataratixBot</col>] " + current.getQuestion());
			}
			@Override
			public void onStop() {
			}
		});	
	}
	
	/**
	 * Assigns a new question
	 */
	private static void assign() {
		current = Utility.randomElement(BOT_DATA);
		sendMessage("[" + COLOR + "ataratixBot</col>] " + current.getQuestion());
	}
	
	/**
	 * Handles player answering the question
	 * @param player
	 * @param answer
	 */
	public static void answer(Player player, String answer) {
		if (current == null) {
			return;
		}
		for (int i = 0; i < ataratixConstants.BAD_STRINGS.length; i++) {
			if (answer.contains(ataratixConstants.BAD_STRINGS[i])) {
				player.send(new SendMessage("[" + COLOR + "ataratixBot</col>] That was an offensive answer! Contain yourself or be punished."));
				return;
			}
		}
		for (int i = 0; i < current.getAnswers().length; i++) {
			if (current.getAnswers()[i].equalsIgnoreCase(answer)) {
				answered(player, answer);
				return;
			}
		}
		player.send(new SendMessage("[" + COLOR + "ataratixBot</col>] Sorry, the answer you have entered is incorrect! Try again!"));
		attempts.add(answer);
	}
	
	/**
	 * Handles player answering the question successfully 
	 * @param player
	 * @param answer
	 */
	private static void answered(Player player, String answer) {
		sendMessage("[" + COLOR + "ataratixBot</col>] " + COLOR + player.determineIcon(player) + " " + player.getUsername() + "</col> has answered the question correctly! Answer:" + COLOR + " " + Utility.capitalizeFirstLetter(answer) + "</col>.");
		if (attempts.size() > 0) {
			sendMessage("[" + COLOR + "ataratixBot</col>] Attempted answers: " + COLOR + "" + attempts.toString() + "</col>!");
		}
		int REWARD = Utility.random(150_000);
		player.getInventory().addOrCreateGroundItem(995, REWARD, true);
		AchievementHandler.activateAchievement(player, AchievementList.ANSWER_15_TRIVIABOTS_CORRECTLY, 1);
		AchievementHandler.activateAchievement(player, AchievementList.ANSWER_80_TRIVIABOTS_CORRECTLY, 1);
		reset();
	}
	
	/**
	 * Resets the ataratixBot
	 */
	private static final void reset() {
		current = null;
		attempts.clear();
	}
	
	/**
	 * Sends message to server
	 * @param message
	 */
	public static void sendMessage(String message) {
		for (Player players : World.getPlayers()) {
			if (players != null && players.isWantTrivia()) {
				players.send(new SendMessage(message));
			}
		}
	}
	
	/**
	 * Sends notification to server
	 * @param message
	 */
	public static void sendNotification(String message) {
		for (Player players : World.getPlayers()) {
			if (players != null && players.isWantTrivia() && players.isTriviaNotification()) {
				players.send(new SendBanner(message, 0x8E9CA3));
			}
		}
	}

}
