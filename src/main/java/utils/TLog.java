package utils;

import config.Config;

public class TLog {
	public static void printOnConsole(String strMessage) {

		if (Config.printInConsole) {
			System.out.println(strMessage+"\n");
		}
	}
	
	/**
	 * Print message on Console.
	 * @param strMessage
	 */
	public static void pc(String strMessage) {
		printOnConsole(strMessage);
	}
	
	/**
	 * Print message in log file.
	 * !!Current not implement!! 
	 * @param strMessage
	 */
	public static void pl(String strMessage) {
		//printOnConsole(strMessage);
	}	
	
	/**
	 * Create screenshot.
	 * !!Current not implement!! 
	 * @param strMessage
	 */
	public static void ss() {
		//printOnConsole(strMessage);
	}	
	
}
