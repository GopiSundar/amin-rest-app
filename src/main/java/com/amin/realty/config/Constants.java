package com.amin.realty.config;

/**
 * Application constants.
 */
public final class Constants {

	// Regex for acceptable logins
	public static final String LOGIN_REGEX = "^[_'.@A-Za-z0-9-]*$";

	public static final String SYSTEM_ACCOUNT = "system";
	public static final String ANONYMOUS_USER = "anonymoususer";

	public static final String EMAIL_MASK_REGEX = "(?<=.{1}).(?=.*@)";
	public static final String EMAIL_MASK_VAL = "X";

	public enum PropertyStatus {
		Listed, Locked, Closed
	}

	private Constants() {
	}
}
