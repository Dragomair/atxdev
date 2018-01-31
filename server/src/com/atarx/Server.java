package com.atarx;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Logger;

import com.atarx.core.GameThread;
//import com.atarx.core.network.mysql.HiscoreUpdater;
//import com.atarx.core.network.mysql.MembershipRewards;
//import com.atarx.core.network.mysql.VoteUpdater;
//import com.atarx.core.util.logger.PlayerLogger;
import com.atarx.rs2.content.clanchat.ClanManager;
//import com.atarx.rs2.content.io.PlayerSave;
//import com.atarx.rs2.entity.World;
//import com.atarx.rs2.entity.player.Player;


/**
 * Initializes the server
 * 
 * @author Michael Sasse
 * 
 */
public class Server {

	/** 
	 * The logger for printing information.
	 */
	private static Logger logger = Logger.getLogger(Server.class.getSimpleName());

	/**
	 * Handles the clan chat.
	 */
	public static ClanManager clanManager = new ClanManager();

	/**
	 * Gets the ataratix time
	 */
	public static String ataratixTime() {
		return new SimpleDateFormat("HH:mm aa").format(new Date());
	}

	/**
	 * Gets the server date
	 */
	public static String ataratixDate() {
		return new SimpleDateFormat("EEEE MMM dd yyyy ").format(new Date());
	}

	/**
	 * Gets the server uptime
	 * 
	 * @return
	 */
	public static String getUptime() {
		RuntimeMXBean mx = ManagementFactory.getRuntimeMXBean();
		DateFormat df = new SimpleDateFormat("DD 'D', HH 'H', mm 'M'");
		df.setTimeZone(TimeZone.getTimeZone("GMT+0"));
		return "" + df.format(new Date(mx.getUptime()));
	}

	/**
	 * The main method of the server that initializes everything
	 * 
	 * @param args
	 *            The startup arguments
	 */
	public static void main(String[] args) {
		if (args != null && args.length > 0) {
			ataratixConstants.DEV_MODE = Boolean.valueOf(args[0]);
		}
		
		logger.info("Development mode: " + (ataratixConstants.DEV_MODE ? "Online" : "Offline") + ".");
		logger.info("Staff mode: " + (ataratixConstants.STAFF_ONLY ? "Online" : "Offline") + ".");

		/*if (!ataratixConstants.DEV_MODE) {
			try {
				MembershipRewards.prepare();
				HiscoreUpdater.prepare();
				VoteUpdater.prepare();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			Runtime.getRuntime().addShutdownHook(new Thread(() -> {
				for (Player players : World.getPlayers()) {
					if (players != null && players.isActive()) {
						PlayerSave.save(players);
					}
				}

				MembershipRewards.shutdown();
				HiscoreUpdater.shutdown();
				VoteUpdater.shutdown();

				PlayerLogger.SHUTDOWN_LOGGER.log("Logs", String.format("Server shutdown with %s online.", World.getActivePlayers()));
			}));
		}*/

		GameThread.init();
	}
}
