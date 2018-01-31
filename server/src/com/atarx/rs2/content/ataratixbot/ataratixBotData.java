package com.atarx.rs2.content.ataratixbot;

/**
 * Holds the ataratixBot Data
 * @author Daniel
 *
 */
public enum ataratixBotData {	
	  DATA_1("", ""),
	  
	  ;
	
	private final String question;
	private final String[] answers;
	
	private ataratixBotData(String question, String... answers) {
		this.question = question;
		this.answers = answers;
	}
	
	public String getQuestion() {
		return question;
	}
	
	public String[] getAnswers() {
		return answers;
	}
	
}
